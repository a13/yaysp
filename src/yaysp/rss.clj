(ns yaysp.rss
  (:require
   [clojure.data.xml :as c-d-xml]
   [clojure.zip :as c-zip]
   [clojure.data.zip.xml :as c-d-z-xml]))

(defn parse
  "Get urls from rss xml string."
  [xml-str]
  (c-d-z-xml/xml-> (c-zip/xml-zip (c-d-xml/parse-str xml-str))
                   :channel
                   :item
                   :guid
                   c-d-z-xml/text))
