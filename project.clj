(defproject lambdacd-junit "0.3.1-SNAPSHOT"
  :description "Junit reports in LambdaCD"
  :url "https://github.com/thilo11/lambdacd-junit"
  :license {:name "Apache License, version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :deploy-repositories [["releases"  {:sign-releases true :url "https://clojars.org"}]
                        ["snapshots" {:sign-releases true :url "https://clojars.org"}]])