(ns demo.app-logic
  (:require  [demo [app-services :as service]
                   [app-ui :as ui]]
             [clojure.pprint :as pp])
  (:use demo.swing-utils))


(declare frame)

(defn search-debug-handler
  [txt]
  (clojure.pprint/pprint (service/search txt)))

(defn present-result
  [txt]
  (ui/show-result-in-frame frame (service/search txt)))
