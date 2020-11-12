package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.Comment;

public interface CommentService {

	Comment addComment(Comment comment);
	
	Comment queryCommentById(String id);
	
	Comment queryCommentByOrderId(String orderId);
	
	List<Comment> listCommentByTutoringId(String tutoringId,int pageNum,int pageSize);
	
	Comment  queryfirstCommentByTutoringId(String tutoringId);
}
