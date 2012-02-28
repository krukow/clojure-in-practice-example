(ns demo.syntax);; a namespace, like a "live" package

;; Clojure base types mostly unify with Java types
(def s "java.lang.String")

(def b true);;Boolean

(def null nil)

(def n 42);;long

(def r 24/7);; actual ratio object, not an operation
;; By default inpu/output of functions are boxed objects

(defn add2 ;;define function
  "adds two to x" ;;docstring
  [x] ;;params
  (+ x 2)) ;;call function
