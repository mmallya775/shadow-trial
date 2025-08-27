(ns core
  (:require
   [reagent.core :as r]
   [reagent.dom.client :as rdomc]))

;; App state atom
(def app-state (r/atom {:count 0}))


(defn first-component []
  [:div.first
   [:h1#heading "H1 element"]])

(defn another-comp []
  [:h2 "H2 element"])

(defn third-component
  []
  [:div
   [:p "This div contains a p element"]
   [:p "Lorem ipsum dolor sit amet consectetur adipisicing elit. Mollitia debitis ullam unde est adipisci quisquam consectetur deleniti, nisi laudantium corporis optio explicabo voluptas animi! Natus assumenda accusantium blanditiis quibusdam quam."]])

;; Main Reagent component
(defn home-page []
  [:div
   [first-component]
   [another-comp]
   ;;  [third-component]
   [third-component]])


;; Mount function for React 18+
(defonce root-ref (atom nil))

(defn mount-root []
  (let [container (.getElementById js/document "app")]
    (when (and container (nil? @root-ref))
      (reset! root-ref
              (rdomc/create-root container)))
    (when @root-ref
      (.render @root-ref (r/as-element [home-page])))))

;; Init function for Shadow CLJS
(defn ^:export init []
  (mount-root))

;; Hot reload hook - only re-render, don't create new root
(defn ^:dev/after-load reload! []
  (when @root-ref
    (.render @root-ref (r/as-element [home-page]))))