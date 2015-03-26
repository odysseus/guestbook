(ns guestbook.routes.home
  (:require [compojure.core :refer :all]
            [guestbook.views.layout :as layout]
            [hiccup.form :refer :all]
            [guestbook.models.db :as db]
            [guestbook.helper :as util]))

(defn format-time [timestamp]
  (-> "dd/MM/yyyy"
      (java.text.SimpleDateFormat.)
      (.format timestamp)))

(defn show-guests []
  [:ul.guests
   (for [{:keys [message name timestamp]} (db/read-guests)]
     [:li
      [:blockquote message]
      [:p "-" [:cite name]]
      [:time (format-time timestamp)]])])

(defn home [& [name message error]]
  (layout/common
    [:h1 "Entries"]
    [:em [:p "Welcome to the guestbook"]]
    [:p error]

    ; Show the guests
    (show-guests)
    [:hr]

    ; Form to create a new message
    (form-to [:post "/"]
             [:p "Name:"]
             (text-field "name" name)
             [:p "Message:"]
             (text-area {:rows 10 :cols 40} "message" message)
             [:br]
             (submit-button "comment"))))

(defn save-message [name message]
  (cond
    (and (empty? name) (empty? message))
    (home name message "No data entered")

    (empty? name)
    (home name message "Name field has been let blank")

    (empty? message)
    (home name message "Message field has been let blank")

    :else
   (do
     (db/save-message name message)
     (home))))

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/" [name message] (save-message name message))
  (GET "/request" request (layout/common (for [[k v] request] [:p (str k " :: " v)]))))
