package com.app.hupi.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("关注对象")
public class Attention {

	private String id;
	
	private String employerId;
	
	private String tutoringId;
	
	private String createTime;
}
