(ns yaysp.api
  (:require [clj-http.client :as client]
            [clojure.core.memoize :as memo]
            [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan]]))

(def ^:private search-url "http://blogs.yandex.ru/search.rss")

(def ^:private max-queries 3)

(def ^:private ttl 100)

(def ^:private acp-params
  {:timeout 5 :threads max-queries :insecure? false :default-per-route 10})

(defn- search-request
  "Create an async request with output to out."
  [query out]
  (client/get search-url
              {:async? true
               :query-params {:text query}}
              ;; respond callback
              #(go (>! out {:result %}))
              ;; failure callback
              #(go (>! out {:error (.getMessage %)}))))

;; memoizes for same queries only
;; TODO: in memory storage
(def ^:private search-memo (memo/ttl search-request {} :ttl/threshold ttl))

(defn- get-process-result
  "Take a positive result from channel c."
  [c]
  (some->> (<!! c)
           :result
           :body))

(defn fetch-queries
  "Fetch queries in parallel, return only positive results."
  [queries]
  (let [c (chan)]
    (client/with-async-connection-pool
      acp-params
      (doseq [q queries]
        (search-memo q c)))
    ;; we don't need failed results
    (keep
     (fn [_] (get-process-result c))
     queries)))
