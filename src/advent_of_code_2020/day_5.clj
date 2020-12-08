(ns advent-of-code-2020.day-5
  (:require [advent-of-code-2020.utils :as utils]))

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

(defn solution-1
  []
  (let [rows (map #(split-indices % row-indices) input)
        cols (map (fn [input-elem]
                    (-> (subs input-elem 7)
                        (split-indices col-indices)))
                  input)
        vals (->> (map #(+ (* % 8) %2) rows cols)
                  (apply max))]
    vals))