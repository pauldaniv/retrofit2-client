import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
  idea
  groovy
  `maven-publish`
  kotlin("jvm") version "1.3.72" apply false
  kotlin("plugin.spring") version "1.3.72" apply false
  id("org.springframework.boot") version "2.3.3.RELEASE" apply false
  id("io.spring.dependency-management") version "1.0.10.RELEASE" apply false
  id("io.freefair.lombok") version "5.1.1" apply false
}

val packagesUrl = "https://maven.pkg.github.com/pauldaniv"

val githubUsr: String = findParam("gpr.usr", "USERNAME") ?: ""
val publishKey: String? = findParam("gpr.key", "GITHUB_TOKEN")
val packageKey = findParam("TOKEN", "PACKAGES_ACCESS_TOKEN") ?: publishKey

subprojects {
  group = "com.pauldaniv.retrofit2"

  apply(plugin = "idea")
  apply(plugin = "kotlin")
  apply(plugin = "groovy")
  apply(plugin = "maven-publish")
  apply(plugin = "org.springframework.boot")
  apply(plugin = "org.jetbrains.kotlin.jvm")
  apply(plugin = "io.spring.dependency-management")
  apply(plugin = "org.jetbrains.kotlin.plugin.spring")
  apply(plugin = "io.freefair.lombok")

  repositories {
    jcenter()
    mavenCentral()
    mavenLocal()
    repoForName(
        "bom-template"
    ) {
      maven(it)
    }
  }

  dependencies {
//    implementation(platform("com.paul:bom-template:0.0.+"))
    implementation("org.springframework.boot:spring-boot-starter")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")
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
          password = publishKey
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

fun repoForName(vararg repos: String, repoRegistrar: (MavenArtifactRepository.() -> Unit) -> Unit) = repos.forEach {
  val maven: MavenArtifactRepository.() -> Unit = {
    name = "GitHubPackages"
    url = uri("$packagesUrl/$it")
    credentials {
      username = githubUsr
      password = packageKey
    }
  }
  repoRegistrar(maven)
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
