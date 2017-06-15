package com.gkhn.classpath.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Application {
	public static void main(String[] args) {
		if (args.length > 0) {
			searchJarFiles(args[0]);
		} else {
		System.out.println("Please provide root path!");	
		
		}

	}

	public static void searchJarFiles(String rootPath) {
		Path rootDir = Paths.get(rootPath);
		try {
			Files.walk(rootDir).filter(path -> !Files.isDirectory(path))
					.filter(path -> path.toString().endsWith(".jar")).forEach(path -> listClassesInJar(path.toFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void listClassesInJar(File jarFile) {
		try {
			System.out.println("- " + jarFile.getAbsolutePath());
			ZipInputStream zip = new ZipInputStream(new FileInputStream(jarFile));
			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
				if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
					// This ZipEntry represents a class. Now, what class does it
					// represent?
					String className = entry.getName().replace('/', '.'); // including
																			// ".class"
					String fullName = className.substring(0, className.length() - ".class".length());
					System.out.println("\t" + fullName);
				}
			}
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
