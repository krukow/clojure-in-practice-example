(ns demo.app-ui
  (:import  [javax.swing JFrame JLabel JPanel
               JTextField JButton JTextArea
               BorderFactory]
            [java.awt.event ActionListener]
            [java.io StringWriter]
            [java.awt FlowLayout GridLayout BorderLayout])
  (:require [clojure.pprint :as pp])
  (:use [demo swing-utils]))


(comment
  (do (require 'clojure.pprint)
        (use 'clojure.repl)
        (use 'clojure.java.javadoc)))

(declare search-performed)


(defn make-frame
  []
  ( let [frame (JFrame. "Example App")
         search-panel (JPanel.)
         search-field (JTextField.)
         search-label (JLabel. "Search")
         search-button  (JButton. "Search")
         result-text (JTextArea. "The result will be displayed here")

         search-listener
         (reify ActionListener
           (actionPerformed [this e]
             (let [txt (.getText search-field)]
               (search-performed txt))))]

    (.setBorder search-label
                (BorderFactory/createEmptyBorder 0 54 0 0))

    (.addActionListener search-button search-listener)

    (reify ActionListener (actionPerformed [this e] (prn frame)))
    (doto search-panel
      (.setLayout (GridLayout. 2 2 3 3))
      (.add search-label)
      (.add search-field)
      (.add search-button))

    (.setLineWrap result-text true)

    (doto frame
      (.setLayout (BorderLayout.))
      (.add search-panel BorderLayout/PAGE_START)
      (.add result-text BorderLayout/CENTER)
      (.setSize 300 300)
      (.setVisible true))))

(def search-handlers
  (atom [prn]))

(defn add-handler!
  [h]
  (swap! search-handlers conj h))


(defn search-performed [txt]
  (doseq [handler @search-handlers]
    (handler txt)))

(defn result->text
  [res]
  (binding
      [*out* (StringWriter.)]
    (pp/print-table [:name :rating] res)
    (.toString *out*)))

(defn show-result-in-frame
  [frame res]
  (let [txt-area (first (find-by-class frame JTextArea)) ]
    (.setText txt-area (result->text res))))
