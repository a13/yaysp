(ns tpgeocache.utils-test
  (:require [clojure.test :refer :all]
            [tpgeocache.utils :as utils]))

(deftest process-test
  (is (= (#'tpgeocache.utils/process
          '("https://www.linux.org.ru/forum/development/13451999" "" "https://groups.google.com/d/topic/ror2ru/2hdkOK1yvWU" "https://groups.google.com/d/topic/ror2ru/2hdkOK1yvWU" "http://www.sql.ru/forum/1261520/tyapnichnyy-benchmark-cpu-part-zero" "http://www.jdon.com/48916" "https://jobs.dou.ua/companies/attendify/vacancies/42879/?1494936667" "http://rubyjobs.ru/vacancies/4805" "http://lisper.ru/forum/messages/12276" "http://lisper.ru/forum/messages/12266"))
         '("https://www.linux.org.ru/forum/development/13451999" "https://groups.google.com/d/topic/ror2ru/2hdkOK1yvWU" "http://www.sql.ru/forum/1261520/tyapnichnyy-benchmark-cpu-part-zero" "http://www.jdon.com/48916" "https://jobs.dou.ua/companies/attendify/vacancies/42879/?1494936667" "http://rubyjobs.ru/vacancies/4805" "http://lisper.ru/forum/messages/12276" "http://lisper.ru/forum/messages/12266"))))

(deftest calc-stat-test
  (is (= (#'tpgeocache.utils/calc-stat
          '("https://www.linux.org.ru/forum/development/13451999" "https://groups.google.com/d/topic/ror2ru/2hdkOK1yvWU" "http://www.sql.ru/forum/1261520/tyapnichnyy-benchmark-cpu-part-zero" "http://www.jdon.com/48916" "https://jobs.dou.ua/companies/attendify/vacancies/42879/?1494936667" "http://rubyjobs.ru/vacancies/4805" "http://lisper.ru/forum/messages/12276" "http://lisper.ru/forum/messages/12266"))
         {"www.linux.org.ru" 1, "groups.google.com" 1, "www.sql.ru" 1, "www.jdon.com" 1, "jobs.dou.ua" 1, "rubyjobs.ru" 1, "lisper.ru" 2})))
