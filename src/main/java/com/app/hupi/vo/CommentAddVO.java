package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentAddVO {
	@ApiModelProperty(value="订单ID",required=true)
	private String orderId;
	@ApiModelProperty(value="评论内容",required=true)
	private String content;
}
