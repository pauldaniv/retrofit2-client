plugins {
    java
}

group = "com.pauldaniv.two"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(project(":first"))
    testCompile("junit:junit:4.12")
    testCompile("org.assertj:assertj-core:3.15.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    useJUnit()
    maxHeapSize = "1G"
}
