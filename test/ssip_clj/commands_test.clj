;;      Filename: commands_test.clj
;; Creation Date: Tuesday, 01 December 2015 07:55 AM AEDT
;; Last Modified: Sunday, 06 December 2015 10:32 AM AEDT
;;        Author: Tim Cross <theophilusx AT gmail.com>
;;   Description:
;;

(ns ssip-clj.commands-test
  (:require [clojure.test :refer :all]
            [ssip-clj.commands :refer :all]))

(deftest return-codes
  (testing "return-code"
    (is (= :information (return-code "100-some value")))
    (is (= :ok (return-code "200-some value")))
    (is (= :server-error (return-code "300-some value")))
    (is (= :invalid-argument (return-code "400-some value")))
    (is (= :invalid-command (return-code "500-some value")))
    (is (= :unknown-code (return-code "600-some value")))
    (is (= :event-notification (return-code "700-some value")))
    (is (= :unknown-code (return-code "a bad value")))))

(deftest speak-command
  (testing "Testing speak command"
    (let [t1 (speak ["This is line 1" "This is line 2"])
          t2 (speak ["This is it"])
          t3 (speak "Hello")
          t4 (speak [])
          t5 (speak [""])
          t6 (speak 4)
          t7 (speak :foo)]
      (is (= :speak (:cmd t1)))
      (is (vector? (:value t1)))
      (is (= 2 (count (:value t1))))
      (is (string? (first (:value t1))))
      (is (= :speak (:cmd t2)))
      (is (vector? (:value t2)))
      (is (= 1 (count (:value t2))))
      (is (string? (first (:value t2))))
      (is (= :speak (:cmd t3)))
      (is (vector? (:value t3)))
      (is (= 1 (count (:value t3))))
      (is (string? (first (:value t3))))
      (is (= :no-op (:cmd t4)))
      (is (nil? (:value t4)))
      (is (= 0 (count (:value t4))))
      (is (= :speak (:cmd t5)))
      (is (vector? (:value t5)))
      (is (= 1 (count (:value t5))))
      (is (string? (first (:value t5))))
      (is (= :error (:cmd t6)))
      (is (= :error (:cmd t7))))))

(deftest speak-char-command
  (testing "Testing speak-char command"
    (let [t1 (speak-char "")
          t2 (speak-char "a")
          t3 (speak-char 'a')
          t4 (speak-char \\)
          t5 (speak-char \A)
          t6 (speak-char [])]
      (is (= :no-op (:cmd t1)))
      (is (= :char (:cmd t2)))
      (is (= \a (:value t2)))
      (is (= :error (:cmd t3)))
      (is (= :char (:cmd t4)))
      (is (= \\ (:value t4)))
      (is (= :char (:cmd t5)))
      (is (= \A (:value t5)))
      (is (= :error (:cmd t6))))))
