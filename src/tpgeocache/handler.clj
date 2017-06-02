(ns tpgeocache.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [tpgeocache.api :as api]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  ;; (GET "/" [] "Hello World")
  (GET "/geocode" [address] (-> address
                                api/geocode-cached
                                (select-keys [:status :headers :body])))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults
   app-routes
   (assoc-in site-defaults [:security :anti-forgery] false)))
