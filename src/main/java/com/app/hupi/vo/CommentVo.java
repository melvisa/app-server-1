package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentVo {

	@ApiModelProperty("雇主头像")
	private String employerImage;
	@ApiModelProperty("雇主名字")
	private String employerName;
	@ApiModelProperty("评论时间")
	private String commentTime;
	@ApiModelProperty("评论内容")
	private String content;
}
