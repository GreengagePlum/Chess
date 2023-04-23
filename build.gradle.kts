plugins {
    id("java")
    id("application")
}

group = "me.erken.efe.chess"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        from("MANIFEST.MF")
    }
}

application {
    mainClass.set("me.erken.efe.chess.Main")
}