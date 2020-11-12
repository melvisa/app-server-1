package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.Code;

public interface CodeService {

	List<Code> listCodeByGroup(String group);
	
	Code queryCodeByGroupAndValue(String group,String value);
	
	String  queryCodeValueByGroupAndValue (String group,String value);
}
