(ns advent-of-code-2020.day-2
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [clojure.java.io :as io]))

(def input
  (->> (io/resource "day-2.txt")
       slurp
       str/split-lines))

(defn- tokenize-input
  "Split each input element into tuple containing
  the min, the max, the letter, and the pw"
  [elem]
  (letfn [(str->num [str idx]
            (edn/read-string (nth str idx)))]
    (let [split-by-space (str/split elem #" ")
          min-and-max (str/split (first split-by-space) #"-")
          min (str->num min-and-max 0)
          max (str->num min-and-max 1)
          letter (-> (second split-by-space)
                     seq
                     first)
          pw (last split-by-space)]
      [min max letter pw])))

(defn solution-1
  []
  (reduce (fn [acc curr]
            (let [[min max letter pw] (tokenize-input curr)
                  letter-freq (-> (frequencies pw)
                                  (get letter))]
              (if (and (not (nil? letter-freq))
                       (<= min letter-freq)
                       (>= max letter-freq))
                (inc acc)
                acc)))
          0
          input))