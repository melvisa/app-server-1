package com.app.hupi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Demand;
import com.app.hupi.domain.Employer;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.service.DemandService;
import com.app.hupi.service.EmployerService;
import com.app.hupi.service.TutoringService;
import com.app.hupi.vo.DemandAddVO;
import com.app.hupi.vo.DemandListVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/demand")
@Api(tags = {"需求模块"})
@RestController
public class DemandContorller {

	@Autowired
	private DemandService demandService;
	
	@Autowired
	private EmployerService employerService;
	

	@Autowired
	private TutoringService toturingService;
	
	
	@ApiOperation(value = "需求发布校验 返回1 可以发帖 返回0  不可以发帖")
	@GetMapping("/checkDemand")
	public DataResult<String> checkDemand(@RequestHeader("token")String token) {
		Employer employer=employerService.queryEmployerByToken(token);
		String level=employer.getLevel();
		if("1".equals(level)) {
			return DataResult.getSuccessDataResult("1");
		}
		else {
			List<Demand> list=demandService.listDemandByEmployer(employer.getId(), 1, Integer.MAX_VALUE);
		     if(list.size()>1) {
		    	 return DataResult.getSuccessDataResult("0");
		     }
		}
		return DataResult.getSuccessDataResult("1");
	}
	
	
	@ApiOperation(value = "新增需求")
	@PostMapping("/addDemand")
	public DataResult<Demand> addDemand(@RequestHeader("token")String token,@RequestBody DemandAddVO demandAddVO) {
		Employer employer=employerService.queryEmployerByToken(token);
		String employerId=employer.getId();
		Demand demand=demandService.addDemand(employerId,demandAddVO);
		return DataResult.getSuccessDataResult(demand);
	}
	
	
	@ApiOperation(value = "雇主查看我的需求列表")
	@GetMapping("/listDemandByEmployer")
	public DataResult<List<Demand>> listDemandByEmployer(@RequestHeader("token")String token,int pageNum,int pageSize) {
		Employer employer=employerService.queryEmployerByToken(token);
		String employerId=employer.getId();
		List<Demand> list=demandService.listDemandByEmployer(employerId, pageNum, pageSize);
		return DataResult.getSuccessDataResult(list);
	}
	
	@ApiOperation(value = "家教首页查看需求列表")
	@GetMapping("/listDemandByTutoring")
	 @ApiImplicitParams({
	        @ApiImplicitParam(name = "lng", value = "经度", required = true, dataType = "String"),
	        @ApiImplicitParam(name = "lat", value = "纬度", required = true, dataType = "String"),
	        @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int")
	 })
	public DataResult<List<DemandListVO>> listDemandByTutoring(
			@RequestHeader("token")String token,
			@RequestParam(name="lng",required=true)String lng,
			@RequestParam(name="lat",required=true)String lat,
			@RequestParam(name="pageNum",required=true)int pageNum,
			@RequestParam(name="pageSize",required=true)int pageSize) {
		Tutoring toturing=toturingService.queryTutoringByToken(token);
		String tutoringId=toturing.getId();
		List<DemandListVO> list=demandService.listDemandByTutoring(tutoringId,lng,lat,pageNum,pageSize);
		return DataResult.getSuccessDataResult(list);
	}
	
	
}
