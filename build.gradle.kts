plugins {
    id("org.jetbrains.intellij.platform") version "2.6.0"
    java
    antlr
}

val pluginVersion: String by project
val ideaVersion: String by project
val antlr4Version: String by project

group = "antlr"
version = pluginVersion

tasks.wrapper {
    gradleVersion = "8.13"
}

tasks.compileJava {
    sourceCompatibility = JavaVersion.VERSION_17.toString()
    targetCompatibility = JavaVersion.VERSION_17.toString()
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        create(org.jetbrains.intellij.platform.gradle.IntelliJPlatformType.IntellijIdeaUltimate, ideaVersion)
    }
    antlr("org.antlr:antlr4:$antlr4Version") {
        exclude(group = "com.ibm.icu", module = "icu4j")
    }
    implementation("org.antlr:antlr4-intellij-adaptor:0.1")
    testImplementation("junit:junit:4.13.2")
}

intellijPlatform {
    pluginConfiguration {
        name.set("antlr4-intellij-plugin-sample")
        version.set(pluginVersion)
        ideaVersion {
            untilBuild.set(provider { null })
        }
    }
}

tasks.named("verifyPluginProjectConfiguration") {
    enabled = false
}

tasks.named<org.gradle.api.plugins.antlr.AntlrTask>("generateGrammarSource") {
    arguments = arguments + listOf("-package", "org.antlr.jetbrains.sample.parser", "-Xexact-output-dir")
}
