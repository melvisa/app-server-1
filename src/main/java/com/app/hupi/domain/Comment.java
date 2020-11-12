package com.app.hupi.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("评论对象")

public class Comment {
	private String id;
	
	private String employerId;
	
	private String tutoringId;
	
	private String content;
	
	private String createTime;
	
	private String orderId;
}
