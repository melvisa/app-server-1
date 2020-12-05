package com.app.hupi.domain;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("家教对象")
public class Tutoring {
	private String id;
	@ApiModelProperty("电话号码")
	private String number;
	@ApiModelProperty("邀请码")
	private String yqm;
	@ApiModelProperty("自己的邀请码")
	private String yqmSelf;
	@ApiModelProperty("名字")
	private String name;
	@ApiModelProperty("身份证号")
	private String idCard;
	@ApiModelProperty("头像")
	private String headImage;
	@ApiModelProperty("年级种类【小学，初中，高中，大学】，多个之间,号分割")
	private String classType;
	@ApiModelProperty("家教科目【语文、数学、英语】,多个之间,号分割")
	private String tutoringType;
	@ApiModelProperty("工作经历")
	private String workExperience;
	@ApiModelProperty("教育经历")
	private String eduExperience;
	@ApiModelProperty("个人介绍")
	private String introduce;
	@ApiModelProperty("标签")
	private String tags;
	@ApiModelProperty("课程表")
	@Column(name="schedule",length=4000)
	private String schedule;
	@ApiModelProperty("相册")
	@Column(name="album",length=1000)
	private String album;
	@ApiModelProperty("身份级别 0 普通家教 1 vip")
	private String level;
	@ApiModelProperty("VIP剩余时间 天数")
	private String vipTime;
	private String vipCreateTime;
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
	@ApiModelProperty("时间")
	private String createTime;
	@ApiModelProperty("经度")
	private String lng;
	@ApiModelProperty("纬度")
	private String lat;
	@ApiModelProperty("实名认证信息")
	@Column(name="auth_info",defaultValue="fail")
	private String authInfo;
	@ApiModelProperty("银行卡信息")
	private String bankInfo;
	@ApiModelProperty("账户余额")
	@Column(name="total_account",defaultValue="0")
	private Double totalAccount;
	@ApiModelProperty("代金券")
	@Column(name="coupon",defaultValue="0")
	private Integer coupon;
	
	private String token;
	
	private String tokenTime;
	
	private String unicode;
	
}
