package com.app.hupi.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

public class LoggerUtil {

	public static void logger(String operate,String userId,Map<String,Object> paramInfo) {
		 Logger logger = Logger.getLogger(operate);
		 StringBuffer sb=new StringBuffer();
		 sb.append("操作用戶：【"+userId+"】、");
		 sb.append("操作：【"+operate+"】、");
		 sb.append("相关参数：【");
		 if(MapUtil.isNotEmpty(paramInfo)) {
			 Set<Entry<String,Object>> set= paramInfo.entrySet();
			 for(Entry<String,Object> entry:set) {
				 sb.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
			 }
		 }
		 sb.append("】");
		 logger.error(sb.toString());
	}
}
