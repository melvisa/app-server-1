package com.app.hupi.util;

import com.app.hupi.exception.KiteException;
import com.app.hupi.vo.UserVO;

public class UserUtil {
	
	
	
	

	public static String getUserId() {
		return "1";
	}
	
	public static UserVO getUserVO() {
		UserVO vo=(UserVO) WebUtil.getSession().getAttribute("user");
		if(vo==null) {
			KiteException.throwException("请重新登录");
		}
		return vo;
	}
}
