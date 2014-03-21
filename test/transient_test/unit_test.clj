(ns transient-test.unit-test
  (:require [clojure.test :refer :all]
            [transient-test.core :refer :all]))

(def examples
  [
   #(-> #{} (disj 5) (conj 5) transient (conj! 5) (disj! 5) persistent! (conj 1))
   #(-> #{} (disj 5) (conj 1) transient (conj! -5) (disj! 5) persistent! (conj -5))
   #(-> #{} transient (conj! 1) (disj! 1) persistent!)
   #(-> #{} transient persistent! (conj 122) (disj 1) transient (conj! 122) (disj! 122) persistent!)
   #(-> #{} transient (conj! 1) (disj! 2) persistent!)
   #(-> #{} transient (conj! 1) (disj! -1) persistent!)
   #(-> #{} transient (conj! 1) (disj! -1)  (conj! 1) persistent! (conj -1))
   #(-> #{} transient (conj! 1) (disj! -1)  (conj! 1) persistent! (conj -1))
   #(-> #{} transient (conj! 1) (disj! -1)  (conj! 1) persistent!)
   #(-> #{} transient (conj! 1) (disj! 1) persistent! (conj 1))
   #(-> #{} (conj 50) transient (disj! 50) persistent!)
   ])

(deftest transient-equality
  (doall (for [ex examples]
           (is (= (ex) (ex))))))
