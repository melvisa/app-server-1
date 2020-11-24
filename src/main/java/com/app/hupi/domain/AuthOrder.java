package com.app.hupi.domain;

import lombok.Data;

@Data
public class AuthOrder {
	private String id;
	private String tutoringId;
	private String createTime;
	private String money;
	private String name;
	private String idcard;
	private String status;
	private String payTime;
	private String payno;
}
