(ns combloclj.core-test
  (:require [clojure.test :refer :all]
            [combloclj.core :refer :all]))

(deftest get-prefix-combinator-test
  (testing "正常系"
    (is (= "S" (get-prefix-combinator "Sxyz" ["S" "K" "I"])))
    (is (= \Z (get-prefix-combinator "Zxyz" ["S" "K" "I"])))
    ; (is (= "(abc)" (get-prefix-combinator "(abc)xyz" ["S" "K" "I"])))
    ))

(deftest get-prefix-bracket-combinator-test
  (testing "正常系"
    (is (= "(abc)" (get-prefix-bracket-combinator "(abc)xyz")))
    (is (= "(abc)" (get-prefix-bracket-combinator "(abc)")))
    (is (= "(ab(cd(e)))" (get-prefix-bracket-combinator "(ab(cd(e)))xyz")))
    (is (= nil (get-prefix-bracket-combinator "((abc)xyz")))
    (is (= nil (get-prefix-bracket-combinator "")))
    (is (= "()" (get-prefix-bracket-combinator "()")))
    ))
