package com.app.hupi.vo;

import java.util.List;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class TutoringDetailCmsVo {
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
	@ApiModelProperty("家教科目描述")
	private List<String> subjectList;
	@ApiModelProperty("工作经历")
	List<WorkExperienceVO>  workExperienceList;
	@ApiModelProperty("教育经历")
	private EduExperienceVO eduExperience;
	@ApiModelProperty("个人介绍")
	private String introduce;
	@ApiModelProperty("标签")
	private  List<String> tags;
	private String schedule;
	@ApiModelProperty("图片信息")
	private List<String> images;
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
	@ApiModelProperty("实名认证信息")
	@Column(name="auth_info",defaultValue="fail")
	private String authInfo;
	@ApiModelProperty("银行卡信息")
	private String bankInfo;
	@ApiModelProperty("账户余额")
	@Column(name="total_account",defaultValue="0")
	private Integer totalAccount;
	@ApiModelProperty("代金券")
	@Column(name="coupon",defaultValue="0")
	private Integer coupon;
}
