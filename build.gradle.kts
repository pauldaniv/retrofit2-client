import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
  idea
  groovy
  `maven-publish`
  kotlin("jvm") version "1.3.50" apply false
  kotlin("plugin.spring") version "1.3.50" apply false
  id("org.springframework.boot") version "2.2.0.RELEASE" apply false
  id("io.spring.dependency-management") version "1.0.8.RELEASE" apply false
}

val packagesUrl = "https://maven.pkg.github.com/pauldaniv"

val githubUsr: String = findParam("gpr.usr", "USERNAME") ?: ""
val publishingKey: String? = findParam("gpr.key", "GITHUB_TOKEN")
val packageRepoKey = findParam("TOKEN", "PACKAGES_ACCESS_TOKEN") ?: publishingKey

subprojects {
  group = "com.pauldaniv.retrofit2.clients"

  apply(plugin = "idea")
  apply(plugin = "kotlin")
  apply(plugin = "groovy")
  apply(plugin = "maven-publish")
  apply(plugin = "org.springframework.boot")
  apply(plugin = "org.jetbrains.kotlin.jvm")
  apply(plugin = "io.spring.dependency-management")
  apply(plugin = "org.jetbrains.kotlin.plugin.spring")

  repositories {
    jcenter()
    mavenCentral()
    mavenLocal()
    maven {
      name = "GitHub-Bom-Repository"
      url = uri("$packagesUrl/bom-template")
      credentials {
        username = githubUsr
        password = packageRepoKey
      }
    }
  }

  dependencies {
//    implementation(platform("com.paul:bom-template:0.0.+"))
    implementation("org.springframework.boot:spring-boot-starter")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.asprise.ocr:java-ocr-api:15.3.0.3")
    implementation("com.google.guava:guava:29.0-jre")
    testImplementation("org.assertj:assertj-core")
    implementation("org.codehaus.groovy:groovy:2.5.6")

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
  }

  val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
  }

  publishing {
    repositories {
      maven {
        name = "GitHub-Publish-Repo"
        url = uri("$packagesUrl/${rootProject.name}")
        credentials {
          username = githubUsr
          password = publishingKey
        }
      }
    }

    publications {
      register<MavenPublication>("gpr") {
        from(components["java"])
        artifact(sourcesJar)
      }
    }
  }

  tasks.getByName<BootJar>("bootJar") {
    enabled = false

  }
  tasks.getByName<Jar>("jar") {
    enabled = true
  }

  idea {
    module {
      excludeDirs.addAll(listOf(
          file(".idea"),
          file(".gradle"),
          file("gradle"),
          file("build"),
          file("out")
      ))
    }
  }
  tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict")
      jvmTarget = "1.8"
    }
  }
  configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
  }
  tasks.withType<Test> {
    useJUnitPlatform()
  }
  configurations.all {
    resolutionStrategy.cacheDynamicVersionsFor(1, "minutes")
  }
}

fun findParam(vararg names: String): String? {
  for (name in names) {
    val param = project.findProperty(name) as String? ?: System.getenv(name)
    if (param != null) {
      return param
    }
  }
  return null
}
