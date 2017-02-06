package com.elementalprime.bft.web.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import freemarker.template.TemplateModel;

public class FreemarkerUtil implements TemplateModel {
	public static String date(LocalDateTime d, String pattern) {
		return d == null ? null : d.format(DateTimeFormatter.ofPattern(pattern));
	}

	public static String time(LocalDateTime d) {
		return date(d, "yyyy-MM-dd HH:mm");
	}
}
