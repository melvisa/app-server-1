package com.app.hupi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.app.hupi.vo.LngAndLatVO;

import net.sf.json.JSONObject;
 
/**
 * 经纬度工具类
 * 
 */
public class LngAndLatUtils
{
	
	public static void main(String[] args) {
		String address="324地址32";
		getLngAndLat(address);
	}
	public static LngAndLatVO getLngAndLat(String address)
	{
		LngAndLatVO vo=new LngAndLatVO();
		String key="2bbkbapPmRcXiworGv5jXV1axEu4L4cg";
		Map<String, String> map = new HashMap<String, String>();
		// 调用百度接口
		String url ="http://api.map.baidu.com/geocoding/v3/?address="+address+"&output=json&ak="+key+""; 
		String json = loadJSON(url);
		JSONObject obj = JSONObject.fromObject(json);
		if (obj.get("status").toString().equals("0"))
		{
			String lng = obj.getJSONObject("result").getJSONObject("location").getString("lng");
			String lat = obj.getJSONObject("result").getJSONObject("location").getString("lat");
			map.put("lng", lng);
			map.put("lat", lat);
			vo.setLat(lat);
			vo.setLng(lng);
		}
		System.out.println(JsonUtil.toJson(obj));
		return vo;
	}
	public static String loadJSON(String url)
	{
		StringBuilder json = new StringBuilder();
		try
		{
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null)
			{
				json.append(inputLine);
			}
			in.close();
		}
		catch (MalformedURLException e)
		{
		}
		catch (IOException e)
		{
		}
		return json.toString();
	}
}