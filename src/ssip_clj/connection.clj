(ns ssip-clj.connection
  (:require [clojure.core.async :refer [chan <! >! go]]
            [clojure.java.io :as io]
            [clojure.string :as s])
  (:import [java.io BufferedReader]
           [java.net Socket]))

(def eol "\r\n")

;; (defn ssip-connection [host port request response]
;;   (with-open [sock (Socket. host port)
;;               writer (io/writer sock)
;;               reader (io/reader sock)]
;;     (println "ssip-connection: socket connections established")
;;     (go-loop [v (<! request)]
;;       (when v
;;         (println (str "ssip-connection: got request " v))
;;         (>! response v)
;;         (recur (<! request))))
;;     (println "ssip-connection: exiting")))

(defn write-to [socket-out txt]
  (.write socket-out (str txt eol))
  (.flush socket-out))

(defn read-from [socket-in]
  (.readLine socket-in))

(defn do-set [cmd socket-out socket-in]
  (write-to socket-out (str "set " (:value cmd)))
  (read-from socket-in))

(defn do-speak [cmd socket-out socket-in]
  (write-to socket-out "speak")
  (println (read-from socket-in))
  (dorun (for [txt (:value cmd)]
           (write-to socket-out txt)))
  (write-to socket-out ".")
  (read-from socket-in))

(defn do-command [cmd socket-out socket-in]
  (write-to socket-out (str (name (:cmd cmd) " " (:value cmd))))
  (read-from socket-in))

(defn ssip-connection [host port request response]
  (go
    (with-open [sock (Socket. "localhost" 6560)
                socket-write (io/writer sock)
                socket-read (BufferedReader. (io/reader sock))]
      (println "ssip-connection: socket connections established")
      (loop [v (<! request)]
        (when v
          (case (:cmd v)
            :set (>! response (do-set v socket-write socket-read))
            :speak (>! response (do-speak v socket-write socket-read))
            :block (>! response (str "BLOCK not yet supported"))
            :list (>! response (str "LIST not yet supported"))
            (>! response (do-command v socket-write socket-read)))
          (recur (<! request))))
      (println "ssip-connection: exiting"))))
