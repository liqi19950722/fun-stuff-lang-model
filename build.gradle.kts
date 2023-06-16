plugins {
    id("java-library")
}

group = "io.github.lq.fun.stuff.lang.mode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("jakarta.enterprise:jakarta.enterprise.lang-model:4.0.1")
    implementation("it.unimi.dsi:fastutil-core:8.5.12")

    testImplementation(platform("org.junit:junit-bom:5.9.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("jakarta.enterprise:cdi-tck-lang-model:4.0.10")
}

tasks.test {
    useJUnitPlatform()
}