(ns system.components.congo
  (:require [com.stuartsierra.component :as component]
            [somnium.congomongo :as cg]))

(defrecord Congo [uri db]
  component/Lifecycle
  (start [component]
    (if uri
      (let [{:keys [mongo db]} (cg/make-connection uri)]
        (assoc component :db db))
      (let [{:keys [mongo db]} (cg/make-connection
                                     "congo-dev"
                                     :host "127.0.0.1"
                                     :port 27017)]
        (println "Connected to local dev db..")
        (assoc component :db db))))
  (stop [component]
    (assoc component :db nil)))

(defn new-mongo-db
  ([]
     (map->Congo {}))
  ([uri]
     (map->Congo {:uri uri})))
