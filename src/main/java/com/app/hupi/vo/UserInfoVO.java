package com.app.hupi.vo;

import com.app.hupi.domain.Employer;
import com.app.hupi.domain.Tutoring;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoVO {
	@ApiModelProperty(value="用户类型  T 家教  E 雇主")
	private String userType;
	@ApiModelProperty(value="雇主对象")
	private Employer employer;
	@ApiModelProperty(value="家教对象")
	private Tutoring tutoring;
}
