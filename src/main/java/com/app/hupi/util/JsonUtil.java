package com.app.hupi.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

	
	
	   public static String toJson(Object object) {
		   if(object==null) {
			   return "NULL";
		   }
		   String json=JSON.toJSONString(object);
		   return json;
	   }
	    /**
	     * Json字符串转换成JSONObject(不抛错)
	     * @param text
	     * @return
	     */
	    public static JSONObject parseObject(String text) {
	        try {
	            return JSON.parseObject(text);
	        }
	        catch (JSONException e) {
	        }
	        return null;
	    }

	    /**
	     * Json字符串转换成对象(不抛错)
	     * @param text
	     * @param clazz
	     * @return
	     */
	    public static <T> T parseObject(String text, Class<T> clazz) {
	        try {
	            return JSON.parseObject(text, clazz);
	        }
	        catch (JSONException e) {
	        }
	        return null;
	    }

	    /**
	     * Json字符串转换成JSONArray(不抛错)
	     * @param text
	     * @return
	     */
	    public static JSONArray parseArray(String text) {
	        try {
	            return JSON.parseArray(text);
	        }
	        catch (JSONException e) {
	        }
	        return new JSONArray();
	    }

	    /**
	     * Json字符串转换成数组(不抛错)
	     * @param text
	     * @param clazz
	     * @return
	     */
	    public static <T> List<T> parseArray(String text, Class<T> clazz) {
	        try {
	            return JSON.parseArray(text, clazz);
	        }
	        catch (JSONException e) {
	        }
	        return new ArrayList<>();
	    }
}
