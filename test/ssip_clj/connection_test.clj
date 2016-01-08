;;      Filename: connection_test.clj
;; Creation Date: Thursday, 10 December 2015 05:54 PM AEDT
;; Last Modified: Friday, 08 January 2016 07:52 PM AEDT
;;        Author: Tim Cross <theophilusx AT gmail.com>
;;   Description:
;;

(ns ssip-clj.connection-test
  (:require [clojure.test :refer :all]
            [ssip-clj.commands :as cmd]
            [environ.core :refer [env]]
            [clojure.core.async :refer [chan <! >! <!! >!! go close!]]
            [ssip-clj.connection :refer :all]))

(def ssip-host (env :ssip-host))
(def ssip-port (env :ssip-port))

(def ^:dynamic ssip-out nil)
(def ^:dynamic ssip-in nil)

(defn env-setup [test-fn]
  (binding [ssip-out (chan 2)
            ssip-in (chan 2)]
    (ssip-connection ssip-host ssip-port ssip-out ssip-in)
    (test-fn)
    (close! ssip-out)
    (close! ssip-in)))

(use-fixtures :once #'env-setup)

(deftest speech-connection
  (testing "speech server init"
    (>!! ssip-out (cmd/set-client-name "tcross:ssip-clj:default"))
    (let [rslt (<!! ssip-in)]
      (is (= :ok (:result-code rslt)))
      (is (= "208 OK CLIENT NAME SET" (last (:result rslt))))))
  (testing "basic speech"
    (>!! ssip-out (cmd/speak ["This is line 1" "This is line 2" " and line 3"]))
    (let [rslt (<!! ssip-in)]
      (is (= :ok (:result-code rslt)))
      (is (= "225 OK MESSAGE QUEUED" (last (:result rslt))))))
  (testing "speak characters"
    (>!! ssip-out (cmd/speak-char "a"))
    (let [rslt (<!! ssip-in)]
      (is (= :ok (:result-code rslt)))
      (is (= "225 OK MESSAGE QUEUED" (last (:result rslt)))))
    (>!! ssip-out (cmd/speak-char \A))
    (let [rslt (<!! ssip-in)]
      (is (= :ok (:result-code rslt)))
      (is (= "225 OK MESSAGE QUEUED" (last (:result rslt)))))
    (>!! ssip-out (cmd/speak-char \t))
    (let [rslt (<!! ssip-in)]
      (is (= :ok (:result-code rslt)))
      (is (= "225 OK MESSAGE QUEUED" (last (:result rslt)))))))
