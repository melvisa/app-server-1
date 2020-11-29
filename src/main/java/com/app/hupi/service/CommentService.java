package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.Comment;
import com.app.hupi.vo.CommentVo;
import com.github.pagehelper.PageInfo;

public interface CommentService {

	Comment addComment(Comment comment);
	
	Comment queryCommentById(String id);
	
	Comment queryCommentByOrderId(String orderId);
	
	
	PageInfo<CommentVo> pageInfo(String tutoringId,int pageNum,int pageSize);
	
	List<CommentVo> listCommentByTutoringId(String tutoringId,int pageNum,int pageSize);
	
	Comment  queryfirstCommentByTutoringId(String tutoringId);
}
