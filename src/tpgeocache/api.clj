(ns tpgeocache.api
  (:require [clj-http.client :as client]
            [clojure.core.cache :as cache]
            [clojure.core.async
             :refer [>! <! >!! <!! go chan]]))

(def ^:private hour (* 1000 60 60))

(def ^:private address-cache (atom (cache/ttl-cache-factory {} :ttl hour)))

(def ^:private search-url "http://blogs.yandex.ru/search.rss")

(defn- query-params
  [q]
  {:text q})

(defn- search-request
  "Create an async request with output to out."
  [query out]
  (client/get search-url
              {:async? true
               :socket-timeout 2000
               :conn-timeout 2000
               :query-params (query-params query)}
              ;; respond callback
              #(go (>! out %))
              ;; failure callback
              #(go (>! out (.getMessage %)))))

(defn- geocode
  [address]
  (let [c (chan)]
    (search-request address c)
    (<!! c)))

(defn geocode-cached
  [address]
  (if (cache/has? @address-cache address)
    (get (cache/hit @address-cache address) address)
    (let [gc (geocode address)]
      (if (= 200 (:status gc))
        (do
          (let [updated-cache (swap! address-cache #(cache/miss % address gc))]
            (get updated-cache address)))
        {:status 500
         :body gc}))))
