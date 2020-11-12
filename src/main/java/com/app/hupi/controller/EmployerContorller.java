package com.app.hupi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Employer;
import com.app.hupi.exception.KiteException;
import com.app.hupi.service.EmployerService;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.vo.EmployerAddVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/employer")
@Api(tags = {"雇主模块"})
@RestController
public class EmployerContorller {

	@Autowired
	private EmployerService employerService;
	
	
	@ApiOperation(value = "雇员注册")
	@PostMapping("/addEmployer")
	public DataResult<Employer> addEmployer(@RequestBody EmployerAddVO EmployerAddVO) {
		Employer employer;
		employer=employerService.queryEmployerByNumber(EmployerAddVO.getNumber());
		if(employer!=null) {
			KiteException.throwException("号码已注册，请直接登录。");
		}
		employer=new Employer();
		employer.setId(KiteUUID.getId());
		employer.setCreateTime(DateUtil.getFormatedDateTime());
		employer.setNumber(EmployerAddVO.getNumber());
		employer=employerService.addEmployer(employer);
		return DataResult.getSuccessDataResult(employer);
		
	}
	
	
	
	
	
}
