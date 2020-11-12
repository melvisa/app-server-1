package com.app.hupi.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Appointment {

	private String id;
	
	private String employerId;
	
	private String tutoringId;
	
	private String demandId;
	
	private String createTime;
	@ApiModelProperty("主动对象 E 雇主主动预约老师 ,T 老师主动投递简历 ")
	private String active;
	
	private String status;
}
