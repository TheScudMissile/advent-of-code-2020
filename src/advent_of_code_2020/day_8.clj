(ns advent-of-code-2020.day-8
  (:require [advent-of-code-2020.utils :as utils]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def input (->> (utils/input->string-vec "day-8.txt")
                (map #(do [% (atom false)]))))

(def acc (atom 0))


(defn step-through
  [program-counter]
  (let [[curr already-called?] (nth input program-counter)
        [instruction amount] (str/split curr #" ")
        amount-num (edn/read-string amount)]
    (if @already-called?
      @acc
      (do
        (swap! already-called? not)
        (case instruction

            "acc"
            (do
              (swap! acc + amount-num)
              (step-through (inc program-counter)))

            "jmp"
            (step-through (+ program-counter amount-num))

            "nop"
            (step-through (inc program-counter)))))))

(defn solution-1
  []
  (step-through 0))
