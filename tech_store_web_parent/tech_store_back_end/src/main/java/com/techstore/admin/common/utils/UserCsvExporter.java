package com.techstore.admin.common.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.techstore.common.entities.User;

public class UserCsvExporter extends AbstractExporter {
	public void export(List<User> userList, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "text/csv", ".csv");

		ICsvBeanWriter iCsvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);

		String[] csvHeader = { "User ID", "E-mail", "First Name", "Last Name", "Roles", "Enabled" };
		String[] fieldMapping = { "id", "email", "firstName", "lastName", "roles", "enabled" };

		iCsvBeanWriter.writeHeader(csvHeader);

		for (User user : userList) {
			iCsvBeanWriter.write(user, fieldMapping);
		}

		iCsvBeanWriter.close();
	}
}
