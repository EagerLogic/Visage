import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

buildscript {
    dependencies {
        "classpath"("com.google.cloud.tools:appengine-gradle-plugin:2.1.0")
    }
}

plugins {
    kotlin("multiplatform") version "1.4.31"
    kotlin("plugin.serialization") version "1.4.20"
    kotlin("kapt") version "1.4.20"
    war
}
apply(plugin = "com.google.cloud.tools.appengine")

group = "io.github.eagerlogic"
version = "0.2.10"

val visageVersion = "0.2.11"

repositories {
    mavenLocal()
    jcenter()
    mavenCentral()
}

kotlin {
    jvm("backend") {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    js(LEGACY) {
        browser {
            binaries.executable()
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.eagerlogic:visage:$visageVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")


            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val backendMain by getting {
            dependencies {
                implementation("javax.servlet:javax.servlet-api:3.1.0")
                implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.21")
                implementation("com.google.appengine.tools:appengine-gcs-client:0.7")
                implementation("com.googlecode.objectify:objectify:5.1.22")
                implementation("org.jboss.resteasy:resteasy-servlet-initializer:3.1.4.Final")
                implementation("org.jboss.resteasy:resteasy-client:3.1.4.Final")
                implementation("org.jboss.resteasy:resteasy-jackson-provider:3.1.4.Final")
                //implementation("io.github.eagerlogic:visage-jvm:$visageVersion")
                configurations.get("kapt").dependencies.add(compileOnly("io.github.eagerlogic:visage-jvm:$visageVersion"))
            }
        }
        val backendTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            dependencies {
                //implementation("io.github.eagerlogic:visage-js:$visageVersion")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

tasks.getByName<org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack>("jsBrowserProductionWebpack") {
    outputFileName = "app.js"
}

tasks.getByName<Jar>("jsJar") {
    dependsOn(tasks.getByName("backendMainClasses"))
}

tasks.getByName<Jar>("backendJar") {
    dependsOn(tasks.getByName("jsBrowserProductionWebpack"))
}

tasks.getByName<War>("war") {
    dependsOn("backendJar")
    from(kotlin.targets["backend"].compilations["main"].output.allOutputs.files) {
        into("WEB-INF/classes")
    }
    from(kotlin.targets["backend"].compilations["main"].compileDependencyFiles) {
        into("WEB-INF/lib")
    }
    val jsBrowserProductionWebpack = tasks.getByName<org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack>("jsBrowserProductionWebpack")
    from(File(jsBrowserProductionWebpack.destinationDirectory, jsBrowserProductionWebpack.outputFileName))
    from(File(jsBrowserProductionWebpack.destinationDirectory, jsBrowserProductionWebpack.outputFileName + ".map"))
    from("/src/main/webapp/")
    include("**/*.*")
}

tasks.getByName("explodeWar") {
    dependsOn("war")
}

configure<com.google.cloud.tools.gradle.appengine.standard.AppEngineStandardExtension>() {
    deploy {
        projectId = "visageui"
        version = "0-2-10"
    }
    run {
        automaticRestart = true
        jvmFlags = mutableListOf<String>( "-Ddatastore.backing_store=/Users/dipacs/WORK/visagedoc_db.bin", "-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5010")
    }
}