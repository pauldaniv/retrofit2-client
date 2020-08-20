version = "1.2-SNAPSHOT"

dependencies {
  api("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.0")
  implementation("com.squareup.retrofit2:converter-jackson:2.9.0")
  implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.11.2")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
  implementation("org.springframework.cloud:spring-cloud-context:2.2.0.RELEASE")
  implementation("org.reflections:reflections:0.9.12")
  testImplementation("org.assertj:assertj-core")
  testImplementation("org.junit.jupiter:junit-jupiter")
}
