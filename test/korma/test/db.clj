(ns korma.test.db
  (:use
    [clojure.test]
    [korma.db]))

(def ^{:private true} db-config-with-pooling
  {:classname "org.h2.Driver"
   :subprotocol "h2"
   :subname "mem:db_connectivity_test_db"
   :minimum-pool-size 5
   :maximum-pool-size 20}
  )

(deftest connection-pooling-default-test
  (let [db-config-default (dissoc db-config-with-pooling :minimum-pool-size :maximum-pool-size)
        pool (connection-pool db-config-default)
        datasource (get pool :datasource)]
    (is (= (.getMaxPoolSize datasource) 15))
    (is (= (.getMinPoolSize datasource) 3))
    ))

(deftest connection-pooling-test
  (let [pool (connection-pool db-config-with-pooling)
        datasource (get pool :datasource)]
    (is (= (.getMaxPoolSize datasource) 20))
    (is (= (.getMinPoolSize datasource) 5))
    ))