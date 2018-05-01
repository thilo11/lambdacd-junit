(ns lambdacd-junit.core-test
  (:require [lambdacd-junit.core :refer :all]
            [clojure.test :refer :all]))

(def a-testsuite {:tag     :testsuite, :attrs {:time "0.005", :hostname "nb162", :timestamp "2016-02-27T16:27:10", :errors "0", :failures "2", :skipped "0", :tests "2", :name "de.foocorp.AnotherClassTest"},
                  :content [{:tag :properties, :attrs nil, :content nil}
                            {:tag     :testcase, :attrs {:time "0.004", :classname "de.foocorp.AnotherClassTest", :name "testMult1"},
                             :content [{:tag     :failure, :attrs {:type "java.lang.AssertionError", :message "java.lang.AssertionError: expected:<0> but was:<10>"},
                                        :content ["java.lang.AssertionError: expected:<0> but was:<10>\\n\\tat org.junit.Assert.fail(Assert.java:88)\\n\\tat org.junit.Assert.failNotEquals(Assert.java:743)\\n\\tat"]}]}
                            {:tag     :testcase, :attrs {:time "0.001", :classname "de.foocorp.AnotherClassTest", :name "testMult2"},
                             :content [{:tag     :failure, :attrs {:type "java.lang.AssertionError", :message "java.lang.AssertionError: expected:<1> but was:<10>"},
                                        :content ["java.lang.AssertionError: expected:<1> but was:<10>\\n\\tat org.junit.Assert.fail(Assert.java:88)\\n\\tat org.junit.Assert.failNotEquals(Assert.java:743)\\n\\tat"]}]}
                            {:tag :system-out, :attrs nil, :content nil} {:tag :system-err, :attrs nil, :content nil}]})

(def first-testcase-raw {:tag     :testcase, :attrs {:time "0.004", :classname "de.foocorp.AnotherClassTest", :name "testMult1"},
                         :content [{:tag     :failure, :attrs {:type "java.lang.AssertionError", :message "java.lang.AssertionError: expected:<0> but was:<10>"},
                                    :content ["java.lang.AssertionError: expected:<0> but was:<10>\\n\\tat org.junit.Assert.fail(Assert.java:88)\\n\\tat org.junit.Assert.failNotEquals(Assert.java:743)\\n\\tat"]}]})

(def first-testcase {:time       "0.004", :classname "de.foocorp.AnotherClassTest", :name "testMult1", :type "java.lang.AssertionError", :message "java.lang.AssertionError: expected:<0> but was:<10>"
                     :stacktrace "java.lang.AssertionError: expected:<0> but was:<10>\\n\\tat org.junit.Assert.fail(Assert.java:88)\\n\\tat org.junit.Assert.failNotEquals(Assert.java:743)\\n\\tat"})


(def result (list {:label "testMult1{:time \"0.004\", :classname \"de.foocorp.AnotherClassTest\", :type \"java.lang.AssertionError\", :message \"java.lang.AssertionError: expected:<0> but was:<10>\"}"
                   :raw   "java.lang.AssertionError: expected:<0> but was:<10>\\n\\tat org.junit.Assert.fail(Assert.java:88)\\n\\tat org.junit.Assert.failNotEquals(Assert.java:743)\\n\\tat"}
                  {:label "testMult2{:time \"0.001\", :classname \"de.foocorp.AnotherClassTest\", :type \"java.lang.AssertionError\", :message \"java.lang.AssertionError: expected:<1> but was:<10>\"}"
                   :raw   "java.lang.AssertionError: expected:<1> but was:<10>\\n\\tat org.junit.Assert.fail(Assert.java:88)\\n\\tat org.junit.Assert.failNotEquals(Assert.java:743)\\n\\tat"}))

