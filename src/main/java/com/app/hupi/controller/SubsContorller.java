package com.app.hupi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Code;
import com.app.hupi.service.CodeService;
import com.app.hupi.vo.SubsVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/subs")
@Api(tags = {"科目模块"})
@RestController
public class SubsContorller {

	@Autowired
	private CodeService codeService;
	
	
	@ApiOperation(value = "获取家教注册科目")
	@GetMapping("/listSubs")
	public DataResult<List<SubsVO>> listSubs() {
		List<SubsVO> voList=new ArrayList<>();
		List<Code> codeList=codeService.listCodeByGroup("tutoring_type");
		for(Code code:codeList) {
			SubsVO vo=new SubsVO();
			vo.setName(code.getName().split(",")[0]);
			vo.setValue(code.getValue());
			vo.setIcon(code.getName().split(",")[1]);
			List<Code> subCodeList=codeService.listCodeByGroup(code.getValue());
			List<SubsVO> voList2=new ArrayList<>();
			for(Code c:subCodeList) {
				SubsVO vo2=new SubsVO();
				vo2.setName(c.getName().split(",")[0]);
				vo2.setValue(c.getValue());
				vo2.setIcon(c.getName().split(",")[1]);
				vo2.setParentValue(code.getValue());
				voList2.add(vo2);
			}
			vo.setChildList(voList2);
			voList.add(vo);
		}
		return DataResult.getSuccessDataResult(voList);
		
	}
	
	
	
	
	
}
