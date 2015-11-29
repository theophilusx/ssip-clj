;;      Filename: log.clj
;; Creation Date: Saturday, 28 November 2015 12:22 PM AEDT
;; Last Modified: Saturday, 28 November 2015 12:29 PM AEDT
;;        Author: Tim Cross <theophilusx AT gmail.com>
;;   Description:
;;

(ns ssip-clj.log
  (:require [environ.core :refer [env]]
            [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.3rd-party.rotor :as rotor]))

(defn init-logging []
  (timbre/merge-config!
    {:level     (if (env :dev) :trace :info)
     :appenders {:rotor (rotor/rotor-appender
                          {:path "ssip-clj.log"
                           :max-size (* 512 1024)
                           :backlog 10})}})
  (timbre/info "Logging started"))
