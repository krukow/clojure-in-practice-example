(ns demo.app-services
  (:require [demo [aws :as aws]]
            [cemerick.rummage :as sdb]
            [clucy.core :as lucy]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.string :as s])
  (:import [org.apache.lucene.index
            IndexWriter IndexReader
            IndexWriter$MaxFieldLength
            Term]
           [ org.apache.lucene.search BooleanClause BooleanClause$Occur
            BooleanQuery IndexSearcher TermQuery]))


;;;; Query Ratings
(defn query-ratings
  "Queries ratings in domain (foodstands, foodstandsproduction)"
  [config domain]
  (sdb/query
   config
   `{select [:rating]
     from ~domain
     where (not (= :rating "0.00,0"))
     order-by [:rating desc]
     limit 2500}))

(def client (aws/config-client))

(defn normalize-ratings
  [ratings]
  (let [normalize-rating
        (fn [{r :rating i ::sdb/id}]
          (let [[rating num] (s/split r #",")]
            {:rating rating :rating-count num :id i}))]
    (map normalize-rating ratings)))

(defn fetch-ratings
  []
  (normalize-ratings
   (query-ratings client "foodstands")))

(declare find-stand-by-id)

(defn merge-data-sources
  [ratings]
  (let [find-rating (fn [{id :id :as r}]
                      (merge r (find-stand-by-id id)))]
    (map find-rating ratings)))


;;; Lucene stuff
(def index
  (lucy/disk-index "indices"))

(defn write-ratings-to-index
  []
  (apply lucy/add index (merge-data-sources (fetch-ratings))))


(defn writer
  []
  (IndexWriter. (:index @index) lucy/*analyzer* IndexWriter$MaxFieldLength/UNLIMITED))

(defn searcher
  []
  (IndexSearcher. (:index @index)))


;;; Other Data source

(def foodstands
  (with-open
      [in-file (io/reader (io/resource "foodstands.csv"))]
    (let [stand->map
          (fn [[id name & rest]]
            (let [id (s/replace (s/trim id) "." "")
                  name (s/trim name)]
              {:id id :name name}))]
      (doall
       (map stand->map (drop 1 (csv/read-csv in-file)))))))


(defn find-stand-by-id
  [id]
  (first (filter
          (fn [{sid :id}] (= id sid))
          foodstands)))


;;; API

(defn search
  ([term] (search term 10))
  ([term num] (lucy/search index term num)))
