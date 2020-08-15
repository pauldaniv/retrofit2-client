plugins {
    java
    idea
}

group = "com.pauldaniv.library.template"
version = "1.0-SNAPSHOT"


configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
idea {
    module {
        excludeDirs.add(file(".idea"))
    }
}
