version = "0.0.1-SNAPSHOT"

dependencies {
  implementation(project(":retrofit2-spring-boot-starter"))
  implementation("com.pauldaniv.promotion.yellowtaxi.facade:api:0.0.1-SNAPSHOT")
  implementation("org.springframework.boot:spring-boot-starter-web")
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
  }
  testImplementation("io.projectreactor:reactor-test")
  testImplementation("org.springframework.security:spring-security-test")
}
