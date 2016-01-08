(ns ssip-clj.connection
  (:require [clojure.core.async :refer [chan <! >! go]]
            [clojure.java.io :as io]
            [clojure.string :as s]
            [ssip-clj.commands :as cmd]
            [taoensso.timbre :as timbre])
  (:import [java.io BufferedReader]
           [java.net Socket]))

(def eol "\r\n")


(defn write-to [sock-out txt]
  (.write sock-out (str txt eol))
  (.flush sock-out))

(defn read-from [sock-in cmd]
  (loop [resp (.readLine sock-in)
         rslt []]
    (if (re-matches #"\d\d\d .*" resp)
      {:cmd (:cmd cmd)
       :result-code (cmd/return-code resp)
       :value (:value cmd)
       :result (conj rslt resp)}
      (recur (.readLine sock-in)
             (conj rslt resp)))))

(defn do-set [cmd sock-out sock-in]
  (write-to sock-out (str "set " (:value cmd)))
  (read-from sock-in cmd))

(defn do-speak [cmd sock-out sock-in]
  (write-to sock-out "speak")
  (let [rslt (read-from sock-in cmd)]
    (println (str "do-speak: " (:result rslt)))
    (when (= :ok (:result-code rslt))
      (doseq [txt (:value cmd)]
        (write-to sock-out txt))
      (write-to sock-out ".")
      (read-from sock-in cmd))))

(defn do-command [cmd sock-out sock-in]
  (println (str "do-command: cmd = " cmd " str = " (name (:cmd cmd)) " " (:value cmd)))
  (write-to sock-out (str (name (:cmd cmd)) " " (:value cmd)))
  (read-from sock-in cmd))

(defn do-list-output-modules [cmd sock-out sock-in]
  (write-to sock-out (:value cmd))
  (read-from sock-in cmd))

;; (defn do-list-output-modules [cmd sock-out sock-in]
;;   (write-to sock-out (:value cmd))
;;   (loop [resp (.readLine sock-in)
;;          rslt []]
;;     (println (str "do-list-output-modules: " resp))
;;     (if (re-matches #"\d\d\d .*" resp)
;;       {:cmd (:cmd cmd) :value (:value cmd)
;;        :result-code (cmd/return-code resp)
;;        :result (conj rslt resp)}
;;       (recur (.readLine sock-in)
;;              (conj rslt resp)))))


(defn do-quit [sock-out sock-in]
  (write-to sock-out "QUIT"))

(defn ssip-connection [host port request response]
  (go
    (with-open [sock (Socket. "localhost" 6560)
                socket-write (io/writer sock)
                socket-read (BufferedReader. (io/reader sock))]
      (loop [v (<! request)]
        (when v
          (case (:cmd v)
            :set (>! response (do-set v socket-write socket-read))
            :speak (>! response (do-speak v socket-write socket-read))
            :block (>! response (str "BLOCK not yet supported"))
            :list-output-modules (>! response (do-list-output-modules v
                                               socket-write socket-read))
            :list (>! response (str "LIST not yet supported"))
            :no-op (>! response v)
            :error (do (timbre/error (str "ssip-connection " v))
                       (>! response v))
            (>! response (do-command v socket-write socket-read)))
          (recur (<! request))))
      (do-quit socket-write socket-read))))
