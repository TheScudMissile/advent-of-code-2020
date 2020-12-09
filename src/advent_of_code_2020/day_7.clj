(ns advent-of-code-2020.day-7
  (:require [advent-of-code-2020.utils :as utils]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def input (utils/input->string-vec "day-7.txt"))

(defn- rule->map
  [rule]
  (let [[parent children] (str/split rule #"s contain ")
        bag-coll (->> (str/split children #", ")
                      (map (fn [elem]
                             (let [num (-> (re-find #"[1-9]" elem)
                                           edn/read-string)
                                   bag (-> (subs elem 2)
                                           (str/replace #"s|\." ""))]
                               (when num
                                 {bag num}))))
                      (apply merge))]
    {parent bag-coll}))

(defn- build-map
  []
  (map rule->map input))
