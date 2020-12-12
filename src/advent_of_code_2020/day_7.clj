(ns advent-of-code-2020.day-7
  (:require [advent-of-code-2020.utils :as utils]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def input (utils/input->string-vec "day-7.txt"))
(def target-bag "shiny gold bag")

(defn- rule->map
  [rule]
  (let [[parent children] (str/split rule #"s contain ")
        children-map (->> (str/split children #", ")
                     (reduce (fn [acc curr]
                               (let [num (-> (re-find #"[1-9]" curr)
                                             edn/read-string)
                                     bag (-> (subs curr 2)
                                             (str/replace #"bags\.|bags|bag." "bag"))]
                                 (if num
                                   (conj acc {bag num})
                                   acc)))
                             {}))]
    {parent children-map}))

(def full-map (apply merge (map rule->map input)))

(defn- shiny-in-key-path?
  [key]
  (let [children (-> (get full-map key) keys set)
        shiny? (contains? children target-bag)]
    (or shiny?
        (boolean (some shiny-in-key-path? children)))))

(defn solution-1
  []
  (->> (keys full-map)
       (map shiny-in-key-path?)
       (filter true?)
       count))

(defn- count-bags
  ([]
   (count-bags target-bag))
  ([key]
   (let [children (get full-map key)]
     (if (seq children)
       (->> (map (fn [[key val]]
                   (* val (count-bags key)))
                 children)
            (cons 1)
            (apply +))
       1))))

(defn solution-2
  []
  (- (count-bags) 1))
