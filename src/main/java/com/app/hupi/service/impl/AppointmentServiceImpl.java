package com.app.hupi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.Appointment;
import com.app.hupi.mapper.AppointmentMapper;
import com.app.hupi.service.AppointmentService;
import com.app.hupi.util.BeanUtil;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.vo.AppointmentAddVO;
import com.app.hupi.vo.UserVO;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentMapper appointmentMapper;
	
	@Override
	public Appointment addAppointment(UserVO userVO,AppointmentAddVO appointmentAddVO) {
		Appointment appointment=new Appointment();
		appointment.setId(KiteUUID.getId());
		appointment.setCreateTime(DateUtil.getFormatedDateTime());
		BeanUtil.copyProperties(appointmentAddVO, appointment);
		appointment.setEmployerId(userVO.getId());
		appointment.setStatus("1");
		appointment.setActive("E");
		appointmentMapper.insert(appointment);
		return appointment;
	}

}
