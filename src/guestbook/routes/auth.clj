(ns guestbook.routes.auth
  (:require [compojure.core :refer [defroutes GET POST]]
            [guestbook.views.layout :as layout]
            [hiccup.form :refer
             [form-to label text-field password-field submit-button]]
            [noir.response :refer [redirect]]
            [noir.session :as session]))

(defn control [field name text]
  (list (label name text)
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

(defn login-page []
  (layout/common
   "Login"
   (form-to [:post "/login"]
            (control text-field :id "Screenname: ")
            (control password-field :pass "Password: ")
            (submit-button "Login"))))

(defroutes auth-routes
  (GET "/register" [_] (registration-page))
  (POST "/register" [id pass pass1]
        (if (= pass pass1)
          (redirect "/")
          (registration-page)))
  (GET "/login" [] (login-page))
  (POST "/login" [id pass]
        (session/put! :user id)
        (redirect "/"))
  (GET "/logout" []
       (layout/common
        "Logout"
        (form-to [:post "/logout"]
                 (submit-button "logout"))))
  (POST "/logout" []
        (session/clear!)
        (redirect "/")))
