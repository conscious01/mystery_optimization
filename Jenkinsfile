pipeline {
    agent any
    stages {
        stage("检出") {
            steps {
                checkout([
                        $class           : 'GitSCM', branches: [[name: env.GIT_BUILD_REF]],
                        userRemoteConfigs: [[
                                                    url          : env.GIT_REPO_URL,
                                                    credentialsId: env.CREDENTIALS_ID
                                            ]]
                ])
            }
        }

        stage('构建') {
            steps {
                sh './gradlew clean && rm -rf ./app/build/'
                sh './gradlew assembleRelease'
            }
        }

        stage("APK 签名") {
            steps {
                signAndroidApks(
                        keyStoreId: env.ANDROID_CREDENTIAL_ID,
                        keyAlias: env.ANDROID_CREDENTIAL_ALIAS,
                        apksToSign: "app/build/outputs/apk/**/*.apk",
                        archiveSignedApks: false,
                        archiveUnsignedApks: false,
                        skipZipalign: true
                )
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'app/build/outputs/apk/**/*.apk', fingerprint: true
            }
        }

        stage('上传到 Generic') {
            steps {
                codingArtifactsGeneric(
                        credentialsId: '${env.CODING_ARTIFACTS_CREDENTIALS_ID}',
                        withBuildProps: true,
                        files: 'app/build/outputs/apk/**/*.apk',
                        repoName: 'android-release',
                        version: '${CI_BUILD_NUMBER}'
                )
            }
        }
    }
}