(ns advent-of-code-2020.day-5
  (:require [advent-of-code-2020.utils :as utils]
            [clojure.set :as set]))

(def input (utils/input->string-vec "day-5.txt"))

(def row-indices (range 128))
(def col-indices (range 8))

(defn- split-indices
  [input-elem indices]
  (if (= 1 (count indices))
    (first indices)
    (let [section (first input-elem)
          split-coll (partition (/ (count indices) 2) indices)
          coll-fn (if (or (= \F section) (= \L section))
                    first
                    last)]
      (split-indices (subs input-elem 1) (coll-fn split-coll)))))

(defn- get-ids
  [input-rows]
  (let [rows (map #(split-indices % row-indices) input-rows)
        cols (map (fn [input-elem]
                    (-> (subs input-elem 7)
                        (split-indices col-indices)))
                  input-rows)]
    (map #(+ (* % 8) %2) rows cols)))

(defn solution-1
  []
  (->> (get-ids input)
       (apply max)))

(defn solution-2
  []
  (let [ids (-> (get-ids input)
                sort)
        possible-ids (range (first ids) (+ (last ids) 1))]
    (-> (set/difference (set possible-ids) (set ids))
        first)))