package com.app.hupi.util;

import java.util.ArrayList;
import java.util.List;

public class DeliveryTimeUtil {

	/**
	 *根据当前时间计算配送时间
	 * @param prodType
	 * @return
	 */
    public static  List<String> getDeliveryTime(){
    	// TODO
    	List<String> result=new ArrayList<String>();
    	result.add("10:30~11:30");
    	result.add("10:30~12:30");
    	result.add("17:30~19:30");
    	result.add("20:30~22:30");
    	return result;
    }
}
