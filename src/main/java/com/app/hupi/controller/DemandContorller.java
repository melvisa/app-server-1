package com.app.hupi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Demand;
import com.app.hupi.service.DemandService;
import com.app.hupi.util.UserUtil;
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
	
	@ApiOperation(value = "新增需求")
	@PostMapping("/addDemand")
	public DataResult<Demand> addDemand(@RequestBody DemandAddVO demandAddVO) {
		Demand demand=demandService.addDemand(demandAddVO);
		return DataResult.getSuccessDataResult(demand);
	}
	
	
	@ApiOperation(value = "雇主查看我的需求列表")
	@GetMapping("/listDemandByEmployer")
	public DataResult<List<Demand>> listDemandByEmployer() {
		String employerId=UserUtil.getUserVO().getId();
		List<Demand> list=demandService.listDemandByEmployer(employerId);
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
			@RequestParam(name="lng",required=true)String lng,
			@RequestParam(name="lat",required=true)String lat,
			@RequestParam(name="pageNum",required=true)int pageNum,
			@RequestParam(name="pageSize",required=true)int pageSize) {
		String tutoringId=UserUtil.getUserVO().getId();
		List<DemandListVO> list=demandService.listDemandByTutoring(tutoringId,lng,lat,pageNum,pageSize);
		return DataResult.getSuccessDataResult(list);
	}
	
	
}
