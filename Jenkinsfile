node{
    // Mark the code checkout stage
    stage 'Stage Checkout'

    checkout scm

    statge 'Stage Build'

    echo "My branch is ${env.BRANCH_NAME}"

    echo "build debug"

    sh "./gradlew clean assembleDebug"


    stage 'Stage Archive'
    //tell Jenkins to archive the apks
    archiveArtifacts artifacts: 'app/build/outputs/apk/*.apk', fingerprint: true
}
