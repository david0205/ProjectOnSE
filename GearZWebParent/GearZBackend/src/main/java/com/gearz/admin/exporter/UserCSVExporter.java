package com.gearz.admin.exporter;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.gearz.admin.AbstractExporter;
import com.gearz.common.entity.User;

public class UserCSVExporter extends AbstractExporter {

	public void export(List<User> userList, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "text/csv", ".csv", "users_");
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader = {"User ID", "Email", "First name", "Last name", "Roles", "Enabled"};
		String[] fieldMapping = {"id", "email", "firstName", "lastName", "roles", "enabled"};
		
		csvWriter.writeHeader(csvHeader);
		
		for (User user : userList) {
			csvWriter.write(user, fieldMapping);
		}
		
		csvWriter.close();
	}
}
