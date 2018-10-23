(ns combloclj.core
  (:require [clojure.string :as str]))

(defn get-prefix-bracket-combinator
  "先頭の、括弧でくくられた文字列を返す"
  [text]
  (loop [t text
         i 0
         depth 0]
    (cond
      (empty? t) nil
      (< depth 0) nil
      (= \( (first t)) (recur (rest t)
                              (inc i)
                              (inc depth))
      (= \) (first t)) (let [d (dec depth)]
                         (if (= 0 d)
                           (str/join (take (inc i) text))
                           (recur (rest t)
                                  (inc i)
                                  d)))
      :else (recur (rest t)
                   (inc i)
                   depth)
      )))

(defn get-prefix-combinator
  "先頭のコンビネータを取得する"
  [clcode combinators]
  (let [m (filter #(str/starts-with? clcode %) combinators)]
    (if (not (= (count m) 0))
      (first m)
      (if (= \( (first clcode))
        (get-prefix-bracket-combinator clcode)
        (-> clcode first str)))))

(defn calc-clcode
  "CLCodeを計算する"
  [clcode combinators]
  "xz(yz)")

(defn -main
  "main func"
  []
  (println "hello"))
