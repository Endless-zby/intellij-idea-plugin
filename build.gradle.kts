plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.21"
    id("org.jetbrains.intellij") version "1.13.3"
}

group = "cn.byzhao.generate"
version = "2.7-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // 其他依赖项...
    implementation("com.alibaba:easyexcel:3.1.0"){
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    annotationProcessor("com.alibaba:easyexcel:3.1.0"){
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    implementation("org.freemarker:freemarker:2.3.31")
    annotationProcessor("org.freemarker:freemarker:2.3.31")
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.2.5")
    type.set("IC") // Target IDE Platform
    plugins.set(arrayListOf("java", "gradle"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
//    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//        kotlinOptions.jvmTarget = "17"
//    }

    patchPluginXml {
        sinceBuild.set("211")
        untilBuild.set("232.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
