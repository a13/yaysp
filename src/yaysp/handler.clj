(ns yaysp.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :as json]
            [yaysp.utils :as utils]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn- search-handler
  [query]
  {:status 200
   :headers {"Content-Type" "text/json; charset=utf-8"}
   :body
   (json/generate-string (utils/stat query)
                         {:pretty true})})

(defroutes app-routes
  ;; (GET "/" [] "Hello World")
  (GET "/search" [query] (search-handler query))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults
   app-routes
   (assoc-in site-defaults [:security :anti-forgery] false)))
