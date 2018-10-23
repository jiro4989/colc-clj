(ns combloclj.core-test
  (:require [clojure.test :refer :all]
            [combloclj.core :refer :all]))

(deftest test-get-prefix-bracket-combinator
  (testing "正常系"
    (is (= "(abc)" (get-prefix-bracket-combinator "(abc)xyz")))
    (is (= "(abc)" (get-prefix-bracket-combinator "(abc)")))
    (is (= "(ab(cd(e)))" (get-prefix-bracket-combinator "(ab(cd(e)))xyz")))
    (is (= nil (get-prefix-bracket-combinator "((abc)xyz")))
    (is (= nil (get-prefix-bracket-combinator "")))
    (is (= "()" (get-prefix-bracket-combinator "()")))
    ))

(deftest test-get-prefix-combinator
  (testing "正常系"
    (is (= "S" (get-prefix-combinator "Sxyz" ["S" "K" "I"])))
    (is (= "(ABC)" (get-prefix-combinator "(ABC)XYZ" ["S" "K" "I"])))
    (is (= "Z" (get-prefix-combinator "Zxyz" ["S" "K" "I"])))
    (is (= "Z" (get-prefix-combinator "Zxyz" [])))
    (is (= "Abc" (get-prefix-combinator "Abcxyz" ["Abc"])))
    ))

(deftest test-calc-clcode
  (testing "正常系"
    (is (= "xz(yz)" (calc-clcode "Sxyz" [{:combinator "S"
                                          :args-count 3
                                          :format "{0}{2}({1}{2})"}
                                         ])))
    ))
