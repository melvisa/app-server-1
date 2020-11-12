package com.app.hupi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Code;
import com.app.hupi.service.CodeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/employer")
@Api(tags = {"字典模块"})
@RestController
public class CodeContorller {

	@Autowired
	private CodeService codeService;
	
	@ApiOperation(value = "根据group查询字典")
	@GetMapping("/listCodeWithGroup")
	 @ApiImplicitParams({
	        @ApiImplicitParam(name = "group", value = "分组名", required = true, dataType = "String")
	 })
	public DataResult<List<Code>> listCode(@RequestParam(name="group",required=true)String group) {
		return DataResult.getSuccessDataResult(codeService.listCodeByGroup(group));
	}
	
	
	
	
	
}
