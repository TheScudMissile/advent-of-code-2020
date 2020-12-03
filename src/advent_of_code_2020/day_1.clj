(ns advent-of-code-2020.day-1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def input
  (->> (io/resource "day-1.txt")
       (slurp)
       (str/split-lines)
       (map edn/read-string)))

(def input-set (set input))

(defn get-values
  [needed-sum]
  (loop [idx (- (count input) 1)]
    (let [curr-val (nth input idx)
          needed-val (- needed-sum curr-val)
          match? (input-set needed-val)]
      (cond
        match?
        [curr-val needed-val]

        (> idx 0)
        (recur (dec idx))))))

(defn solution-1
  []
  (apply * (get-values 2020)))

(defn solution-2
  []
  (loop [idx (- (count input) 1)]
    (let [curr-val (nth input idx)
          needed-val-sum (- 2020 curr-val)
          needed-vals (get-values needed-val-sum)]
      (if needed-vals
        (apply * (cons curr-val needed-vals))
        (recur (dec idx))))))