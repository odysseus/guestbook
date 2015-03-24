(ns guestbook.routes.about
  (:require [compojure.core :refer :all]
            [guestbook.views.layout :as layout]))

(defn about []
  (layout/common
   [:h1 "About"]
   [:em [:p "\"Clojure all the way down.\""]]))

(defroutes about-routes
  (GET "/about" [] (about)))
