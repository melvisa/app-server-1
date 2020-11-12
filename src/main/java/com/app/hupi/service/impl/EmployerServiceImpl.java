package com.app.hupi.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.Code;
import com.app.hupi.domain.Employer;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.EmployerMapper;
import com.app.hupi.service.EmployerService;
import com.app.hupi.util.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
public class EmployerServiceImpl implements EmployerService {

	@Autowired
	private EmployerMapper employerMapper;
	
	@Override
	public Employer addEmployer(Employer employer) {
		employerMapper.insert(employer);
		return employer;
	}

	@Override
	public Employer queryEmployerById(String id) {
		return employerMapper.selectById(id);
	}

	@Override
	public Employer updateEmployer(Employer employer) {
		employerMapper.updateById(employer);
		return employer;
	}

	@Override
	public PageInfo<Employer> queryPageInfo(int pageIndex, int pageSize, String searchValue) {
		PageHelper.startPage(pageIndex, pageSize);
		return null;
	}

	@Override
	public Employer queryEmployerByNumber(String number) {
		Employer employer=new Employer();
		employer.setNumber(number);
		return employerMapper.selectOne(employer);
	}

	@Override
	public Employer queryEmployerByToken(String token) {
		Employer employer=new Employer();
		employer.setToken(token);
		employer =employerMapper.selectOne(employer);
		if(employer!=null) {
			String tokenTime=employer.getTokenTime();
			// 如果当前时间和tokenTime相差6小时、则token失效
			Date minDate=DateUtil.parseStrToDate(tokenTime, DateUtil.DATE_TIME_FORMAT);
			Date maxDate=new Date();
			long subTime=maxDate.getTime()-minDate.getTime();
			if(subTime>6*60*3600*1000) {
				KiteException.throwException("登录已失效。请重新登录");
			}
			//如果没有失效  则更新tokenTime
			employer.setTokenTime(DateUtil.getFormatedDateTime());
			employerMapper.updateById(employer);
		}
		return employer;
	}

}
