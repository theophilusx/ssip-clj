;      Filename: core.clj
; Creation Date: Sunday, 14 September 2014 04:49 PM EST
; Last Modified: Sunday, 22 January 2017 05:58 PM AEDT>
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

(defn -main [& args]
  (let [ssip-out (chan 2)
        ssip-in (chan 2)]
    (log/init-logging)
    (timbre/info (str "SSIP Server: " ssip-host ":" ssip-port))
    (ssip-connection ssip-host ssip-port ssip-out ssip-in)
    (>!! ssip-out (cmd/set-client-name "tcross:ssip-clj:default"))
    (>!! ssip-out (cmd/set-notifications-all "off"))
    (>!! ssip-out (cmd/speak ["This is line 1" "This is line 2" "This is all"]))
    (>!! ssip-out (cmd/list-modules))
    (println (str "main: got back " (<!! ssip-in)))
    (println (str "main: got back " (<!! ssip-in)))
    (println (str "main: got back " (<!! ssip-in)))
    (println (str "main: got back " (<!! ssip-in)))
    (close! ssip-out)
    (close! ssip-in)
    (timbre/info "Program exiting normally")))
