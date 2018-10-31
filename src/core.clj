(ns double-booked.core
  (:gen-class)
  (:require [clojure.spec.alpha :as s]
            [clojure.pprint :refer [pprint]]))

(def events
  [{:start-time #inst "2018-10-30T12:00:00.000-00:00", :end-time #inst "2018-10-30T13:20:00.000-00:00"}
   {:start-time #inst "2018-10-29T12:00:00.000-00:00", :end-time #inst "2018-10-30T13:00:00.000-00:00"}
   {:start-time #inst "2018-10-30T10:00:00.000-00:00", :end-time #inst "2018-10-30T13:20:00.000-00:00"}
   {:start-time #inst "2018-10-30T11:00:00.000-00:00", :end-time #inst "2018-10-30T13:20:00.000-00:00"}
   {:start-time #inst "2018-10-30T18:00:00.000-00:00", :end-time #inst "2018-10-31T13:20:00.000-00:00"}
   {:start-time #inst "2018-10-20T12:00:00.000-00:00", :end-time #inst "2018-10-20T13:20:00.000-00:00"}])

(defn- time-within? [time {:keys [start-time end-time]}]
  (not= (compare time start-time)
        (compare time end-time)))

(defn- booked? [{:keys [start-time end-time]} scheduled-date]
  (or (time-within? start-time scheduled-date)
      (time-within? end-time scheduled-date)))

(defn- check-calendar
  "Takes a requested date and a sequence of dates and returns whether or not the
  requested date is already booked"
  [requested-date calendar]
  (let [booked-dates (filter #(booked? requested-date %) calendar)]
    (map (fn [booked-date] [requested-date booked-date])
         booked-dates)))


(s/def ::start-time inst?)
(s/def ::end-time inst?)
(s/def ::date (s/keys :req-un [::start-time ::end-time]))
(s/def ::dates (s/coll-of ::date))
(s/def ::double-booking-pairs (s/tuple ::date ::date))
(s/def ::coll-double-booking-pairs (s/coll-of ::double-booking-pairs))

(defn find-double-bookings [dates]
  {:pre [(s/valid? ::dates dates)]
   :post [(s/valid? ::coll-double-booking-pairs %)]}
  (->> dates
       (map (fn [current-date]
              (let [other-dates (filter #(not= current-date %) dates)]
                (check-calendar current-date other-dates))))
       flatten
       (partition-all 2)
       (mapv #(into [] %))))

(defn -main [& args]
  (pprint (find-double-bookings events)))
