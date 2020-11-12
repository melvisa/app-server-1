package com.app.hupi.util;

import java.util.Random;
import java.util.UUID;

public class KiteUUID {

	/**
	 * 当前时间戳+随机5位数
	 * @return
	 */
	public static String getId() {
		Long time=System.currentTimeMillis();
		String temp1=String.valueOf(time);
		String temp2=randomString(5);
		return temp1+temp2;
	}
	
	/**
	 * 当前时间戳+随机5位数
	 * @return
	 */
	public static String getId(String prifex) {
		Long time=System.currentTimeMillis();
		String temp1=String.valueOf(time);
		String temp2=randomString(5);
		if(prifex!=null) {
			return prifex+"_"+temp1+temp2;
		}
		return temp1+temp2;
	}
	
	
	
	public static String randomString(int length) {
		StringBuffer sb=new StringBuffer();
		Random random=new Random();
		for(int i=0;i<length;i++) {
			int temp=random.nextInt(10);
			sb.append(temp);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getId());
		System.out.println(getId());
		System.out.println(System.currentTimeMillis());
		System.out.println(System.currentTimeMillis());
	}

	
	
	
}
