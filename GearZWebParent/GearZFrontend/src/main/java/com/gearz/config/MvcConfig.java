package com.gearz.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	/*
	 * Public image directory on the file system.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		revealDirectory("../brand-logos", registry);
		revealDirectory("../category-images", registry);
		revealDirectory("../product-images", registry);
		revealDirectory("../site-logo", registry);
	}

	public void revealDirectory(String pathPattern, ResourceHandlerRegistry registry) {
		Path path = Paths.get(pathPattern);
		String absolutePath = path.toFile().getAbsolutePath();
		String logicalPath = pathPattern.replace("../", "") + "/**";
		registry.addResourceHandler(logicalPath).addResourceLocations("file:/" + absolutePath + "/");
	}

}
