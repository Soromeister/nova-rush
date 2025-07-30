plugins {
    id("java")
    id("idea")
    id("groovy")
    id("io.micronaut.application") version "4.5.4"
    id("com.gradleup.shadow") version "8.3.7"
    id("io.micronaut.aot") version "4.5.4"
}

version = "0.1"
group = "com.gabannicusor"

repositories {
    mavenCentral()
}

val log4j2Version = project.properties.get("log4j2Version")
val micronautEclipseStoreVersion = project.properties.get("micronautEclipseStoreVersion")
val eclipseStoreVersion = project.properties.get("eclipseStoreVersion")
val eclipseCollectionsVersion = project.properties.get("eclipseCollectionsVersion")
val googleJavaFormatVersion = project.properties.get("googleJavaFormatVersion")
val jacksonDatabindVersion = project.properties.get("jacksonDatabindVersion")
var instancioVersion = project.properties.get("instancioVersion")
val guavaVersion = project.properties.get("guavaVersion")
val fakerVersion = project.properties.get("fakerVersion")
val lombokVersion = project.properties.get("lombokVersion")
val tsIdVersion = project.properties.get("tsIdVersion")

dependencies {
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.eclipsestore:micronaut-eclipsestore-annotations")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    annotationProcessor("io.micronaut.spring:micronaut-spring-annotation")
    annotationProcessor("io.micronaut.spring:micronaut-spring-boot-annotation")
    annotationProcessor("io.micronaut.spring:micronaut-spring-web-annotation")
    implementation("io.micronaut:micronaut-http-server")
    implementation("io.micronaut.eclipsestore:micronaut-eclipsestore")
    implementation("io.micronaut.eclipsestore:micronaut-eclipsestore-annotations")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("io.hypersistence:hypersistence-tsid:${tsIdVersion}")
    implementation("org.apache.logging.log4j:log4j-api")
    implementation(platform("org.apache.logging.log4j:log4j-bom:2.24.3"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("io.micronaut:micronaut-http-client")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("io.micronaut.spring:micronaut-spring-boot")
    runtimeOnly("io.micronaut.spring:micronaut-spring-web")
    implementation("org.eclipse.collections:eclipse-collections:${eclipseCollectionsVersion}")
    implementation("org.eclipse.serializer:configuration:${eclipseStoreVersion}")
    implementation("org.eclipse.serializer:serializer:${eclipseStoreVersion}")
    implementation("org.eclipse.serializer:persistence:${eclipseStoreVersion}")
    implementation("org.eclipse.serializer:persistence-binary-jdk17:${eclipseStoreVersion}")
    implementation("org.eclipse.store:storage-embedded:${eclipseStoreVersion}")
    implementation("org.eclipse.store:storage-embedded-configuration:${eclipseStoreVersion}")
    implementation("org.eclipse.serializer:codegen-entity:${eclipseStoreVersion}")
    runtimeOnly("org.apache.logging.log4j:log4j-core")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl")
    runtimeOnly("org.yaml:snakeyaml")
    testAnnotationProcessor("io.micronaut.spring:micronaut-spring-boot-annotation")
    testAnnotationProcessor("io.micronaut.spring:micronaut-spring-web-annotation")
    testImplementation("io.micronaut:micronaut-http-client")
    developmentOnly("io.micronaut.eclipsestore:micronaut-eclipsestore-rest")
}


application {
    mainClass = "com.gabannicusor.Application"
}

java {
    sourceCompatibility = JavaVersion.VERSION_23
    targetCompatibility = JavaVersion.VERSION_23
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

graalvmNative.toolchainDetection = true

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.gabannicusor.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = true
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = false
    }
}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "23"
}
