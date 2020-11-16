package com.app.hupi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.service.VipService;
import com.app.hupi.vo.VipVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/vip")
@Api(tags = {"Vip模块"})
@RestController
public class VipContorller {

	@Autowired
	private VipService vipService;
	
	@ApiOperation(value = "查询雇主vip界面信息")
	@GetMapping("/vipInfoWithEmployer")
	public DataResult<List<VipVO>> vipInfoWithEmployer() {
		List<VipVO> list=vipService.listVip("E");
		return DataResult.getSuccessDataResult(list);
	}
	
	@ApiOperation(value = "查询vip界面信息")
	@GetMapping("/vipInfoWithTutoring")
	public DataResult<List<VipVO>> vipInfoWithTutoring() {
		List<VipVO> list=vipService.listVip("T");
		return DataResult.getSuccessDataResult(list);
	}
	
	
	
	
}
