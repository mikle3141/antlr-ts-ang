plugins {
    id("org.jetbrains.intellij") version "1.17.4"
    java
    antlr
}

val pluginVersion: String by project
val ideaVersion: String by project
val antlr4Version: String by project

group = "antlr"
version = pluginVersion

tasks.wrapper {
    gradleVersion = "5.2.1"
}

tasks.compileJava {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}

intellij {
    version.set(ideaVersion)
    pluginName.set("antlr4-intellij-plugin-sample")
    downloadSources.set(true)
    updateSinceUntilBuild.set(false)
}

tasks.named("verifyPluginConfiguration") {
    enabled = false
}

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:$antlr4Version") {
        exclude(group = "com.ibm.icu", module = "icu4j")
    }
    implementation("org.antlr:antlr4-intellij-adaptor:0.1")
    testImplementation("junit:junit:4.11")
}

tasks.named<org.gradle.api.plugins.antlr.AntlrTask>("generateGrammarSource") {
    arguments = arguments + listOf("-package", "org.antlr.jetbrains.sample.parser", "-Xexact-output-dir")
}
