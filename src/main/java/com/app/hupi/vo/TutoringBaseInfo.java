package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TutoringBaseInfo {
	@ApiModelProperty("名字")
	private String name;
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
}
