package com.techstore.admin.common.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.techstore.common.utils.Constant;

public class AbstractExporter {
	public void setResponseHeader(HttpServletResponse httpServletResponse, String contentType, String extension)
			throws IOException {
		DateFormat dateFormatter = new SimpleDateFormat(Constant.YYYY_MM_DD_HH_MM_SS);
		String timestamp = dateFormatter.format(new Date());
		String fileName = "users_" + timestamp + extension;

		httpServletResponse.setContentType(contentType);
		httpServletResponse.setCharacterEncoding(Constant.UTF8);

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;
		httpServletResponse.setHeader(headerKey, headerValue);
	}
}
