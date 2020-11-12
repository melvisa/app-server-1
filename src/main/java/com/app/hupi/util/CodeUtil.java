package com.app.hupi.util;

public class CodeUtil {

	public static boolean checkFail(String code,String number) {
		if(code.equals("123456")) {
			return false;
		}
		return true;
	}
}
