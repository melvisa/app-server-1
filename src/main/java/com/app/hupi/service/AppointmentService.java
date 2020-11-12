package com.app.hupi.service;

import com.app.hupi.domain.Appointment;
import com.app.hupi.vo.AppointmentAddVO;
import com.app.hupi.vo.UserVO;

public interface AppointmentService {

	Appointment addAppointment(UserVO userVO,AppointmentAddVO  appointmentAddVO);
}
