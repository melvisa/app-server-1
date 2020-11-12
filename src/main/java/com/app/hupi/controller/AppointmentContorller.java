package com.app.hupi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Appointment;
import com.app.hupi.service.AppointmentService;
import com.app.hupi.util.UserUtil;
import com.app.hupi.vo.AppointmentAddVO;
import com.app.hupi.vo.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/appointment")
@Api(tags = {"预约模块"})
@RestController
public class AppointmentContorller {

	@Autowired
	private AppointmentService appointmentService;
	
	@ApiOperation(value = "新增预约")
	@PostMapping("/addAppointment")
	public DataResult<Appointment> addAppointment(@RequestBody AppointmentAddVO appointmentAddVO) {
		UserVO userVO=UserUtil.getUserVO();
		Appointment appointment=appointmentService.addAppointment(userVO, appointmentAddVO);
		return DataResult.getSuccessDataResult(appointment);
	}
	
	
	
	
	
}
