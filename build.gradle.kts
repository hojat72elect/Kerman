plugins {
    kotlin("jvm") version "2.2.21"
}

group = "com.kerman"
version = "0.0.1"

val lwjglVersion = "3.4.0-SNAPSHOT"
val lwjglNatives = "natives-windows"

repositories {
    mavenCentral()
    maven("https://central.sonatype.com/repository/maven-snapshots")
}

dependencies {

    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
    implementation("org.lwjgl:lwjgl")
    implementation("org.lwjgl:lwjgl-opengl")
    implementation("org.lwjgl:lwjgl-glfw")


    // for loading native libraries of different platforms.
    implementation("com.badlogicgames.gdx:gdx-jnigen-loader:2.5.0")

    // natives
    implementation ("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    implementation ("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)

    // test
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}