(ns advent-of-code-2020.day-9
  (:require [advent-of-code-2020.utils :as utils]
            [clojure.edn :as edn]))

(def preamble-len 25)

(def input (->> (utils/input->string-vec "day-9.txt")
                (map edn/read-string)
                vec))

(defn- is-valid?
  [working-coll val]
  (->> (map-indexed (fn [idx curr]
                      (let [others (if (< idx (count working-coll))
                                     (subvec working-coll (inc idx))
                                     [])]
                        (some #(= (+ % curr) val) others)))
                    working-coll)
       (filter identity)
       seq
       boolean))

(defn- find-invalid-val
  [input]
  (loop [working-coll (subvec input 0 preamble-len)
         curr-val-idx preamble-len]
    (let [curr-val (nth input curr-val-idx)]
      (if (is-valid? working-coll curr-val)
        (recur (conj (subvec working-coll 1) curr-val)
               (inc curr-val-idx))
        curr-val))))