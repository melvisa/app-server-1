package com.app.hupi.domain;

import lombok.Data;

@Data
public class CouponOrder {
	
	private String id;
	private String userId;
	private String status;
	private String couponId;
	private String payTime;
	private String money;
	private String createTime;
	

}
