(defproject demo "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/data.csv "0.1.2"]
                 [clucy/clucy "0.2.3"]
                 [com.cemerick/rummage "0.0.3"]]
  :jvm-opts ["-agentlib:jdwp=transport=dt_socket,server=y,suspend=n"]
  :dev-dependencies [[clojure-source "1.3.0"]]
  :resources-path "resources"
  :project-init
    (do (require 'clojure.pprint)
        (use 'clojure.repl)
        (use 'clojure.java.javadoc)))
