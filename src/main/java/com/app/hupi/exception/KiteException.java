package com.app.hupi.exception;

public class KiteException {

	public static void throwException(String message) {
		throw new BusinessException(message);
	}
}
