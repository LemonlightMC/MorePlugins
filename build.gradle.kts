allprojects {
  version = "0.1.0"
  group = "com.lemonlightmc"
  description = "MorePlugins"
}

subprojects {
  apply(plugin = "java")
  apply(plugin = "java-library")
  apply(plugin = "maven-publish")

  // configure Java toolchain and artifact jars
  extensions.configure<org.gradle.api.plugins.JavaPluginExtension> {
      toolchain {
          languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(24))
      }
      withSourcesJar()
      //withJavadocJar()
  }

  extensions.configure<org.gradle.api.publish.PublishingExtension> {
      publications.create(
          "maven",
          org.gradle.api.publish.maven.MavenPublication::class
      ) {
          from(components["java"])
          groupId = project.group.toString()
          artifactId = project.name
          version = project.version.toString()
      }
  }
}