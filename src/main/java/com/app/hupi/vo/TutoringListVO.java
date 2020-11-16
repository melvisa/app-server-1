package com.app.hupi.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TutoringListVO {
	private String id;
	@ApiModelProperty("名字")
	private String name;
	@ApiModelProperty("头像")
	private String headImage;
	@ApiModelProperty("家教科目描述")
	private List<String> subjectList;

	@ApiModelProperty("个性标签")
	private  List<String> tags;
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

	@ApiModelProperty("经度")
	private String lng;
	@ApiModelProperty("纬度")
	private String lat;
	@ApiModelProperty("距离")
	private String distance;
	@ApiModelProperty("实名认证信息")
	private String authInfo;
}
