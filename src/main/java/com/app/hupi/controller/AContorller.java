package com.app.hupi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/employer")
@Api(tags = {"雇主模块"})
@RestController
public class AContorller {

	
	
	@ApiOperation(value = "管理员列表")
	@GetMapping("/listAdmin")
	public DataResult<Integer> listWithdrawal() {
		return null;
	}
	
	
	
	
	
}
