(ns demo.aws
  (:require [clojure.java [io :as io]]
            [cemerick.rummage :as sdb]
            [cemerick.rummage.encoding :as enc])
  (:import [java.util Properties]))


(declare aws-credentials)

(defn config-client
  "Creates and configures an AWS client for this project"
  []
  (let [client (sdb/create-client (:id aws-credentials) (:secret aws-credentials))]
    (.setEndpoint client "sdb.eu-west-1.amazonaws.com")
    (assoc enc/keyword-strings :client client)))



;;;;;;;;;;;;;; AWS auth helpers ;;;;;;;;;;;;;

(defn ^clojure.lang.IPersistentMap
  propmap
  "Load properties file from classpath and return as map"
  [path]
  (let [p (Properties.)]
    (.load p (io/reader (io/resource path)))
    (into {} p)))

(def aws-credentials
  (clojure.walk/keywordize-keys (propmap "aws.properties")))
