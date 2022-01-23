package com.gearz.admin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

public abstract class AbstractExporter {
	public void setResponseHeader(HttpServletResponse response, String contentType, String extension, String prefix) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
		String timestamp = dateFormat.format(new Date());
		String csvFileName = prefix + timestamp + extension;
		
		response.setContentType(contentType);
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + csvFileName;
		response.setHeader(headerKey, headerValue);
	}
}
