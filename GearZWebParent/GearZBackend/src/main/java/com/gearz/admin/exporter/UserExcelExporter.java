package com.gearz.admin.exporter;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gearz.admin.AbstractExporter;
import com.gearz.common.entity.User;

public class UserExcelExporter extends AbstractExporter {
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	public UserExcelExporter() {
		workbook = new XSSFWorkbook();
	}
	
	private void writeHeader() {
		sheet = workbook.createSheet("Users");
		XSSFRow row = sheet.createRow(0);
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(14);
		cellStyle.setFont(font);
		
		createCell(row, 0, "User ID", cellStyle);
		createCell(row, 1, "Email", cellStyle);
		createCell(row, 2, "First name", cellStyle);
		createCell(row, 3, "Last name", cellStyle);
		createCell(row, 4, "Roles", cellStyle);
		createCell(row, 5, "Enabled", cellStyle);
	}
	
	private void createCell(XSSFRow row, int colIndex, Object value, CellStyle style) {
		XSSFCell cell = row.createCell(colIndex);
		sheet.autoSizeColumn(colIndex);
		
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);			
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);		
	}
	
	private void writeData(List<User> userList) {
		int rowIndex = 1;
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(13);
		cellStyle.setFont(font);
		
		for (User user : userList) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int colIndex = 0;
			
			createCell(row, colIndex++, user.getId(), cellStyle);
			createCell(row, colIndex++, user.getEmail(), cellStyle);
			createCell(row, colIndex++, user.getFirstName(), cellStyle);
			createCell(row, colIndex++, user.getLastName(), cellStyle);
			createCell(row, colIndex++, user.getRoles().toString(), cellStyle);
			createCell(row, colIndex++, user.isEnabled(), cellStyle);
		}
	}
	
	public void export(List<User> userList, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/octet-stream", ".xlsx", "users_");
		
		writeHeader();
		writeData(userList);
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		
		workbook.close();
		outputStream.close();
	}

}
