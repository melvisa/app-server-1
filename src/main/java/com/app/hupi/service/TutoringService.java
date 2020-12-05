package com.app.hupi.service;

import java.util.List;
import java.util.Map;

import com.app.hupi.domain.Tutoring;
import com.app.hupi.vo.DeliveryResumeVO;
import com.app.hupi.vo.SimpleTutoring;
import com.app.hupi.vo.SimpleUserVo;
import com.app.hupi.vo.TutoringAddVO;
import com.app.hupi.vo.TutoringDetailCmsVo;
import com.app.hupi.vo.TutoringDetailVO;
import com.app.hupi.vo.TutoringListVO;
import com.app.hupi.vo.TutoringRegisterVO;
import com.github.pagehelper.PageInfo;

public interface TutoringService {


	/**
	 * 佣金分配
	 */
	void  updateTutoringAccount(String  tutoringId,String money);
	
	
	TutoringDetailCmsVo queryTutoringDetailCmsVo(String  id);
	
	PageInfo<SimpleTutoring> pageInfo(int pageNum,int pageSize,String name ,String number);
	
	List<SimpleUserVo> queryByYqm(String yqm);
	
	Tutoring queryOneByYqm(String yqm);
	
	Tutoring  queryTutoringByToken(String token);
	
	DeliveryResumeVO deliveryResume(String tutoringId,String demandId);
	
	Tutoring updateTutoring(Tutoring tutoring);
	
	Tutoring addTutoring(TutoringAddVO tutoringAddVO);
	
	Tutoring queryTutoringByNumber(String number);
	
	Tutoring queryTutoringByUnicode(String unicode);
	
	Tutoring register(TutoringRegisterVO tutoringRegisterVO);
	
	TutoringDetailVO queryTutoringDetail(String id,String employerId);
	
	List<TutoringListVO> listTutoringList(String employerId,String tutoringType,String lng,String lat,int pageIndex,int pageSize);
	
	List<TutoringListVO> listTutoringListByParams(int pageIndex,int pageSize,Map<String,String>params);
}
