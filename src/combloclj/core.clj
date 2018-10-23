(ns combloclj.core
  (:require [clojure.string :as str]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn parse-clcode
  "CLCodeを解析し、nodeのリストとして返却する"
  [clcode]
  (println clcode))

(defn get-prefix-combinator
  "先頭のコンビネータを取得する"
  [clcode combinators]
  (let [m (filter #(str/starts-with? clcode %) combinators)]
    (if (= (count m) 0)
      (first clcode)
      (first m))))

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

(defn -main
  "main func"
  []
  (println "hello"))
