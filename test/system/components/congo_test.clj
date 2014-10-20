(ns system.components.congo-test
  (:require
   [system.components.congo :refer [new-mongo-db]]
   [com.stuartsierra.component :as component]
   [clojure.test :refer [deftest is]]
   [somnium.congomongo :as cg]))


;(def mongo-db-prod (new-mongo-db "mongodb://127.0.0.1/mongo-test4"))
(def mongo-db-dev (new-mongo-db))

;; (deftest mongo-production
;;   (alter-var-root #'mongo-db-prod component/start)
;;   (is (:db mongo-db-prod) "DB has been added to component")
;;   (is (= clojure.lang.PersistentHashSet (type (db/get-collection-names (:db mongo-db-prod)))) "Collections on DB is a set")
;;   (alter-var-root #'mongo-db-prod component/stop)
;;   (is (nil? (:db mongo-db-prod)) "DB is stopped"))

(deftest mongo-development
  (alter-var-root #'mongo-db-dev component/start)
  (is (:db mongo-db-dev) "DB has been added to component")
  (cg/create-collection! (:db mongo-db-dev) "coll" {:capped true :size 100000 :max 10})
  (is (cg/collection-exists? (:db mongo-db-dev) "coll"))
  (cg/drop-coll! (:db mongo-db-dev) "coll")
  (alter-var-root #'mongo-db-dev component/stop)
  (is (nil? (:db mongo-db-dev)) "DB is stopped"))
