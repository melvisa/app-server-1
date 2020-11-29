package com.app.hupi.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.constant.Constant;
import com.app.hupi.domain.Employer;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.EmployerMapper;
import com.app.hupi.service.EmployerService;
import com.app.hupi.util.BeanUtil;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.StringUtil;
import com.app.hupi.vo.EmployerCmsVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
public class EmployerServiceImpl implements EmployerService {

	@Autowired
	private EmployerMapper employerMapper;
	
	@Override
	public Employer addEmployer(Employer employer) {
		employer.setHeadImage(Constant.DEFAULT_HEAD_IMAGE_EMPLOYER);
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

	@Override
	public Employer queryEmployerByUnicode(String unicode) {
		Employer employer=new Employer();
		employer.setUnicode(unicode);
		return employerMapper.selectOne(employer);
	}

	@Override
	public PageInfo<EmployerCmsVo> pageInfo(int pageNum, int pageSize, String name, String number) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<Employer> wrapper=new EntityWrapper<Employer>();
		
		if(StringUtil.isNotEmpty(name)) {
			wrapper.eq("name", name);
		}
		else if(StringUtil.isNotEmpty(number)) {
			wrapper.eq("number", number);
		}
		List<Employer> list=employerMapper.selectList(wrapper);
		PageInfo<Employer> info=new PageInfo<>(list);
		PageInfo<EmployerCmsVo> pageInfo=new PageInfo<>();
		BeanUtil.copyProperties(info, pageInfo);
		List<EmployerCmsVo>voList=BeanUtil.copyPropsForList(list, EmployerCmsVo.class);
		pageInfo.setList(voList);
		return pageInfo;
	}

}
