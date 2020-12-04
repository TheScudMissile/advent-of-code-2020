(ns advent-of-code-2020.utils
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn input->string-vec
  [filename]
  (->> (io/resource filename)
       slurp
       str/split-lines))