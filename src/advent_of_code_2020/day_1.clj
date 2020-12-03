(ns advent-of-code-2020.day-1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def input
  (->> (io/resource "day-1.txt")
       (slurp)
       (str/split-lines)
       (map edn/read-string)))

(defn solution-1
  []
  (let [input-set (set input)]
    (loop [idx (- (count input) 1)]
      (let [curr-val (nth input idx)
            needed-val (- 2020 curr-val)
            match? (input-set needed-val)]
        (if match?
          (* curr-val needed-val)
          (recur (dec idx)))))))