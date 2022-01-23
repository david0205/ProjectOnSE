package com.gearz.admin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	
	private static final Logger logger = LogManager.getLogger(FileUploadUtil.class);
	
	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		
		// If upload path doesn't exist, create directories
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("Could not save file: " + fileName, e);
		}
	}
	
	public static void cleanDir(String dir) {
		Path profilePicPath = Paths.get(dir);
		try {
			Files.list(profilePicPath).forEach(file -> {
				if (!Files.isDirectory(file)) {
					try {
						Files.delete(file);
					} catch (IOException e) {
						logger.error("Could not delete file: " + file);
					}
				}
			});
		} catch (IOException e) {
			logger.error("Could not list directory: " + profilePicPath);
		}
	}
	
	public static void removeDir(String dir) {
		cleanDir(dir);
		try {
			Files.delete(Paths.get(dir));
		} catch (IOException e) {
			logger.error("Could not remove directory: " + dir);
		}
	}
}
