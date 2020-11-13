package com.app.hupi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Comment;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.domain.TutoringOrder;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.TutoringOrderMapper;
import com.app.hupi.service.CommentService;
import com.app.hupi.service.TutoringService;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.vo.AttentionListVO;
import com.app.hupi.vo.CommentAddVO;
import com.app.hupi.vo.CommentVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/comment")
@Api(tags = {"评论模块"})
@RestController
public class CommentContorller {
	@Autowired
	private TutoringService tutoringService;
	@Autowired
	private CommentService  commentService;
	@Autowired
	private TutoringOrderMapper  tutoringOrderMapper;
	
	@ApiOperation(value = "增加评论")
	@PostMapping("/addComment")
	public DataResult<Comment> addComment(@RequestBody CommentAddVO commentAddVO) {
		TutoringOrder tutoringOrder=tutoringOrderMapper.selectById(commentAddVO.getOrderId());
		if(tutoringOrder==null) {
			KiteException.throwException("订单号不存在");
		}
		Comment comment=new Comment();
		comment.setId(KiteUUID.getId());
		comment.setCreateTime(DateUtil.getFormatedDateTime());
		comment.setEmployerId(tutoringOrder.getEmployerId());
		comment.setTutoringId(tutoringOrder.getTutoringId());
		comment.setOrderId(commentAddVO.getOrderId());
		comment.setContent(commentAddVO.getContent());
		comment=commentService.addComment(comment);
		return DataResult.getSuccessDataResult(comment);
	}
	
	@ApiOperation(value = "家教评价列表")
	@GetMapping("/listAttention")
	public DataResult<List<CommentVo>> listAttention(@RequestHeader("token")String token,int pageNum,int pageSize) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		return DataResult.getSuccessDataResult(commentService.
				listCommentByTutoringId(tutoring.getId(), pageNum, pageSize));
	}
	
	
	
	
}
