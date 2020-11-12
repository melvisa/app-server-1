package com.app.hupi.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("家教添加")
@Data
public class TutoringAddVO {
	@ApiModelProperty(value="头像",required=true)
	private String headImage;
	@ApiModelProperty(value="工作经历",required=true)
	private List<WorkExperienceVO> workExperienceList;
	@ApiModelProperty(value="教育经历",required=true)
	private EduExperienceVO eduExperience;
	@ApiModelProperty(value="个人介绍",required=true)
	private String introduce;
	@ApiModelProperty(value="名字",required=true)
	private String name;
	@ApiModelProperty(value="家教身份",required=true)
	private String tutoringIdentity;
	@ApiModelProperty(value="教龄",required=true)
	private String teacheTime;
	@ApiModelProperty(value="性别,直接传 男 女",required=true)
	private String sex;
	@ApiModelProperty(value="年龄",required=true)
	private String age;
	@ApiModelProperty(value="证书标识直接传 是 否",required=true)
	private String certFlag;
	
}
