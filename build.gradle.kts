plugins {
    id("java")
    id("application")
    id("idea")
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.26.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.erken.efe.chess"
version = "1.0.0"

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
    mainClass.set("me.erken.efe.chess.main.Main")
    mainModule.set("me.erken.efe.chess")
}

javafx {
    version = "17.0.7"
    modules("javafx.controls", "javafx.fxml", "javafx.graphics")
}

jlink {
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "Chess"
    }
}
