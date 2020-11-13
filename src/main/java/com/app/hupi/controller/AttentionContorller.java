package com.app.hupi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Attention;
import com.app.hupi.domain.Employer;
import com.app.hupi.service.AttentionService;
import com.app.hupi.service.EmployerService;
import com.app.hupi.vo.AttentionListVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/attention")
@Api(tags = {"关注模块"})
@RestController
public class AttentionContorller {

	@Autowired
	private AttentionService attentionService;
	@Autowired
	private EmployerService employerService;
	
	@ApiOperation(value = "增加关注")
	@PostMapping("/addAttention")
	public DataResult<Attention> addAttention(@RequestHeader("token")String token,String tutoringId) {
		Employer employer=employerService.queryEmployerByToken(token);
		String employerId=employer.getId();
		Attention a=attentionService.addAttention(employerId, tutoringId);
		return DataResult.getSuccessDataResult(a);
	}
	
	@ApiOperation(value = "取消关注,返回1 成功 ")
	@PostMapping("/cancelAttention")
	public DataResult<Integer> cancelAttention(String attentionId) {
		int i=attentionService.cancelAttention(attentionId);
		return DataResult.getSuccessDataResult(i);
	}
	
	@ApiOperation(value = "雇主关注列表")
	@GetMapping("/listAttention")
	public DataResult<List<AttentionListVO>> listAttention(@RequestHeader("token")String token,int pageNum,int pageSize) {
		Employer employer=employerService.queryEmployerByToken(token);
	    List<AttentionListVO> list=attentionService.listAttention(employer.getId(),pageNum,pageSize);
		return DataResult.getSuccessDataResult(list);
	}
	
}
