package com.app.hupi.util;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据库存储的是单位是分 不包含小数点
 *    返回前端是需要转换成元 需要除100
 *    
 *    费用计算的时候、只能根据数据库的规格数据去计算、不能使用前端传进来的参数
 *    
 * @author 22768
 *
 */
public class MoneyUtil {
	
	// 6 分钟0.15 低于6分钟的不计
	public static Integer getMoney(String startTime, String endTime) { 
		 
		Date date1=DateUtil.parseStrToDate(startTime, DateUtil.DATE_TIME_FORMAT);
		Date date2=DateUtil.parseStrToDate(endTime, DateUtil.DATE_TIME_FORMAT);
		long miao=((date2.getTime()-date1.getTime())/1000);
		long  fen=miao/60;
		if(fen<6) {
			return 0;
		}
		Integer result=(int) DoubleUtil.mul(new Double(15), new Double(fen/6));
		return result;
		
	}

	public static Double getFee(Double price ,Integer num ) {
		 BigDecimal a = new BigDecimal(Double.toString(price));  
	     BigDecimal b = new BigDecimal(Double.toString(num));  
	     BigDecimal result  =a.multiply(b);
	     return result.doubleValue();
	}
	
	public static Double fen2yuan(Double fen) {
		if(fen==null) {
			return new Double(0);
		}
		return DoubleUtil.div(fen, new Double(100), 2);
	}
	
	
	public static Double yuan2fen(Double yuan) {
		return DoubleUtil.mul(yuan, new Double(100));
	}
	public static void main(String[] args) throws Exception {
		System.out.println(getMoney("2020-09-29 10:00:00","2020-09-29 11:00:59"));
	}

}
