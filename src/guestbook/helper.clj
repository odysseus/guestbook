(ns guestbook.helper)

(defn extant? [x]
  (not (or
        (= x nil)
        (= x "")
        (= x 0)
        (and (coll? x) (empty? x)))))

(defn params [request]
  (filter #(extant? (val %)) request))
