package com.app.hupi.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TutoringDetailVO {
	private String id;
	@ApiModelProperty("名字")
	private String name;
	@ApiModelProperty("头像")
	private String headImage;
	@ApiModelProperty("工作经历")
	private List<WorkExperienceVO> workExperienceList;
	@ApiModelProperty("教育经历")
	private EduExperienceVO eduExperience;
	@ApiModelProperty("个人介绍")
	private String introduce;
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
	@ApiModelProperty("实名认证信息")
	private String authInfo;
	@ApiModelProperty("图片信息")
	private List<String> images;
}
