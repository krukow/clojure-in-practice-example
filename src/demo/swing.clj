(ns demo.swing
  (:import [javax.swing JFrame JLabel JTextField JButton]
           [java.awt.event ActionListener]
           [java.awt GridLayout]))

(comment
  (do (require 'clojure.pprint)
        (use 'clojure.repl)
        (use 'clojure.java.javadoc)))

;;Interop
;;Direct wrapper free access


(defn celsius []
  (let [frame (JFrame. "Celsius Converter")
        temp-text (JTextField.)
        celsius-label (JLabel. "Celsius")
        convert-button (JButton. "Convert")
        fahrenheit-label (JLabel. "Fahrenheit")]

    (doto frame
      (.setLayout (GridLayout. 2 2 3 3))
      (.add temp-text) (.add celsius-label)
      (.add convert-button) (.add fahrenheit-label)
      (.setSize 300 80) (.setVisible true))))




;;(javadoc frame)
;;(.setLocation frame 300,300)
;;(.setBackground  frame java.awt.Color/RED)
;;(

(defn components [frame]
  (if (nil? frame)
    []
    (let [children (.getComponents frame)
          child-components (map components children)]
      (flatten
       (conj child-components frame)))))

(defn buttons [frame]
  (let [is-button? (fn [child]
                     (instance? javax.swing.JButton child))]
    (filter is-button? (components frame))))

;;- Implement and extend Java Interfaces and classes (but Clojure prefers interfaces)

(def listener
  (reify java.awt.event.ActionListener
    (actionPerformed [this e] prn)))

;; define cel->fahr
;;- functions are callable and Runnable
;;- EVERYTHING is interfaces (=> you can extend clojure in Java)
;;- Primitive arithmetic support for performance






(comment
  (.addActionListener
   convert-button
   (reify java.awt.event.ActionListener
     (actionPerformed [this e]
       (let [cel (Integer/parseInt (.getText temp-text))
             fahr (str (celsius2far cel))]
         (.setText fahrenheit-label fahr))))))
