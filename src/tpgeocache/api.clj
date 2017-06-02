(ns tpgeocache.api
  (:require [clj-http.client :as client]
            [clojure.core.cache :as cache]
            [clojure.core.async
             :refer [>! <! >!! <!! go chan]]))

(def ^:private hour (* 1000 60 60))

(def ^:private address-cache (atom (cache/ttl-cache-factory {} :ttl hour)))

(def ^:private search-url "http://geo.truckerpathteam.com/maps/api/geocode/json")

(defn- query-params
  [q]
  {:address q})

(defn- error-msg
  [e]
  (or (.getMessage e)
      (str "Unknown error: " e)))

(defn- search-request
  "Create an async request with output to out."
  [query out]
  (client/get search-url
              {:async? true
               :socket-timeout 1000
               :conn-timeout 1000
               :query-params (query-params query)}
              ;; respond callback
              #(go (>! out %))
              ;; failure callback
              #(go (>! out (error-msg %)))))

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
      (if (:status gc)
        (let [updated-cache (swap! address-cache #(cache/miss % address gc))]
          (get updated-cache address))
        {:status 500
         :body gc}))))
