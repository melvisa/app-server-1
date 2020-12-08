package com.app.hupi.vo;

import lombok.Data;


@Data
public class WithdrawalListVo {
	private String id;
	private String userId;
	private Integer money;
	private String createTime;
	private String status;
	private String cardNo;
	private String bankName;
	private String cardName;
}
