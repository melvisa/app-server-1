package com.app.hupi.domain;

import lombok.Data;

@Data
public class CouponDetail {
	private String id;
	private String userId;
	private Integer amount;
	private String type;
	private String createTime;
	private String desc;
}
