

var npmInstall = tasks.register<Exec>("npmInstall"){
    executable = "npm"
    args("install")
}

var npmBuild = tasks.register<Exec>("npmBuild"){
    dependsOn(npmInstall)
    executable = "ng"
    args("build")
}

var dockerBuild = tasks.register<Exec>("dockerBuild"){
    dependsOn(npmBuild)
    executable = "docker"
    args("build", "-t", "localhost:5001/frontend:latest", ".")
}

var dockerPush = tasks.register<Exec>("dockerPush"){
    dependsOn(dockerBuild)
    executable = "docker"
    args("push", "localhost:5001/frontend:latest")
}
