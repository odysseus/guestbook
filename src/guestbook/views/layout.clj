(ns guestbook.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn header [title]
  [:h1 title])

(defn footer []
  [:p [:hr][:em "That's all, folks"]])

(defn css-shim []
  (include-css "/css/screen.css"))

(defn standard-page [title & body]
  [:body (header title) body (footer)])

(defn common [title & body]
  (html5
    [:head
     [:title "Welcome to guestbook"]
     (css-shim)]
   (standard-page title body)))
