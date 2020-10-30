pipeline {
   agent any

   stages {
      stage('Build') {
         steps {
            echo 'Building the Code .....'
            bat 'mvn clean'
         }
      }
      stage('Test') {
         steps {
            echo 'Testing the Code......'
            bat 'mvn test'
         }
      }
      stage('Compile') {
         steps {
            echo 'Compiling the project .....'
            bat 'mvn compile'
         }
      }
      stage('Deploy') {
         steps {
            echo 'Deploying the project .....'
         }
      }
   }

}