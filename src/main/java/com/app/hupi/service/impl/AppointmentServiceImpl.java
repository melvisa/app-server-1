package com.app.hupi.service.impl;

import java.util.List;

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
import com.baomidou.mybatisplus.mapper.EntityWrapper;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentMapper appointmentMapper;
	
	@Override
	public Appointment addAppointment(String employerId,AppointmentAddVO appointmentAddVO) {
		Appointment appointment=new Appointment();
		appointment.setId(KiteUUID.getId());
		appointment.setCreateTime(DateUtil.getFormatedDateTime());
		BeanUtil.copyProperties(appointmentAddVO, appointment);
		appointment.setEmployerId(employerId);
		appointment.setStatus("1");
		appointment.setActive("E");
		appointmentMapper.insert(appointment);
		return appointment;
	}

	@Override
	public Appointment queryAppointmentBy(String employerId, String tutoringId, String demandId) {
		// TODO Auto-generated method stub
		EntityWrapper<Appointment> wrapper=new EntityWrapper<>();
		wrapper.eq("employer_id", employerId).eq("tutoring_id", tutoringId)
		.eq("demand_id", demandId);
		List<Appointment> list=appointmentMapper.selectList(wrapper);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

}
