package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.Attention;
import com.app.hupi.vo.AttentionListVO;

public interface AttentionService {

	Attention addAttention(String employerId,String tutoringId);
	
	List<AttentionListVO> listAttention(String employerId);
	
	int cancelAttention(String attentionId);
}