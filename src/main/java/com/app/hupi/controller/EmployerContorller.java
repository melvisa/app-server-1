package com.app.hupi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Employer;
import com.app.hupi.exception.KiteException;
import com.app.hupi.service.AttentionService;
import com.app.hupi.service.CodeService;
import com.app.hupi.service.DemandService;
import com.app.hupi.service.EmployerService;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.vo.EmployerAddVO;
import com.app.hupi.vo.EmployerUserInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/employer")
@Api(tags = {"雇主模块"})
@RestController
public class EmployerContorller {

	@Autowired
	private EmployerService employerService;
	@Autowired
	private  DemandService demandService;
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private  AttentionService attentionService;
	
	
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
	
	@ApiOperation(value = "雇员个人中心信息")
	@PostMapping("/employeerInfo")
	public DataResult<EmployerUserInfo> employeerInfo(@RequestHeader("token")String token) {
		Employer employer= employerService.queryEmployerByToken(token);
		EmployerUserInfo  employerUserInfo=new EmployerUserInfo();
		// 关注数量
		int attentionNum=attentionService.attentionNumByEmpoyerId(employer.getId());
		//需求数量
		int demandNum=demandService.demandNum(employer.getId());
		employerUserInfo.setAttentionNum(attentionNum);
		employerUserInfo.setDemandNum(demandNum);
		employerUserInfo.setHeadImage(employer.getHeadImage());
		employerUserInfo.setName(employer.getName());
		employerUserInfo.setVipTag(employer.getLevel());
		
		if("1".equals(employer.getLevel())) {
			//计算剩余天数
			long subDay=DateUtil.getBetweenDays(DateUtil.getFormatedDate(), 
					employer.getVipTime(), DateUtil.DATE_FORMAT);
			employerUserInfo.setVipTime(subDay+"");
		}
		String serviceNumber=codeService.queryCodeValueByGroupAndValue("serviceNumber", "serviceNumber");
		employerUserInfo.setServiceNumber(serviceNumber);
		return DataResult.getSuccessDataResult(employerUserInfo);
		
	}
	
	
	
}
