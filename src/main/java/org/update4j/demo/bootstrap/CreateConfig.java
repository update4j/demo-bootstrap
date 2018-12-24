package org.update4j.demo.bootstrap;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.update4j.Configuration;
import org.update4j.FileMetadata;
import org.update4j.OS;

public class CreateConfig {

	public static void main(String[] args) throws IOException {
		Configuration config = Configuration.builder()
						.baseUri("${javafx.url}")
						.basePath("${user.dir}/bootstrap")
						.file(FileMetadata.readFrom("config/demo-bootstrap-1.0.0.jar")
										.classpath()
										.uri("http://docs.update4j.org/demo/bootstrap/demo-bootstrap-1.0.0.jar"))
						.files(FileMetadata.streamDirectory("config/javafx")
										.peek(f -> f.classpath())
										.peek(f -> f.ignoreBootConflict()) // if run with JDK 9/10
										.peek(f -> f.osFromFilename())
										.peek(f -> f.path((String) null)) // lets use uri only
										.peek(f -> {
											String module = f.getSource().getFileName().toString().substring(7);
											module = module.substring(0, module.indexOf("-"));
											
											f.uri(mavenUrl("org.openjfx", "javafx." + module,
													"11.0.1", f.getOs()));
										}))
						
						.property("default.launcher.main.class", "org.update4j.Bootstrap")
						.property("maven.central", MAVEN_BASE)
						.property("javafx.url", "${maven.central}org/openjfx/javafx-")
						.build();
						

		try (Writer out = Files.newBufferedWriter(Paths.get("config/setup.xml"))) {
			config.write(out);
		}
	}

	public static final String MAVEN_BASE = "https://repo1.maven.org/maven2/";

	public static String mavenUrl(String groupId, String artifactId, String version, OS os) {
		StringBuilder builder = new StringBuilder();
		builder.append(MAVEN_BASE);
		builder.append(groupId.replace('.', '/') +"/");
		builder.append(artifactId.replace('.', '-') + "/");
		builder.append(version + "/");
		builder.append(artifactId.replace('.', '-') + "-" + version);
		
		if(os != null) {
			builder.append('-' + os.getShortName());
		}
		
		builder.append(".jar");

		return builder.toString();
	}
	
	public static String mavenUrl(String groupId, String artifactId, String version) {
		return mavenUrl(groupId, artifactId, version, null);
	}
}
