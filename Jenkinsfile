pipeline {
   agent any

   stages {

      stage('Compile') {
         steps {
            echo 'Compiling the project .....'
            bat 'mvn compile'
         }
      }
      stage('Test') {
         steps {
            echo 'Testing the Code......'
            bat 'mvn test'
         }
      }
      stage('Build') {
         steps {
            echo 'Building the Code .....'
            bat 'mvn clean install -Dmaven.test.skip=true -o  '
         }
      }
      stage('Deploy') {
         steps {
            echo 'Deploying the project .....'
         }
      }
   }

}