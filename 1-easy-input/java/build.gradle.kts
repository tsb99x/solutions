plugins {
    application
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src"))
        }
    }
}

application {
    mainClassName = "Main"
}

val run by tasks.getting(JavaExec::class) {
    standardInput = System.`in`
}

version = "1.0.0"