(ns lambdacd-junit.core
  (:require [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.java.io :as io])
  (:import (java.io File ByteArrayInputStream)
           (java.nio.file Paths)))

(defn ?assoc
  "Same as assoc, but skip the assoc if v is nil"
  [m & kvs]
  (->> kvs
       (partition 2)
       (filter second)
       flatten
       (apply assoc m)))

(defn- parse-xml-file [filename]
  (-> (slurp filename)
      (.getBytes)
      (ByteArrayInputStream.)
      (xml/parse)
      (zip/xml-zip)
      (first)))

(defn testcase-from-raw
  [raw]
  (merge
    (assoc (:attrs raw) :stacktrace (first (:content (first (:content raw)))))
    (:attrs (first (:content raw)))))

(defn select-testcases-from-testsuite
  [testsuite]
  (->> (:content testsuite)
       (filter #(= (:tag %) :testcase))
       (map testcase-from-raw)
       (map #(?assoc {} :label (str (:name %) (dissoc % :name :stacktrace))
                     :raw (:stacktrace %)))))

(defn- suite-to-map [testsuite]
  (hash-map :label (str (:name (:attrs testsuite)) (dissoc (:attrs testsuite) :name))
            :details (select-testcases-from-testsuite testsuite))
  )

(defn- junit4-report-for-test-suite [filename]
  (let [testsuite (parse-xml-file filename)]
    (if (= (:tag testsuite) :testsuites)
      (map suite-to-map (:content testsuite))
      [(suite-to-map testsuite)])
    ))

(defn- relative-path [base-dir file]
  (let [base-path (Paths/get (.toURI base-dir))
        file-path (Paths/get (.toURI file))]
    (.relativize base-path file-path)))

(defn- file-matches [base-dir regex-or-string]
  (if (string? regex-or-string)
    (fn [file]
      (= regex-or-string (str (relative-path base-dir file))))
    (fn [file]
      (re-matches regex-or-string (str (relative-path base-dir file))))))

(defn- find-files-matching [regex-or-string dir]
  (filter (file-matches dir regex-or-string)
          (file-seq dir)))

(defn junit4-reports [shell-out args path title patterns]
  (let [working-dir (io/file (str (:cwd args) path))
        output-files (doall (->> patterns
                                 (map #(find-files-matching % working-dir))
                                 (flatten)
                                 (filter #(not (.isDirectory %)))))
        file-details (flatten (map #(junit4-report-for-test-suite %) output-files))
        details (:details shell-out)]
    (assoc shell-out :details (into details [{:label title :details file-details}]))))
