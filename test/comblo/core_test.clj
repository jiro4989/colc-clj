(ns comblo.core-test
  (:require [clojure.test :refer :all]
            [comblo.core :refer :all]))

(def cs [
         {:combinator "S" :args-count 3 :format "{0}{2}({1}{2})"}
         {:combinator "K" :args-count 2 :format "{0}"}
         {:combinator "I" :args-count 1 :format "{0}"}
         ])
(def cns (map :combinator cs))

(deftest test-get-prefix-bracket-combinator
  (testing "get-prefix-bracket-combinatorは"
    (testing "先頭の括弧でくくられた文字列を返す"
      (is (= "(abc)" (get-prefix-bracket-combinator "(abc)xyz"))))

    (testing "先頭のネストした括弧の文字列は一番階層の浅い括弧を返す"
      (is (= "(ab(cd(e)))" (get-prefix-bracket-combinator "(ab(cd(e)))xyz"))))

    (testing "括弧の対応が不正な場合はnilを返す"
      (is (= nil (get-prefix-bracket-combinator "((abc)xyz"))))

    (testing "空データを渡されたとき、nilを返す"
      (is (= nil (get-prefix-bracket-combinator "")))
      (is (= nil (get-prefix-bracket-combinator nil)))
      (is (= nil (get-prefix-bracket-combinator [])))
      )

    (testing "括弧のとき、括弧を返す"
      (is (= "()" (get-prefix-bracket-combinator "()"))))

    ))

(deftest test-get-prefix-combinator
  (testing "get-prefix-combinatorは"
    (testing "先頭のコンビネータを返す"
      (is (= "S" (get-prefix-combinator "Sxyz" ["S" "K" "I"]))))

    (testing "先頭の括弧で括られたコンビネータを返す"
      (is (= "(ABC)" (get-prefix-combinator "(ABC)XYZ" ["S" "K" "I"]))))

    (testing "定義済みコンビネータに含まれない文字列の場合は、先頭1文字を返す"
      (is (= "x" (get-prefix-combinator "xyz" ["S" "K" "I"]))))

    (testing "定義済みコンビネータが空の場合は先頭1文字を返す"
      (is (= "Z" (get-prefix-combinator "Zxyz" []))))

    (testing "正義済みコンビネータには複数文字を指定できる"
      (is (= "Abc" (get-prefix-combinator "Abcxyz" ["Abc"]))))

    ))

(deftest test-parse-combinators
  (testing "parse-combinatorsは"
    (testing "文字列をコンビネータのベクタに変換する"
      (is (= ["S" "x" "y" "z"] (parse-combinators "Sxyz" cns))))

    (testing "括弧で括られた文字列をコンビネータとして扱う"
      (is (= ["(abc)" "(def)" "S"] (parse-combinators "(abc)(def)S" cns))))

    (testing "空文字列が渡されたとき、空ベクタを返す"
      (is (= [] (parse-combinators "" cns))))

    ))

(deftest test-replace-template
  (testing "replace-templateは"
    (testing "Sコンビネータ"
      (is (= "KI(II)" (replace-template ["S" "K" "I" "I"] cs))))

    (testing "Kコンビネータ"
      (is (= "I" (replace-template ["K" "I" "I"] cs))))

    (testing "Iコンビネータ"
      (is (= "I" (replace-template ["I" "I"] cs))))

    (testing "引数不足の場合はそのまま結合して返す"
      (is (= "SKI" (replace-template ["S" "K" "I"] cs))))

    (testing "定義済みコンビネータが存在しない場合は計算対象をそのまま結合して返す"
      (is (= "xyz" (replace-template ["x" "y" "z"] cs))))

    (testing "処理対象がない空の場合はnilを返す"
      (is (= nil (replace-template [] cs))))

    ))
