plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.6"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.netflix.dgs.codegen") version "6.2.1"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "com.carmazing"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["netflixDgsVersion"] = "9.2.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")
	implementation("org.flywaydb:flyway-core:9.22.3")
	implementation("com.netflix.graphql.dgs:graphql-dgs-pagination")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")


	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework:spring-webflux")
	testImplementation("org.springframework.graphql:spring-graphql-test")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:${property("netflixDgsVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.generateJava {
	schemaPaths.add("${projectDir}/src/main/resources/graphql-client")
	packageName = "com.carmazing.product.codegen"
	generateClient = true
	typeMapping = mutableMapOf(
		"Date"             			to  "java.time.LocalDate",
	"DateTime"         			to  "java.time.LocalDateTime",
	"NonPositiveInt"   			to  "kotlin.Int",
	"NonPositiveFloat" 			to  "kotlin.Double",
	"NonNegativeInt"   			to  "kotlin.Int",
	"NonNegativeFloat" 			to  "kotlin.Double",
	"PositiveInt"      			to  "kotlin.Int",
	"PositiveFloat"    			to "kotlin.Double",
	"NegativeInt"      			to "kotlin.Int",
	"NegativeFloat"    			to "kotlin.Double",
	"ManufacturerConnection"    to "graphql.relay.SimpleListConnection<Manufacturer>",
	"SeriesConnection"    		to "graphql.relay.SimpleListConnection<Series>",
	"ModelConnection"    		to "graphql.relay.Connection<com.carmazing.product.datasource.entity.Models>",
	)
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
