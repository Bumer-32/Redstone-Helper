import com.modrinth.minotaur.dependencies.ModDependency
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
	id("fabric-loom") version "1.8.9"
  	id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
  	id("org.jetbrains.kotlin.jvm") version "2.0.21"
	id("com.modrinth.minotaur") version "2.+"
}

version = project.property("mod_version").toString()
group = project.property("maven_group").toString()

base {
	archivesName.set("${project.property("archives_base_name")}-${project.property("minecraft_version")}")
}

repositories {
	maven("https://maven.wispforest.io")
	maven {
		name = "WorldEdit Maven"
		url = URI("https://maven.enginehub.org/repo/")
	}
}

modrinth {
	token.set(
		if (project.hasProperty("modrinthToken")) project.property("modrinthToken").toString()
		else System.getenv("MODRINTH_TOKEN")
	)
	projectId.set(project.property("id_main").toString())
	versionNumber.set(project.property("mod_version").toString())
	versionName.set("Redstone Helper ${project.property("mod_version")}")
	uploadFile.set(tasks.remapJar)
	// additionalFiles.set(listOf(remapSourcesJar))
	gameVersions.set(project.property("release_minecraft_versions").toString().split(" "))
	versionType.set(project.property("release_type").toString())
	loaders.set(listOf("fabric"))
	syncBodyFrom.set(rootProject.file("README.md").readText())
	changelog.set(rootProject.file("CHANGELOG.md").readText())
	dependencies.set(
		listOf(
			ModDependency(project.property("id_fabric_api").toString(), "required"),
			ModDependency(project.property("id_fabric_kotlin").toString(), "required"),
			ModDependency(project.property("id_owo").toString(), "required"),
			ModDependency(project.property("id_modmenu").toString(), "optional"),
			ModDependency(project.property("id_world_edit").toString(), "optional")
		)
	)

	tasks.named("modrinth").configure {
		dependsOn(tasks.named("modrinthSyncBody"))
	}
}

dependencies {
	// To change the versions, see the gradle.properties file
	minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
	mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
	modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
	modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")
	modImplementation("net.fabricmc:fabric-language-kotlin:${project.property("fabric_kotlin_version")}")

	implementation("org.mariuszgromada.math:MathParser.org-mXparser:${project.property("mxparser_version")}")

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${project.property("kotlinx_coroutines_version")}")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${project.property("kotlinx_serialization_version")}")

	modImplementation("io.wispforest:owo-lib:${project.property("owo_version")}")
	annotationProcessor("io.wispforest:owo-lib:${project.property("owo_version")}")
	include("io.wispforest:owo-sentinel:${project.property("owo_version")}")

	modImplementation("com.sk89q.worldedit:worldedit-fabric-mc${project.property("world_edit_version")}") {
		exclude(group = "it.unimi.dsi", module = "fastutil")
		exclude(group = "com.google.guava", module = "guava")
	}
}

tasks.withType<ProcessResources> {
	inputs.property("version", project.version)

	filesMatching("fabric.mod.json") {
		expand("version" to project.version)
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release.set(21)
}

tasks.withType<KotlinCompile>().configureEach {
	@Suppress("DEPRECATION")
	kotlinOptions {
		jvmTarget = "21"
	}
}

java {
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	// If you remove this line, sources will not be generated.
	// withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

tasks.named<Jar>("jar") {
	from("LICENSE")
}