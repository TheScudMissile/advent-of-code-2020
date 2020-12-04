(ns advent-of-code-2020.day-2
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [advent-of-code-2020.utils :as utils]))

(def input (utils/input->string-vec "day-2.txt"))

(defn- tokenize-input
  "Split each input element into tuple containing
  the min (or first idx), the max (or second idx),
  the letter, and the pw"
  [elem]
  (letfn [(str->num [str idx]
            (edn/read-string (nth str idx)))]
    (let [split-by-space (str/split elem #" ")
          min-and-max (str/split (first split-by-space) #"-")
          min-or-idx-1 (str->num min-and-max 0)
          max-or-idx-2 (str->num min-and-max 1)
          letter (-> (second split-by-space)
                     seq
                     first)
          pw (last split-by-space)]
      [min-or-idx-1 max-or-idx-2 letter pw])))

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

(defn solution-2
  []
  (reduce (fn [acc curr]
            (let [[idx-1 idx-2 letter pw] (tokenize-input curr)
                  letter-val-1 (nth pw (- idx-1 1))
                  letter-val-2 (nth pw (- idx-2 1))]
              (if (and (or (= letter-val-1 letter)
                           (= letter-val-2 letter))
                       (not= letter-val-1 letter-val-2))
                (inc acc)
                acc)))
          0
          input))