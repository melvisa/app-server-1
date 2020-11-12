package com.app.hupi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("银行卡对象")
public class BankInfoVO {
	@ApiModelProperty(value="银行卡号码",required=true)
	private String cardNo;
	@ApiModelProperty(value="银行卡所属银行",required=true)
	private String bankName;
	@ApiModelProperty(value="持卡人姓名",required=true)
	private String cardName;
}
