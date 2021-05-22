(ns exp-multi-step-form.core
  (:gen-class))

(require '[clojure.string :as str])
(require '[clj-uuid :as uuid])
(require '[clj-yaml.core :as yaml])

(defn prompt-for-var [prompt]
  (print prompt)
  (flush)
  (read-line))

(defn write-record [id step data]
  (spit
   (str "record." id "." step ".yaml")
   (yaml/generate-string data :dumper-options {:flow-style :block})))

(defn get-group-id [] (str/upper-case (str/replace (uuid/v1) "-" "")))

;; Gather Data for step1 of the process
(defn step1 [id]
  {:id id
   :first-name (prompt-for-var "First Name ..................... ")
   :last-name (prompt-for-var  "Last Name ...................... ")})

;; Gather data for step2 of the process
(defn step2 [id]
  {:id id
   :num1 (prompt-for-var "Number 1 ...................... ")
   :num2 (prompt-for-var "Number 2 ...................... ")})

;; Here is an example flow
(defn example-flow [group-id]
  (write-record group-id "step1" (step1 group-id))
  (write-record group-id "step2" (step2 group-id)))

(defn -main
  "Welcome to my experiment"
  [& args]
  (example-flow (get-group-id)))
