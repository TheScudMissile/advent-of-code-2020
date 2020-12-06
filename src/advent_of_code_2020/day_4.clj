(ns advent-of-code-2020.day-4
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input (-> (io/resource "day-4.txt")
                slurp
               (str/split #"\n\n")))

(def regex-vec [#"ecl" #"pid" #"eyr" #"hcl" #"byr" #"iyr" #"hgt"])

(defn solution-1
  []
  (reduce (fn [acc curr]
            (let [results (map #(re-find % curr) regex-vec)]
              (if (every? #(not (nil? %)) results)
                (+ acc 1)
                acc)))
          0
          input))