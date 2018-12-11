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
		Configuration config = Configuration.builder().baseUri("http://docs.update4j.org/demo/bootstrap")
				.basePath("${user.dir}/bootstrap")
				.file(FileMetadata.readFrom("config/demo-bootstrap-1.0.0.jar").classpath()
						.path("demo-bootstrap-1.0.0.jar"))
				.files(FileMetadata.streamDirectory("config/javafx").peek(f -> f.classpath())
						.peek(f -> f.ignoreBootConflict()).peek(f -> {
					OS os = null;
					String filename = f.getSource().getFileName().toString();
					if(filename.endsWith("win.jar")) {
						os = OS.WINDOWS;
					} else if (filename.endsWith("mac.jar")) {
						os = OS.MAC;
					} else if (filename.endsWith("linux.jar")) {
						os = OS.LINUX;
					}
					
					f.os(os);
				})).property("default.launcher.main.class", "org.update4j.Bootstrap").build();

		try (Writer out = Files.newBufferedWriter(Paths.get("config/setup.xml"))) {
			config.write(out);
		}
	}
}
