(ns guestbook.routes.auth
  (:require [compojure.core :refer [defroutes GET POST]]
            [guestbook.views.layout :as layout]
            [guestbook.models.db :as db]
            [hiccup.form :refer
             [form-to label text-field password-field submit-button]]
            [noir.response :refer [redirect]]
            [noir.session :as session]
            [noir.validation
             :refer [rule errors? has-value? on-error]]
            [noir.util.crypt :as crypt]))

(defn format-error [[error]]
  [:p.error error])

(defn control [field name text]
  (list
   (on-error name format-error)
   (label name text)
   [:br]
   (field name)
   [:br]))

(defn registration-page []
  (layout/common
   "Register"
   (form-to [:post "/register"]
            (control text-field :id "Screenname: ")
            (control password-field :pass "Password: ")
            (control password-field :pass1 "Retype Password: ")
            (submit-button "Create Account"))))

(defn handle-registration [id pass pass1]
  (rule (= pass pass1)
        [:pass "Passwords do not match"])
  (if (errors? :pass)
    (registration-page)
    (do
      (db/add-user {:id id :pass (crypt/encrypt pass)})
      (redirect "/login"))))

(defn login-page []
  (layout/common
   "Login"
   (form-to [:post "/login"]
            (control text-field :id "Screenname: ")
            (control password-field :pass "Password: ")
            (submit-button "Login"))))

(defn handle-login [id pass]
  (let [user (db/get-user id)]
    (rule (has-value? id)
          [:id "Screen name is required"])
    (rule (has-value? pass)
           [:pass "Password is required"])
    (rule (and user (crypt/compare pass (:pass user)))
          [:pass "Invalid password"])

    (if (errors? :id :pass)
      (login-page)
      (do
        (session/put! :user id)
        (redirect "/")))))

(defroutes auth-routes
  (GET "/register" [_] (registration-page))
  (POST "/register" [id pass pass1]
        (handle-registration id pass pass1))

  (GET "/login" [] (login-page))
  (POST "/login" [id pass]
        (handle-login id pass))

  (GET "/logout" []
       (layout/common
        "Logout"
        (form-to [:post "/logout"]
                 (submit-button "logout"))))
  (POST "/logout" []
        (session/clear!)
        (redirect "/")))
