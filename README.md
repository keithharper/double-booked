# double-booked

## Double Booked
When maintaining a calendar of events, it is important to know if an event overlaps with another event.

Given a sequence of events, each having a start and end time, write a program that will return the sequence of all pairs of overlapping events.

## Assumptions

1. Start and end dates are datetime instances
2. An exact match to the minute and second should be considered overlapping

## Examples

### With valid data
```
[{:start-time #inst "2018-10-30T12:00:00.000-00:00", :end-time #inst "2018-10-30T13:20:00.000-00:00"}
 {:start-time #inst "2018-10-29T12:00:00.000-00:00", :end-time #inst "2018-10-30T13:00:00.000-00:00"}
 {:start-time #inst "2018-10-30T10:00:00.000-00:00", :end-time #inst "2018-10-30T13:20:00.000-00:00"}
 {:start-time #inst "2018-10-30T11:00:00.000-00:00", :end-time #inst "2018-10-30T13:20:00.000-00:00"}
 {:start-time #inst "2018-10-30T18:00:00.000-00:00", :end-time #inst "2018-10-31T13:20:00.000-00:00"}
 {:start-time #inst "2018-10-20T12:00:00.000-00:00", :end-time #inst "2018-10-20T13:20:00.000-00:00"}]
```

```
double-booked kharper$ lein test

lein test double-booked.core-test

Ran 1 tests containing 1 assertions.
0 failures, 0 errors.

```

```
double-booked kharper$ lein run
[[{:start-time #inst "2018-10-30T12:00:00.000-00:00",
   :end-time #inst "2018-10-30T13:20:00.000-00:00"}
  {:start-time #inst "2018-10-29T12:00:00.000-00:00",
   :end-time #inst "2018-10-30T13:00:00.000-00:00"}]
 [{:start-time #inst "2018-10-30T12:00:00.000-00:00",
   :end-time #inst "2018-10-30T13:20:00.000-00:00"}
  {:start-time #inst "2018-10-30T10:00:00.000-00:00",
   :end-time #inst "2018-10-30T13:20:00.000-00:00"}]
 [{:start-time #inst "2018-10-30T12:00:00.000-00:00",
   :end-time #inst "2018-10-30T13:20:00.000-00:00"}
  {:start-time #inst "2018-10-30T11:00:00.000-00:00",
   :end-time #inst "2018-10-30T13:20:00.000-00:00"}]
 ...]
```

### With invalid data
```
[{:start-time #inst "2018-10-30T12:00:00.000-00:00", :end-time "2018-10-30T13:20:00.000-00:00"}
 {:start-time #inst "2018-10-29T12:00:00.000-00:00", :end-time #inst "2018-10-30T13:00:00.000-00:00"}
 {:start-time #inst "2018-10-30T10:00:00.000-00:00", :end-time #inst "2018-10-30T13:20:00.000-00:00"}]
```
```
double-booked kharper$ lein test

lein test double-booked.core-test

lein test :only double-booked.core-test/calendar-booking-seq-days

ERROR in (calendar-booking-seq-days) (core.clj:38)
Given a sequence of start dates and end dates,
            find all matching pairs of overlapping start dates and end dates
expected: (s/valid? :double-booked.core/coll-double-booking-pairs (find-double-bookings events))
  actual: java.lang.AssertionError: Assert failed: (s/valid? :double-booked.core/dates dates)
 at double_booked.core$find_double_bookings.invokeStatic (core.clj:38)
```

```
double-booked kharper$ lein run
Exception in thread "main" java.lang.AssertionError: Assert failed: (s/valid? :double-booked.core/dates dates), compiling:(/private/var/folders/th/g_s485m51454qdwjnlt3g_200000gp/T/form-init3111175884639922889.clj:1:125)
```
