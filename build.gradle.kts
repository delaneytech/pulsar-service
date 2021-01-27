import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
}

group = "io.geoant.devenv.pulsar"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val pulsarVersion = "2.6.1"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

//dependencies {
//	implementation("org.springframework.cloud:spring-cloud-starter-config")
//}

dependencies {
	implementation("org.apache.pulsar:pulsar-client-original:$pulsarVersion")
	implementation("org.apache.pulsar:pulsar-broker:$pulsarVersion") {
		exclude("org.apache.logging.log4j")
		exclude("org.slf4j")
	}
}
configurations {
	all {
		exclude("javax.ws.rs", "jsr311-api")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
