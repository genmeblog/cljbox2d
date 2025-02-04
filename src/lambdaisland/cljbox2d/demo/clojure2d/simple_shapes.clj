;; https://github.com/lambdaisland/cljbox2d/blob/main/src/lambdaisland/cljbox2d/demo/simple_shapes.cljc
(ns lambdaisland.cljbox2d.demo.clojure2d.simple-shapes
  (:require [lambdaisland.cljbox2d :as b]
            [lambdaisland.cljbox2d.clojure2d :as bc2d]
            [clojure2d.core :as c2d]
            [fastmath.random :as r]))

(def walls
  [{:id       :ground
    :position [6 9.8]
    :fixtures [{:shape [:rect 12 0.4]}]}
   {:id       :left-wall
    :position [0.2 5]
    :fixtures [{:shape [:rect 0.4 10]}]}
   {:id       :right-wall
    :position [11.8 5]
    :fixtures [{:shape [:rect 0.4 10]}]}])

(defn random-body []
  {:position [(r/drand 1 11) (r/drand -2 7)]
   :type :dynamic
   :fixtures [{:shape
               (rand-nth
                [[:circle (r/drand 0.2 0.6)]
                 [:rect (r/drand 0.4 1.2) (r/drand 0.4 1.2)]])
               :restitution 0.1
               :density 1
               :friction 3}]})

(def world (-> (b/world 0 1.0)
             (b/populate walls)
             (b/populate (repeatedly 50 random-body))))

(defn draw
  [canvas _ _ _]
  (b/step-world world)
  (-> canvas
      (c2d/set-background 161 165 134)
      (bc2d/draw! world)))

(def window (c2d/show-window {:canvas (c2d/canvas 1200 1000)
                            :draw-fn draw
                            :window-name "Simple shapes"}))

