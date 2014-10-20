(ns system.components.mongo
  (:require [com.stuartsierra.component :as component]
            [somnium.congomongo :as cg]))

(defrecord Congo [uri db]
  component/Lifecycle
  (start [component]
    (if uri
      (let [{:keys [mongo db]} (cg/make-connection uri)]
        (assoc component :db db))
      (let [{:keys [mongo db]} (cg/make-connection
                                     "mongo-dev"
                                     :host "127.0.0.1"
                                     :port 27017)]
        (assoc component :db db))
  (stop [component]
    (assoc component :db nil)))

(defn new-mongo-db
  ([]
     (map->Congo {}))
  ([uri]
     (map->Congo {:uri uri})))



