package com.app.hupi.domain;

import lombok.Data;

@Data
public class Withdrawal {
	private String id;
	private String userId;
	private Integer money;
	private String createTime;
	private String status;
}
