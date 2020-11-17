package com.app.hupi.domain;

import lombok.Data;

@Data
public class VipOrder {

	private String orderId;
	
	private String userId;
	
	private String createTime;
	
	private String status;
	
	private String money;
	
	private String payTime;
	
	// E 雇主vip  T家教vip
	private String type;
	
	private String vipId;
	
}
