package com.app.hupi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("家教注册对象")
public class TutoringRegisterVO {
	@ApiModelProperty(value="短信验证码",required=true)
	private String code;
	@ApiModelProperty(value="电话号码",required=true)
	private String number;
	@ApiModelProperty(value="邀请码")
	private String yqm;
	@ApiModelProperty(value="年级种类【小学，初中，高中，大学】，多个之间,号分割",required=true)
	private String classType;
	@ApiModelProperty(value="家教科目【语文、数学、英语】,多个之间,号分割",required=true)
	private String tutoringType;
}