(def report-out
  {:details '({:details ({:details ({:label "testMult1{:time \"0.002\", :classname \"de.foocorp.AnotherClassNoFailuresTest\"}"}
                                     {:label "testMult2{:time \"0.0\", :classname \"de.foocorp.AnotherClassNoFailuresTest\"}"})
                          :label   "de.foocorp.AnotherClassNoFailuresTest{:time \"0.002\", :hostname \"nb162\", :timestamp \"2016-03-10T16:21:21\", :errors \"0\", :failures \"0\", :skipped \"0\", :tests \"2\"}"}
                          {:details ({:label "testMult{:time \"0.001\", :classname \"de.foocorp.AnotherClassTest\", :type \"java.lang.AssertionError\", :message \"java.lang.AssertionError: expected:<0> but was:<10>\"}"
                                      :raw   "java.lang.AssertionError: expected:<0> but was:<10>
\tat org.junit.Assert.fail(Assert.java:88)
\tat org.junit.Assert.failNotEquals(Assert.java:743)
\tat org.junit.Assert.assertEquals(Assert.java:118)
\tat org.junit.Assert.assertEquals(Assert.java:555)
\tat org.junit.Assert.assertEquals(Assert.java:542)
\tat de.foocorp.AnotherClassTest.testMult(AnotherClassTest.java:11)
"})
                           :label   "de.foocorp.AnotherClassTest{:time \"0.001\", :hostname \"nb162\", :timestamp \"2016-02-25T12:32:46\", :errors \"0\", :failures \"1\", :skipped \"0\", :tests \"1\"}"}
                          {:details ({:label "testPlus{:time \"0.0\", :classname \"de.foocorp.MainClassTest\", :type \"java.lang.AssertionError\", :message \"java.lang.AssertionError: expected:<9> but was:<8>\"}"
                                      :raw   "java.lang.AssertionError: expected:<9> but was:<8>
\tat org.junit.Assert.fail(Assert.java:88)
\tat org.junit.Assert.failNotEquals(Assert.java:743)
\tat org.junit.Assert.assertEquals(Assert.java:118)
\tat org.junit.Assert.assertEquals(Assert.java:555)
\tat org.junit.Assert.assertEquals(Assert.java:542)
\tat de.foocorp.MainClassTest.testPlus(MainClassTest.java:11)
"})
                           :label   "de.foocorp.MainClassTest{:time \"0.0\", :hostname \"nb162\", :timestamp \"2016-02-25T12:32:46\", :errors \"0\", :failures \"1\", :skipped \"0\", :tests \"1\"}"})
               :label   "Junit test results"})
   :status  :failure})

(def jest-report
  {:details '({:details ({:details ({:label "can translate{:time \"0\", :classname \"Polyglot exploration test\"}"}
                                    {:label "can translate nested{:time \"0\", :classname \"Polyglot exploration test\"}"}
                                    {:label "can interpolate{:time \"0\", :classname \"Polyglot exploration test\"}"}),
                         :label "/Users/alphonse.bendt/Projects/wish-list-ui/wish-list-ui-server/src/test/js/i18n/polyglot.test.js{:timestamp \"2018-04-26T14:50:12\", :time \"0.845\", :failures \"0\", :tests \"3\", :hostname \"localhost\", :package \"/Users/alphonse.bendt/Projects/wish-list-ui/wish-list-ui-server/src/test/js/i18n/polyglot.test.js\", :errors \"0\", :id \"0\"}"}
                         {:details ({:label "can add product{:time \"0\", :classname \"ComparePanel\"}"}
                                     {:label "can add product only once{:time \"0\", :classname \"ComparePanel\"}"}
                                     {:label "can add multiple product{:time \"0\", :classname \"ComparePanel\"}"}
                                     {:label "can remove products{:time \"0\", :classname \"ComparePanel\"}"}),
                          :label "/Users/alphonse.bendt/Projects/wish-list-ui/wish-list-ui-server/src/test/js/compare/compare-panel.test.js{:timestamp \"2018-04-26T14:50:13\", :time \"0.478\", :failures \"0\", :tests \"4\", :hostname \"localhost\", :package \"/Users/alphonse.bendt/Projects/wish-list-ui/wish-list-ui-server/src/test/js/compare/compare-panel.test.js\", :errors \"0\", :id \"1\"}"}),
              :label "Jest rest results"}),
   :status :failure})

(deftest testcase-from-raw-test
  (testing "testcase-from-raw-test"
    (is (= first-testcase
           (testcase-from-raw first-testcase-raw)))))

(deftest select-testcases-from-testsuite-test
  (testing "select-testcases-from-testsuite-test"
    (is (= result
           (select-testcases-from-testsuite a-testsuite)))))

(deftest junit4-reports-test
  (testing "junit4-reports-test"
    (let [shell-out {:status :failure}
          args {:cwd "./"}
          path "test-resources/"]
      (is (= report-out
             (junit4-reports shell-out args path "Junit test results" [#"TEST-.*"]))))))

(deftest jest-reports-test
  (testing "jest-reports-test"
           (let [shell-out {:status :failure}
                 args {:cwd "./"}
                 path "test-resources/"]

             (is (= jest-report
                    (junit4-reports shell-out args path "Jest rest results" [#"JEST-.*"])))
             )))