package com.app.hupi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.Attention;
import com.app.hupi.mapper.AttentionMapper;
import com.app.hupi.mapper.EmployerMapper;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.service.AttentionService;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.vo.AttentionListVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

@Service
public class AttentionServiceImpl implements AttentionService {

	@Autowired
	private AttentionMapper attentionMapper;
	@Autowired
	private TutoringMapper tutoringMapper;
	@Autowired
	private EmployerMapper  employerMapper;

	@Override
	public Attention addAttention(String employerId, String tutoringId) {
		Attention attention = new Attention();
		attention.setEmployerId(employerId);
		attention.setTutoringId(tutoringId);
		attention = attentionMapper.selectOne(attention);
		if (attention == null) {
			attention = new Attention();
			attention.setEmployerId(employerId);
			attention.setTutoringId(tutoringId);
			attention.setId(KiteUUID.getId());
			attention.setCreateTime(DateUtil.getFormatedDateTime());
			attentionMapper.insert(attention);
		}
		return attention;
	}

	@Override
	public List<AttentionListVO> listAttention(String employerId) {
		   EntityWrapper<Attention> wrapper=new EntityWrapper<Attention>();
		   wrapper.eq("employer_id", employerId).orderBy("create_time desc");
		   List<Attention> list=attentionMapper.selectList(wrapper);
		   List<AttentionListVO> voList=new ArrayList<>();
		   for(Attention a:list) {
			   AttentionListVO vo=new AttentionListVO();
			   vo.setEmployer(employerMapper.selectById(a.getEmployerId()));
			   vo.setTutoring(tutoringMapper.selectById(a.getTutoringId()));
			   vo.setAttention(a);
			   voList.add(vo);
		   }
		return voList;
	}

	@Override
	public int cancelAttention(String attentionId) {
		return attentionMapper.deleteById(attentionId);
	}

}
