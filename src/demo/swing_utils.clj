(ns demo.swing-utils)

(defn components [frame]
  (if (nil? frame)
    []
    (let [children (.getComponents frame)
          child-components (map components children)]
      (flatten
       (conj child-components frame)))))

(defn find-components
  "finds all components that match predicate"
  [pred frame]
  (filter pred (components frame)))

(defn find-by-class
  "Find components by class"
  [frame clazz]
  (find-components #(instance? clazz %) frame))
