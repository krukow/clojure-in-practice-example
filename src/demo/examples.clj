(ns demo.examples)


;;; Most common Clojure Compounds

(def vec-ds ["a" "b" "c"])
(def map-ds {:a 42 :b "b", :c :c})
(def set-ds #{1,2})
(def list-ds (list 1 2 3))

;;; All immutable
(def vec2-ds
  (conj vec-ds 4))

(use 'clojure.pprint)
(defn conjoin
  []
  (pprint vec2-ds)
  (prn "---")
  (pprint vec-ds))

(def map2-ds
  (assoc map-ds :d vec-ds))


;;; Sequences
;; All the clojure collection types can be converted to sequences
;; There is a Rich library for sequence manipulation
;;drop, take, cycle
;;interleave
;;partition
;;interpose
;;map, reduce

;;- core abstractions are Java interfaces
;;- collections implement Collection (- write stuff optional)
(comment
  (java.util.Collections/max [1,2,3]))


;;- seq library on Java things (Interable, Strings arrays)
(comment
  (let [array-list (new java.util.ArrayList)]
    (doto array-list
      (.add 1)
      (.add 2)
      (.add 3))
    (map inc array-list)))

(comment (drop 5 "Karl Krukow"))
