package com.app.hupi.vo;

import java.util.List;

import com.app.hupi.domain.Comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TutoringOrderListVO {
	private String id;
	@ApiModelProperty("名字")
	private String name;
	@ApiModelProperty("头像")
	private String headImage;
	@ApiModelProperty("家教科目描述")
	private List<String> subjectList;
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
	@ApiModelProperty("评论对象")
	private List<Comment> comments;
	
}
