(ns advent-of-code-2020.day-3
  (:require [advent-of-code-2020.utils :as utils]))

(def input (utils/input->string-vec "day-3.txt"))
(def max-height-idx (- (count input) 1))
(def max-width-idx (-> (first input) count (- 1)))

(defn- get-val-at-coords
  [row-idx col-idx]
  (-> (get input row-idx)
      (get col-idx)))

(defn- get-updated-col-idx
  [col-idx num-right]
  (let [next-idx (+ col-idx num-right)]
    (if (> next-idx max-width-idx)
      (- next-idx (+ max-width-idx 1))
      next-idx)))

(defn- get-updated-num-trees
  [num-trees val-at-coords]
  (if (= val-at-coords \#)
    (inc num-trees)
    num-trees))

(defn- update-coords-and-tree-count
  ([num-down num-right]
   (update-coords-and-tree-count 0 num-down 0 num-right 0))
  ([row-idx num-down col-idx num-right num-trees]
   (let [val (get-val-at-coords row-idx col-idx)
         updated-row-idx (+ row-idx num-down)
         updated-col-idx (get-updated-col-idx col-idx num-right)
         updated-num-trees (get-updated-num-trees num-trees val)]
     (if (> updated-row-idx max-height-idx)
       updated-num-trees
       (recur updated-row-idx num-down updated-col-idx num-right updated-num-trees)))))

(defn solution-1
  []
  (update-coords-and-tree-count 1 3))

(defn solution-2
  []
  (let [d1-r1 (update-coords-and-tree-count 1 1)
        d1-r3 (solution-1)
        d1-r5 (update-coords-and-tree-count 1 5)
        d1-r7 (update-coords-and-tree-count 1 7)
        d2-r1 (update-coords-and-tree-count 2 1)]
    (* d1-r1 d1-r3 d1-r5 d1-r7 d2-r1)))