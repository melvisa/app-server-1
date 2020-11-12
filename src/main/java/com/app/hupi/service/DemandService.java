package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.Demand;
import com.app.hupi.vo.DemandAddVO;
import com.app.hupi.vo.DemandListVO;

public interface DemandService {

	 Demand addDemand(DemandAddVO demandAddVO);
	 
	 List<Demand> listDemandByEmployer(String employerId);
	 
	 List<DemandListVO> listDemandByTutoring(String tutoringId,String lng,String lat,int pageNum,int pageSize);
	 
	 Demand udpateDemand(Demand demand);
}
