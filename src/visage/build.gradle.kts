import java.net.URI
import java.io.FileInputStream
import java.util.Properties

plugins {
    kotlin("multiplatform") version "1.4.0"
    id("org.jetbrains.dokka") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
    kotlin("kapt") version "1.4.0"
    `maven-publish`
    signing
}

group = "io.github.eagerlogic"
version = "0.2.20"

repositories {
    jcenter()
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
            compilations.all {
                kotlinOptions {
                    sourceMap = true
                    sourceMapEmbedSources = "always"
                }
            }
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

tasks {
    create<Jar>("javadocJar") {
        dependsOn(dokkaJavadoc)
        archiveClassifier.set("javadoc")
        from(dokkaJavadoc.get().outputDirectory)
    }
}

val ossUser = System.getenv("OSS_USER")
val ossPassword = System.getenv("OSS_PASSWORD")
extra["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
extra["signing.password"] = System.getenv("SIGNING_PASSWORD")
extra["signing.secretKeyRingFile"] = "/home/runner/.gnupg/secring.gpg"

val libraryVersion: String = version as String
val publishedGroupId: String = group as String
val artifactName = "visage"
val libraryName = "Visage"
val libraryDescription = "React like UI library for Kotlin JS"
val siteUrl = "https://github.com/EagerLogic/Visage"
val gitUrl = "https://github.com/EagerLogic/Visage"
val licenseName = "The Apache Software License, Version 2.0"
val licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"
val allLicenses = listOf("Apache-2.0")
val developerOrg = "EagerLogic Ltd."
val developerName = "David Ipacs"
val developerEmail = "david.ipacs@gmail.com"
val developerId = "dipacs"

project.group = publishedGroupId
project.version = libraryVersion

//signing {
//    sign(publishing.publications)
//}

afterEvaluate {
    configure<PublishingExtension> {
        publications.all {
            val mavenPublication = this as? MavenPublication
            mavenPublication?.artifactId =
                "${project.name}${"-$name".takeUnless { "kotlinMultiplatform" in name }.orEmpty()}"
        }
    }
}

publishing {
    publications.withType(MavenPublication::class) {
        groupId = publishedGroupId
        artifactId = artifactName
        version = libraryVersion

        artifact(tasks["javadocJar"])

        pom {
            name.set(libraryName)
            description.set(libraryDescription)
            url.set(siteUrl)

            licenses {
                license {
                    name.set(licenseName)
                    url.set(licenseUrl)
                }
            }
            developers {
                developer {
                    id.set(developerId)
                    name.set(developerName)
                    email.set(developerEmail)
                }
            }
            organization {
                name.set(developerOrg)
            }
            scm {
                connection.set(gitUrl)
                developerConnection.set(gitUrl)
                url.set(siteUrl)
            }
        }
    }

    repositories {
        maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
            name = "sonatype"
            credentials {
                username = ossUser
                password = ossPassword
            }
        }
    }
}

//nexusStaging {
//    username = ossUser
//    password = ossPassword
//    packageGroup = publishedGroupId
//}

//publishing {
//
//    repositories {
//        maven {
//            name = "Bintray"
//            url = URI("https://api.bintray.com/maven/dipacs/Visage/Visage/;publish=1;override=0")
//            credentials {
//                username = "dipacs"
//                password = System.getenv("BINTRAY_KEY")
//            }
//        }
//    }
//
//    publications {
//        filterIsInstance<MavenPublication>().forEach { publication ->
//            publication.pom {
//                name.set(project.name)
//                description.set(project.description)
//                packaging = "jar"
////                url.set("https://github.com/serpro69/${project.name}")
////                developers {
////                    developer {
////                        id.set("serpro69")
////                        name.set("Sergii Prodan")
////                        email.set("serpro@disroot.org")
////                    }
////                }
////                licenses {
////                    license {
////                        name.set("MIT")
////                        url.set("https://github.com/serpro69/${project.name}/blob/master/LICENCE.md")
////                    }
////                }
////                scm {
////                    connection.set("scm:git:https://github.com/serpro69/${project.name}.git")
////                    developerConnection.set("scm:git:git@github.com:serpro69/${project.name}.git")
////                    url.set("https://github.com/serpro69/${project.name}")
////                }
//            }
//        }
//    }
//}