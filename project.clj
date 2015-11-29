(defproject ssip-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [com.taoensso/timbre "4.1.4"]
                 [org.clojure/core.async "0.2.374"]
                 [environ "1.0.1"]]

  :plugins [[lein-environ "1.0.1"]]

  :min-lein-version "2.0.0"
  :main ssip-clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev [:project/dev :profiles/dev]
             :test [:project/test :profiles/test]
             :project/dev {:env {:dev true}}
             :project/test {:env {:test true}}
             :profiles/dev {}
             :profiles/test {}}
  :env {:ssip-host "localhost"
        :ssip-port 6560})
