(defproject yaysp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.6.0"]
                 [ring/ring-defaults "0.3.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [org.clojure/core.async "0.3.443"]
                 [clj-http "3.6.1"]
                 [org.clojure/data.xml "0.0.8"]
                 [com.cemerick/url "0.1.1"]
                 [org.clojure/data.zip "0.1.2"]
                 [com.datomic/datomic-free "0.9.5561.50"]
                 [cheshire "5.7.1"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler yaysp.handler/app
         :port 8080
         :nrepl {:start? true}}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
