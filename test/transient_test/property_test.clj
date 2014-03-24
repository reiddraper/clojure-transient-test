(ns transient-test.property-test
  (:require [clojure.test :refer :all]
            [transient-test.core :refer :all]
            [clojure.test.check.clojure-test :refer (defspec)]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

(defn transient?
  [x]
  (instance? clojure.lang.ITransientCollection x))

(def gen-conj
  (gen/fmap (fn [x]
              [:conj x])
            gen/int))

(def gen-disj
  (gen/fmap (fn [x]
              [:disj x])
            gen/int))

(def gen-action
  (gen/one-of [gen-conj
               gen-disj
               (gen/return [:transient])
               (gen/return [:persistent!])]))

(defn reduce-actions
  [coll actions]
  (reduce
    (fn [c [f & [arg]]]
      (condp = [(transient? c) f]
        [true   :conj]          (conj! c arg)
        [false  :conj]          (conj c arg)
        [true   :disj]          (disj! c arg)
        [false  :disj]          (disj c arg)
        [true   :transient]     c
        [false  :transient]     (transient c)
        [true   :persistent!]   (persistent! c)
        [false  :persistent!]   c))
    coll
    actions))

(defn apply-actions
  [coll actions]
  (let [applied (reduce-actions coll actions)]
    (if (transient? applied)
      (persistent! applied)
      applied)))

(defn filter-actions
  [actions]
  (filter (fn [[a & args]]
            (#{:conj :disj} a))
          actions))

(def transient-property
  (prop/for-all
    [a (gen/vector gen-action)]
    (= (apply-actions #{} a)
       (apply-actions #{} (filter-actions a)))))

(defspec transient-property-test 1000000 transient-property)
