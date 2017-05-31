(ns yaysp.rss-test
  (:require [clojure.test :refer :all]
            [yaysp.rss :as rss]))

(def rss-str
  (slurp "test/data/clojure.xml"))

(deftest parse-test
  (is
   (= (rss/parse rss-str)
      '("https://www.linux.org.ru/forum/development/13451999" "" "https://groups.google.com/d/topic/ror2ru/2hdkOK1yvWU" "https://groups.google.com/d/topic/ror2ru/2hdkOK1yvWU" "http://www.sql.ru/forum/1261520/tyapnichnyy-benchmark-cpu-part-zero" "http://www.jdon.com/48916" "https://jobs.dou.ua/companies/attendify/vacancies/42879/?1494936667" "http://rubyjobs.ru/vacancies/4805" "http://lisper.ru/forum/messages/12276" "http://lisper.ru/forum/messages/12266")))
  (is (thrown? javax.xml.stream.XMLStreamException
               (rss/process "")))
  (is (thrown? javax.xml.stream.XMLStreamException
               (rss/process "<malformed shit?"))))
