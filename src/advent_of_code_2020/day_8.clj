(ns advent-of-code-2020.day-8
  (:require [advent-of-code-2020.utils :as utils]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def acc (atom 0))

(defn- get-input
  []
  (->> (utils/input->string-vec "day-8.txt")
       (map #(do [% (atom false)]))))

(defn step-through
  [input pc solution-1?]
  (if-let [[curr already-called?] (nth input pc nil)]
    (let [[instruction amount] (str/split curr #" ")
          amount-num (edn/read-string amount)]
      (cond
        (and @already-called? solution-1?)
        @acc

        (and @already-called? (not solution-1?))
        nil

        :else
        (do
          (swap! already-called? not)
          (case instruction

            "acc"
            (do
              (swap! acc + amount-num)
              (step-through input (inc pc) solution-1?))

            "jmp"
            (step-through input (+ pc amount-num) solution-1?)

            "nop"
            (step-through input (inc pc) solution-1?)))))
    @acc))

(defn solution-1
  []
  (reset! acc 0)
  (step-through (get-input) 0 true))

(defn- get-command-indices
  [input command]
  (->> (map-indexed (fn [idx [line _]]
                      (when (= command (subs line 0 3))
                        idx))
                    input)
       (filter identity)))

(defn- switch-command
  [input idx new-command]
  (let [[command _] (nth input idx)
        updated-command (str new-command (subs command 3))]
    (assoc input idx [updated-command (atom false)])))

(defn solution-2
  []
  (reset! acc 0)
  (let [input (-> (get-input) vec)
        jmp-indices (get-command-indices input "jmp")
        nop-indices (get-command-indices input "nop")
        jmp->nop-results (map (fn [idx-to-switch]
                                (reset! acc 0)
                                (let [input (-> (get-input) vec)
                                      new-input (switch-command input idx-to-switch "nop")]
                                  (step-through new-input 0 false)))
                              jmp-indices)
        nop->jmp-results (map (fn [idx-to-switch]
                                (reset! acc 0)
                                (let [input (-> (get-input) vec)
                                      new-input (switch-command input idx-to-switch "jmp")]
                                  (step-through new-input 0 false)))
                              nop-indices)]
    (->> (concat jmp->nop-results nop->jmp-results)
         (filter identity)
         (first))))
