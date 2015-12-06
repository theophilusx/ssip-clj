;;      Filename: commands.clj
;; Creation Date: Saturday, 28 November 2015 06:42 PM AEDT
;; Last Modified: Sunday, 06 December 2015 05:36 PM AEDT
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
    :unknown-code))

(def key-names #{"space" "underscore" "double-quote" "alt" "control"
                 "hyper" "meta" "shift" "super" "backspace" "break"
                 "delete" "down" "end" "enter" "escape" "f1" "f2"
                 "f3" "f4" "f5" "f6" "f7" "f8" "f9" "f10" "f11" "f12"
                 "f13" "f14" "f15" "f16" "f17" "f18" "f19" "f20" "f21"
                 "f22" "f23" "f24" "home" "insert" "kp-*" "kp-+" "kp--"
                 "kp-." "kp-/" "kp-0" "kp-1" "kp-2" "kp-3" "kp-4" "kp-5"
                 "kp-6" "kp-7" "kp-8" "kp-9" "kp-enter" "left" "menu" "next"
                 "num-lock" "pause" "print" "prior" "return" "right"
                 "scroll-lock" "tab" "up" "window"})

(def key-regexp #"[a-zA-Z0-9\!\@\#\$\%\^\&\*\(\)\_\-\+\=\[\]\{\}\;\:\\]")

(defn speak [v]
  (cond
    (nil? v) {:cmd :no-op :value nil}
    (string? v) {:cmd :speak :value [v]}
    (and  (vector? v)
          (= 0 (count v))) {:cmd :no-op :value nil}
    (vector? v) {:cmd :speak :value v}
    :else {:cmd :error :value {:cmd :speak :value v}}))

(defn speak-char [v]
  (cond
    (nil? v) {:cmd :no-op :value nil}
    (and (string? v)
         (= 0 (count v))) {:cmd :no-op :value nil}
    (and (string? v)
         (= " " v)) {:cmd :char :value "space"}
    (char? v) {:cmd :char :value v}
    (string? v) {:cmd :char :value (first v)}
    :else {:cmd :error :value {:cmd :char :value v}}))

(defn speak-key [v]
  (cond
    (contains? key-names v) {:cmd :key :value v}
    (re-matches key-regexp v) {:cmd :key :value v}
    :else {:cmd :error :value {:cmd :key :value v}}))

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
  (condp = v
    :important {:cmd :set :value "self priority important"}
    :message {:cmd :set :value "self priority message"}
    :text {:cmd :set :value "self priority text"}
    :notification {:cmd :set :value "self priority notification"}
    :progress {:cmd :set :value "self priority progress"}
    {:cmd :error :value {:cmd :set :value v}}))

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

(defn list-modules []
  {:cmd :list-output-modules
   :value "LIST OUTPUT_MODULES"})
