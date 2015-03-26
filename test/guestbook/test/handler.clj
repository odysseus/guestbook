(ns guestbook.test.handler
  (:use clojure.test
        ring.mock.request
        guestbook.handler))

(deftest test-gets
  ;; Homepage
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "Guestbook"))))

  ;; About page
  (testing "about route"
    (let [response (app (request :get "/about"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "About"))))

  ;; User registration
  (testing "register route"
    (let [response (app (request :get "/register"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "Register"))))

  ;; User login
  (testing "login route"
    (let [response (app (request :get "/login"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "Login"))))

  ;; User logout
  (testing "logout route"
    (let [response (app (request :get "/logout"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "Logout"))))

  ;; Finally the catch-all 'not found' route
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
