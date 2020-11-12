package com.app.hupi.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

	public static List emptyList() {
		return new ArrayList<>();
	}

	public static boolean isEmpty(List<?> list) {
		return list == null || list.size() == 0;
	}

	public static boolean isNotEmpty(List<?> list) {
		if (list == null || list.size() == 0) {
			return false;
		}
		return true;
	}

	public static String stringListToString(List<String> list, String separator) {
		if (isEmpty(list)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(separator);
		for (String str : list) {
			sb.append(str).append(separator);
		}
		return sb.toString();
	}

}
