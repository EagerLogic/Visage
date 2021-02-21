import java.net.URI

plugins {
    kotlin("multiplatform") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
    kotlin("kapt") version "1.4.0"
    `maven-publish`
}

group = "com.el"
version = "0.2.5"

repositories {
    mavenCentral()
}
kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    js {
        browser {
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
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.0")
                implementation("com.google.auto.service:auto-service:1.0-rc2")
                configurations.get("kapt").dependencies.add(compileOnly("com.google.auto.service:auto-service:1.0-rc2"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

publishing {

    repositories {
        maven {
            name = "Bintray"
            url = URI("https://api.bintray.com/maven/dipacs/Visage/Visage/;publish=1;override=0")
            credentials {
                username = "dipacs"
                password = System.getenv("BINTRAY_KEY")
            }
        }
    }

    publications {
        filterIsInstance<MavenPublication>().forEach { publication ->
            publication.pom {
                name.set(project.name)
                description.set(project.description)
                packaging = "jar"
//                url.set("https://github.com/serpro69/${project.name}")
//                developers {
//                    developer {
//                        id.set("serpro69")
//                        name.set("Sergii Prodan")
//                        email.set("serpro@disroot.org")
//                    }
//                }
//                licenses {
//                    license {
//                        name.set("MIT")
//                        url.set("https://github.com/serpro69/${project.name}/blob/master/LICENCE.md")
//                    }
//                }
//                scm {
//                    connection.set("scm:git:https://github.com/serpro69/${project.name}.git")
//                    developerConnection.set("scm:git:git@github.com:serpro69/${project.name}.git")
//                    url.set("https://github.com/serpro69/${project.name}")
//                }
            }
        }
    }
}