package com.app.hupi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.Comment;
import com.app.hupi.domain.Employer;
import com.app.hupi.mapper.CommentMapper;
import com.app.hupi.mapper.EmployerMapper;
import com.app.hupi.service.CommentService;
import com.app.hupi.util.ListUtil;
import com.app.hupi.vo.CommentVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;


@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commnetMapper;
	@Autowired
	private EmployerMapper employerMapper;
	@Override
	public Comment addComment(Comment comment) {
		Comment c=queryCommentByOrderId(comment.getOrderId());
		if(c==null) {
			commnetMapper.insert(comment);
			comment=commnetMapper.selectById(comment.getId());
		}
		return comment;
	}

	@Override
	public Comment queryCommentById(String id) {
		return commnetMapper.selectById(id);
	}

	@Override
	public List<CommentVo> listCommentByTutoringId(String tutoringId,int pageNum,int pageSize) {
		EntityWrapper<Comment> wrapper=new EntityWrapper<Comment>();
		wrapper.eq("tutoring_id", tutoringId).orderBy("create_time desc");
		PageHelper.startPage(pageNum, pageSize);
		List<Comment> list=commnetMapper.selectList(wrapper);
		List<CommentVo>  voList=new ArrayList<>();
		for(Comment comment:list) {
			CommentVo vo=new CommentVo();
			vo.setCommentTime(comment.getCreateTime());
			vo.setContent(comment.getContent());
			Employer employer=employerMapper.selectById(comment.getEmployerId());
			if(employer!=null) {
				vo.setEmployerImage(employer.getHeadImage());
				vo.setEmployerName(employer.getName());
			}
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public Comment queryfirstCommentByTutoringId(String tutoringId) {
		EntityWrapper<Comment> wrapper=new EntityWrapper<Comment>();
		wrapper.eq("tutoring_id", tutoringId).orderBy("create_time desc");
		List<Comment> list= commnetMapper.selectList(wrapper);
		if(ListUtil.isNotEmpty(list)) {
			list.get(0);
		}
		return null;
	}

	@Override
	public Comment queryCommentByOrderId(String orderId) {
		Comment c=new Comment();
		c.setOrderId(orderId);
		return commnetMapper.selectOne(c);
	}
}
