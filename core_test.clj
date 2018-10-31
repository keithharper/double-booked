(ns double-booked.core-test
  (:require [clojure.test :refer :all]
            [double-booked.core :refer :all]
            [clojure.spec.alpha :as s]))

(deftest calendar-booking-seq-days
  (testing "Given a sequence of start dates and end dates,
            find all matching pairs of overlapping start dates and end dates"
    (is (s/valid? :double-booked.core/coll-double-booking-pairs
                  (find-double-bookings events)))))