;;      Filename: commands.clj
;; Creation Date: Saturday, 28 November 2015 06:42 PM AEDT
;; Last Modified: Sunday, 29 November 2015 05:53 PM AEDT
;;        Author: Tim Cross <theophilusx AT gmail.com>
;;   Description:
;;

(ns ssip-clj.commands)

(defn return-code [v]
  (condp = (first v)
    \1 :information
    \2 :ok
    \3 :server-error
    \4 :invalid-argument
    \5 :invalid-command
    \7 :event-notification
    :default :unknown-code))



(defn speak [v]
  {:cmd :speak
   :value v})

(defn speak-char [v]
  {:cmd :char
   :value v})

(defn speak-key [v]
  {:cmd :key
   :value v})

(defn sound-icon [v]
  {:cmd :sound_icon
   :value v})

(defn stop-speech []
  {:cmd :stop
   :value "self"})

(defn cancel-speech []
  {:cmd :cancel
   :value "self"})

(defn pause-speech []
  {:cmd :pause
   :value "self"})

(defn resume-speech []
  {:cmd :resume
   :value "self"})

(defn set-priority [v]
  {:cmd :set
   :value (str "self priority " v)})

(defn speech-block [v]
  {:cmd :block
   :value v})

(defn set-client-name [v]
  {:cmd :set
   :value (str "self client_name " v)})

(defn set-debug [v]
  {:cmd :set
   :value (str "all debug " v)})

(defn set-output-module [v]
  {:cmd :set
   :value (str "self output_module " v)})

(defn set-language [v]
  {:cmd :set
   :value (str "self language " v)})

(defn set-ssml-mode [v]
  {:cmd :set
   :value (str "self ssml_mode " v)})

(defn set-punctuation [v]
  {:cmd :set
   :value (str "self punctuation " v)})

(defn set-spelling [v]
  {:cmd :set
   :value (str "self spelling " v)})

(defn set-caps-recognition [v]
  {:cmd :set
   :value (str "self cap_let_recogn " v)})

(defn set-voice [v]
  {:cmd :set
   :value (str "self voice " v)})

(defn set-synth-voice [v]
  {:cmd :set
   :value (str "self synthesis_voice " v)})

(defn set-rate [v]
  {:cmd :set
   :value (str "self rate " v)})

(defn set-pitch [v]
  {:cmd :set
   :value (str "self pitch " v)})

(defn set-volume [v]
  {:cmd :set
   :value (str "self volume " v)})

(defn set-pause-context [v]
  {:cmd :set
   :value (str "self pause_context " v)})

(defn set-history [v]
  {:cmd :set
   :value (str "self history " v)})

(defn get-params [v]
  {:cmd :list
   :value v})

(defn set-notifications-all [v]
  {:cmd :set
   :value (str "self notification all " v)})

(defn quit-speech []
  {:cmd :quit
   :value ""})
