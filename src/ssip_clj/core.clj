;      Filename: core.clj
; Creation Date: Sunday, 14 September 2014 04:49 PM EST
; Last Modified: Sunday, 29 November 2015 04:51 PM AEDT>
;   Description:
;

(ns ssip-clj.core
  (:require [taoensso.timbre :as timbre]
            [environ.core :refer [env]]
            [ssip-clj.log :as log]
            [clojure.core.async :refer [chan <! >! <!! >!! go close!]]
            [ssip-clj.commands :as cmd]
            [ssip-clj.connection :refer [ssip-connection]]))

(def ssip-host (env :ssip-host))
(def ssip-port (env :ssip-port))

(def ssip-out (chan 10))
(def ssip-in (chan 10))

(defn -main [& args]
  (log/init-logging)
  (timbre/info (str "SSIP Server: " ssip-host ":" ssip-port))
  (ssip-connection ssip-host ssip-port ssip-out ssip-in)
  (>!! ssip-out (cmd/set-client-name "tcross:ssip-clj:default"))
  (>!! ssip-out (cmd/set-notifications-all "off"))
  (>!! ssip-out (cmd/speak ["This is liine 1" "This is line 2" "This is all"]))
  (println (str "main: got back " (<!! ssip-in)))
  (println (str "main: got back " (<!! ssip-in)))
  (println (str "main: got back " (<!! ssip-in)))
  (close! ssip-out)
  (println "main: closed ssip-out")
  (close! ssip-in)
  (println "main: closed ssip-in")
  (timbre/info "Program exiting normally"))
