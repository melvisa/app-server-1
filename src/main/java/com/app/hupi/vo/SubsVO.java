package com.app.hupi.vo;

import java.util.List;

import lombok.Data;

@Data
public class SubsVO {

	private String name;
	
	private String value;
	
	private String icon;
	
	private String parentValue;
	
	private List<SubsVO> childList;
}
