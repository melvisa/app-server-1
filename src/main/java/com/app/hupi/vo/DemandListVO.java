package com.app.hupi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DemandListVO {
	private String id;
	private String employerId;
	@ApiModelProperty(value="姓名",required=true)
	private String name;
	@ApiModelProperty(value="学生姓名",required=true)
	private String studengSex;
	@ApiModelProperty(value="年级",required=true)
	private String className;
	@ApiModelProperty(value="辅导科目，多个之间逗号分隔",required=true)
	private String subs;
	@ApiModelProperty(value="辅导时间",required=true)
	private String time;
	@ApiModelProperty(value="地址",required=true)
	private String address;
	@ApiModelProperty(value="原因",required=true)
	private String reason;
	@ApiModelProperty(value="老师性别",required=true)
	private String tutoringSex;
	@ApiModelProperty(value="老师身份",required=true)
	private String tutoringIdentity;
	@ApiModelProperty(value="老师年龄",required=true)
	private String tutoringAge;
	@ApiModelProperty(value="薪资",required=true)
	private String salary;
	@ApiModelProperty(value="是否需要诚意金 直接传是或者否",required=true)
	private String earnestFlag;
	@ApiModelProperty("经度")
	private String lng;
	@ApiModelProperty("纬度")
	private String lat;
	@ApiModelProperty("距离")
	private String distance;
	
	private String createTime;
	private String status;
}
