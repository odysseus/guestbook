(ns guestbook.test.handler
  (:use clojure.test
        ring.mock.request
        guestbook.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "Guestbook"))))

  (testing "about route"
    (let [response (app (request :get "/about"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "Clojure all the way down"))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
