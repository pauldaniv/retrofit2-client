plugins {
    java
    `maven-publish`
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("plugin.spring") version "1.3.50"

}

group = "com.pauldaniv.one"
version = "1.0-SNAPSHOT"

repositories {
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/pauldaniv/bom-seed")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GIT_HUB_TOKEN")
        }
    }
    jcenter()
}


dependencies {
    //    implementation(platform("com.fym:bom:1.0.4"))
    testCompile("junit:junit:4.12")
    testCompile("org.assertj:assertj-core:3.15.0")
}

depedencyManagement {
    imports {
        mavenBom = "com.fym:bom:1.0.4"
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
