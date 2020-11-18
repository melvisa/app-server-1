package com.app.hupi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.Code;
import com.app.hupi.mapper.CodeMapper;
import com.app.hupi.service.CodeService;
import com.app.hupi.util.WebUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;


@Service
public class CodeServiceImpl  implements CodeService{

	@Autowired
	private CodeMapper codeMapper;
	@Override
	public List<Code> listCodeByGroup(String group) {
		// 先从缓存拿 缓存没拿到去数据库拿
		EntityWrapper<Code> wrapper=new EntityWrapper<Code>();
		wrapper.eq("group_name", group).orderBy("weight");
		return codeMapper.selectList(wrapper);
	}
	@Override
	public Code queryCodeByGroupAndValue(String group, String value) {
		List<Code> list=listCodeByGroup(group);
		for(Code c:list) {
			if(value.equals(c.getValue())) {
				return c;
			}
		}
		return null;
	}
	@Override
	public String queryCodeValueByGroupAndValue(String group, String value) {
		Code c=queryCodeByGroupAndValue(group, value);
		if(c==null) {
			return "";
		}
		return c.getName();
	}

}
