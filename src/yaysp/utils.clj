(ns yaysp.utils
  (:require
   [yaysp.rss :as rss]
   [yaysp.api :as api]
   [cemerick.url :as url]))

(defn- calc-stat
  "Calculate statistics for urls."
  [urls]
  (->> urls
       (map (comp :host url/url))
       frequencies))

(defn- process
  "Prepare results."
  [results]
  (->> results
       flatten
       distinct
       (remove empty?)))

(defn stat
  "Count stats for queries."
  [queries]
  (->> queries
       api/fetch-queries
       (map rss/parse)
       process
       calc-stat))

(comment
  (time
   (stat ["clojure" "scala" "linux"])))
