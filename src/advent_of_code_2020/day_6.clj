(ns advent-of-code-2020.day-6
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input (-> (io/resource "day-6.txt")
               slurp
               (str/split #"\n\n")))

(defn solution-1
  []
  (reduce (fn [acc curr-str]
            (-> (str/replace curr-str #"\n" "")
                set
                count
                (+ acc)))
          0
          input))

(defn solution-2
  []
  (reduce (fn [acc curr-str]
            (->> (str/split curr-str #"\n")
                 (map set)
                 (apply set/intersection)
                 count
                 (+ acc)))
          0
          input))
