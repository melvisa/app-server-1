package com.app.hupi.service;

import com.app.hupi.domain.Appointment;
import com.app.hupi.vo.AppointmentAddVO;
import com.app.hupi.vo.UserVO;

public interface AppointmentService {

	Appointment addAppointment(String employerId,AppointmentAddVO appointmentAddVO);

	Appointment queryAppointmentBy(String employerId,String tutoringId,String demandId);
}
