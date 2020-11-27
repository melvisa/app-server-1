package com.app.hupi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.AuthOrder;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.service.AuthOrderService;
import com.app.hupi.service.TutoringService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/auth")
@Api(tags = {"实名认证模块"})
@RestController
public class AuthOrderContorller {

	@Autowired
	private AuthOrderService authOrderService;
	@Autowired
	private TutoringService tutoringService;
	
	@ApiOperation(value = "实名认证支付订单")
	@GetMapping("/authOrder")
	public DataResult<String> authOrder(@RequestHeader("token")String token,String name ,String idcard) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		AuthOrder authOrder=authOrderService.addAuthOrder(tutoring.getId(), name, idcard);
		return DataResult.getSuccessDataResult(authOrder.getId());
	}
	
	
	
	
	
	
}
