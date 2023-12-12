plugins {
    kotlin("jvm") version "1.9.20"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.12.0")
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}
