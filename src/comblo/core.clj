(ns comblo.core
  (:require [clojure.string :as str]))

(defn get-prefix-bracket-combinator
  "先頭の、括弧でくくられた文字列を返す"
  [text]
  (loop [^String t text
         i (int 0)
         depth (int 0)]
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
  [^String clcode
   combinators]
  (let [m (filter #(str/starts-with? clcode %) combinators)]
    (if (not (= (count m) 0))
      (first m)
      (if (= \( (first clcode))
        (get-prefix-bracket-combinator clcode)
        (-> clcode first str)))))

(defn parse-combinators
  "CLCodeをコンビネータのリストに変換する"
  [clcode combinators]
  (loop [^String c clcode
         ret []]
    (if (empty? c)
      ret
      (let [pc (get-prefix-combinator c combinators)]
        (recur (-> pc
                   count
                   (drop c)
                   str/join)
               (conj ret pc))))))

(defn replace-template
  "CLCodeテンプレート文字列を置換する"
  [cs combinators]
  (cond
    (empty? cs) nil
    (empty? combinators) nil
    :else (let [cb (->> combinators
                        (filter #(= (:combinator %) (first cs)))
                        first)]
            (if (empty? cb)
              (str/join cs)
              (if (< (count cs) (:args-count cb))
                (str/join cs)
                (loop [s (map-indexed vector (rest cs))
                       ret (:format cb)]
                  (cond
                    (empty? s) ret
                    (empty? ret) (str/join cs)
                    :else (recur (rest s)
                                 (str/replace ret
                                              (format "{%d}" (-> s first first))
                                              (-> s first second)))))))))
  )

(defn calc-clcode
  "CLCodeを計算する"
  [^String clcode
   combinators]
  (let [m (filter #(str/starts-with? clcode %)
                  (map :combinator combinators))]
    (if (not (= (count m) 0))
      (let [c (first combinators)]
        (-> c
            count
            (drop clcode)
            parse-combinators)
        )
      nil)
    ))

(defn -main
  "main func"
  []
  (println "hello"))
