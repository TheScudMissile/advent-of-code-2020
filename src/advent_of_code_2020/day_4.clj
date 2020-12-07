(ns advent-of-code-2020.day-4
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def input (-> (io/resource "day-4.txt")
                slurp
               (str/split #"\n\n")))

(def regex-vec [#"ecl" #"pid" #"eyr" #"hcl" #"byr" #"iyr" #"hgt"])

(defn- all-fields-present?
  [str-to-check]
  (let [results (map #(re-find % str-to-check) regex-vec)]
    (boolean (every? #(not (nil? %)) results))))

(defn- reduce-input-with-fn
  [f]
  (reduce (fn [acc curr]
            (if (f curr)
              (+ acc 1)
              acc))
          0
          input))

(defn solution-1
  []
  (reduce-input-with-fn all-fields-present?))

(defn- in-range?
  [val max min]
  (try
    (let [num (edn/read-string val)]
      (and (>= max num) (<= min num)))
    (catch Exception e
      false)))

(defn- valid-height?
  [val]
  (try
    (let [unit (re-find #"cm|in" val)
          num (when unit
                (-> (subs val 0 (- (count val) 2))
                    (edn/read-string)))]
      (if (= "cm" unit)
        (and (>= 193 num) (<= 150 num))
        (and (>= 76 num) (<= 59 num))))
    (catch Exception e
      false)))

(defn- valid-passport?
  [val]
  (try
    (if (= 9 (count val))
      (-> (edn/read-string val)
          boolean)
      false)
    (catch Exception e
      false)))

(defn- valid-hair-color?
  [val]
  (letfn [(is-7? [str]
            (= 7 (count str)))]
    (boolean (and (is-7? val)
                  (is-7? (re-find #"#[0-9a-f]{1,6}" val))))))

(defn- valid-field?
  [str]
  (let [[key val] (str/split str #":")]
    (case key
      "ecl"
      (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} val)

      "pid"
      (valid-passport? val)

      "eyr"
      (in-range? val 2030 2020)

      "hcl"
      (valid-hair-color? val)

      "byr"
      (in-range? val 2002 1920)

      "iyr"
      (in-range? val 2020 2010)

      "hgt"
      (valid-height? val)

      "cid"
      true

      false)))

(defn- all-fields-valid?
  [str-to-check]
  (when (all-fields-present? str-to-check)
    (let [tokenized (str/split str-to-check #"(\n| )")
          result-coll (map valid-field? tokenized)]
      (println tokenized "resulted in " result-coll "which is " (every? true? result-coll))
      (println)
      (every? true? result-coll))))

(defn solution-2
  []
  (reduce-input-with-fn all-fields-valid?))