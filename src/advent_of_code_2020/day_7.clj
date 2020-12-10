(ns advent-of-code-2020.day-7
  (:require [advent-of-code-2020.utils :as utils]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def input (utils/input->string-vec "day-7.txt"))

(defn- rule->map
  [rule]
  (let [[parent children] (str/split rule #"s contain ")
        bag-set (->> (str/split children #", ")
                     (reduce (fn [acc curr]
                               (let [num (-> (re-find #"[1-9]" curr)
                                             edn/read-string)
                                     bag (-> (subs curr 2)
                                             (str/replace #"bags\.|bags|bag." "bag"))]
                                 (if num
                                   (conj acc bag)
                                   acc)))
                             nil)
                     set)]
    {parent bag-set}))

(def full-map (apply merge (map rule->map input)))

(defn- shiny-in-key-path?
  [key]
  (let [children (get full-map key)
        shiny? (contains? children "shiny gold bag")]
    (or shiny?
        (boolean (some shiny-in-key-path? children)))))

(defn- solution-1
  []
  (->> (keys full-map)
       (map shiny-in-key-path?)
       (filter true?)
       count))