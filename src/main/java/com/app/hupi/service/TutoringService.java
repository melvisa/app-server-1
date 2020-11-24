package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.Tutoring;
import com.app.hupi.vo.DeliveryResumeVO;
import com.app.hupi.vo.SimpleUserVo;
import com.app.hupi.vo.TutoringAddVO;
import com.app.hupi.vo.TutoringDetailVO;
import com.app.hupi.vo.TutoringListVO;
import com.app.hupi.vo.TutoringRegisterVO;

public interface TutoringService {

	
	List<SimpleUserVo> queryByYqm(String yqm);
	
	Tutoring  queryTutoringByToken(String token);
	
	DeliveryResumeVO deliveryResume(String tutoringId,String demandId);
	
	Tutoring updateTutoring(Tutoring tutoring);
	
	Tutoring addTutoring(TutoringAddVO tutoringAddVO);
	
	Tutoring queryTutoringByNumber(String number);
	
	Tutoring queryTutoringByUnicode(String unicode);
	
	Tutoring register(TutoringRegisterVO tutoringRegisterVO);
	
	TutoringDetailVO queryTutoringDetail(String id,String employerId);
	
	List<TutoringListVO> listTutoringList(String employerId,String tutoringType,String lng,String lat,int pageIndex,int pageSize);
}
