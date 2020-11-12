package com.app.hupi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("雇主订单列表")
@Data
public class EmployerOrderListVO {
	private String orderId;
	@ApiModelProperty("名字")
	private String name;
	@ApiModelProperty("头像")
	private String headImage;
	@ApiModelProperty("家教身份")
	private String tutoringIdentity;
	@ApiModelProperty("教龄")
	private String teacheTime;
	@ApiModelProperty("性别")
	private String sex;
	@ApiModelProperty("年龄")
	private String age;
	@ApiModelProperty("证书标识 0 没有 1 有")
	private String certFlag;
	@ApiModelProperty("实名认证标识,0 没有 1 有")
	private String authInfo;
	@ApiModelProperty("家教科目【语文、数学、英语】,多个之间,号分割")
	private String tutoringType;
	@ApiModelProperty("评论")
	private String comment;
	@ApiModelProperty("是否评论标识,0 未评论  1 已评论")
	private String commentFlag;
	@ApiModelProperty("联系电话，只有待确认状态才会有电话")
	private String number;
	
}
