(ns guestbook.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn header []
  [:h1 "Guestbook"])

(defn footer []
  [:p [:hr][:em "That's all, folks"]])

(defn css-shim []
  (include-css "/css/screen.css"))

(defn standard-page [& body]
  [:body (header) body (footer)])

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to guestbook"]
     (css-shim)]
   (standard-page body)))
