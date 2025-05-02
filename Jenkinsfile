pipeline {
    agent any

    tools {
        jdk 'JDK_11'         // Jenkins'te tanımlı JDK adı
        maven 'Maven_3.8.1'  // Jenkins'te tanımlı Maven adı
    }

    environment {
        MAVEN_OPTS = "-Dmaven.test.failure.ignore=true"
    }

    stages {
        stage('Checkout') {
            steps {
                echo '🔄 Git deposundan kod çekiliyor...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '🔧 Proje derleniyor...'
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Testler çalıştırılıyor...'
                bat 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo '📦 Jar dosyası oluşturuluyor...'
                bat 'mvn package'
            }
        }

        stage('Test Sonuçları') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Artifacts') {
            steps {
                echo '📁 Artefakt arşivleniyor...'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline başarıyla tamamlandı.'
        }
        failure {
            echo '❌ Pipeline başarısız oldu.'
        }
    }
}
