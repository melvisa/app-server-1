package com.app.hupi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Appointment;
import com.app.hupi.domain.Employer;
import com.app.hupi.service.AppointmentService;
import com.app.hupi.service.EmployerService;
import com.app.hupi.vo.AppointmentAddVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/appointment")
@Api(tags = {"预约模块"})
@RestController
public class AppointmentContorller {

	@Autowired
	private EmployerService employerService;
	@Autowired
	private AppointmentService appointmentService;
	
	@ApiOperation(value = "新增预约")
	@PostMapping("/addAppointment")
	public DataResult<Appointment> addAppointment(@RequestHeader("token")String token,@RequestBody AppointmentAddVO appointmentAddVO) {
		Employer employer=employerService.queryEmployerByToken(token);
		Appointment appointment=appointmentService.addAppointment(employer.getId(), appointmentAddVO);
		return DataResult.getSuccessDataResult(appointment);
	}
	
	
	@ApiOperation(value = "判断是否已经预约 返回1 已经预约 0 未预约")
	@GetMapping("/checkAppointment")
	public DataResult<String> checkAppointment(@RequestHeader("token")String token,String  tutoringId,String demandId ) {
		Employer employer=employerService.queryEmployerByToken(token);
		Appointment appointment=appointmentService.queryAppointmentBy(employer.getId(), tutoringId, demandId);
	    if(appointment==null) {
	    	return  DataResult.getSuccessDataResult("0");
	    }
	    return  DataResult.getSuccessDataResult("1");
	}
	
	
	
}
