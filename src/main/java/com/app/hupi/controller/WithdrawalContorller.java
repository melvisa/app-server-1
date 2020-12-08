package com.app.hupi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.domain.Withdrawal;
import com.app.hupi.service.TutoringService;
import com.app.hupi.service.WithdrawalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/withdrawal")
@Api(tags = {"提现模块"})
@RestController
public class WithdrawalContorller {

	@Autowired
	private WithdrawalService withdrawalService;
	@Autowired
	private TutoringService tutoringService;
	
	@ApiOperation(value = "新增提现")
	@PostMapping("/addWithdrawal")
	public DataResult<Withdrawal> addWithdrawal(@RequestHeader("token")String token ,Integer money) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		Withdrawal withdrawal=withdrawalService.addWithdrawal(tutoring.getId(), money);
		return DataResult.getSuccessDataResult(withdrawal);
	}
	

	@ApiOperation(value = "提现列表")
	@GetMapping("/listWithdrawal")
	public DataResult<List<Withdrawal>> listWithdrawal(@RequestHeader("token")String token ) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		List<Withdrawal> list=withdrawalService.listWithdrawal(tutoring.getId());
		return DataResult.getSuccessDataResult(list);
	}
	

	
}
