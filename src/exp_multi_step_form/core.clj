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
   (yaml/generate-string data :dumper-options {:flow-style :block})
   )
  )

(defn get-group-id [] (str/upper-case (str/replace (uuid/v1) "-" "")))

(defn step1 [id]
  {:id id
   :first-name (prompt-for-var "First Name ..................... ")
   :last-name (prompt-for-var  "Last Name ...................... ")})

(defn step2 [id]
  {:id id
   :num1 (prompt-for-var "Number 1 ...................... ")
   :num2 (prompt-for-var "Number 2 ...................... ")})




(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (def record-id (get-group-id))
  (write-record record-id "step1" (step1 record-id))
  (write-record record-id "step2" (step2 record-id))
  (println "Hello, World!"))
