package com.app.hupi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.Constant;
import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Comment;
import com.app.hupi.domain.Demand;
import com.app.hupi.domain.Employer;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.domain.TutoringOrder;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.DemandMapper;
import com.app.hupi.mapper.EmployerMapper;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.mapper.TutoringOrderMapper;
import com.app.hupi.service.CodeService;
import com.app.hupi.service.CommentService;
import com.app.hupi.service.EmployerService;
import com.app.hupi.service.TutoringOrderService;
import com.app.hupi.util.BeanUtil;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.JsonUtil;
import com.app.hupi.vo.EmployerOrderListVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/employerOrder")
@Api(tags = {"雇主订单模块"})
@RestController
public class EmployerOrderContorller {

	@Autowired
	private TutoringOrderService tutoringOrderService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private TutoringMapper tutoringMapper;
	@Autowired
	private EmployerMapper employerMapper;
	@Autowired
	private DemandMapper demandMapper;
	@Autowired
	private TutoringOrderMapper tutoringOrderMapper;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private CodeService codeService;
	@ApiOperation(value = "雇主查询订单列表")
	@GetMapping("/listEmployerOrder")
	@ApiImplicitParams({
	        @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "String")
	 })
	public DataResult<List<EmployerOrderListVO>> listEmployerOrder(
			@RequestHeader("token")String token,
			@RequestParam(name="pageNum",required=true)int pageNum,
			@RequestParam(name="pageSize",required=true)int pageSize,
			@RequestParam(name="status",required=true)String status) {
		Employer employer=employerService.queryEmployerByToken(token);
		String employerId=employer.getId();
		
		List<TutoringOrder> list=tutoringOrderService.listTutoringOrderWithEmployer(pageNum, pageSize, employerId, status);
		List<EmployerOrderListVO> voList=new ArrayList<>();
		boolean isFirst=true;
		for(TutoringOrder tutoringOrder:list) {
			EmployerOrderListVO vo=changeEmployerOrderListVO(tutoringOrder,isFirst);
			voList.add(vo);
			isFirst=false;
		}
		return DataResult.getSuccessDataResult(voList);
	}
	
	public static void main(String[] args) {
		String s="tutoring_type_xiaoxue_shuxue";
		System.out.println(s.lastIndexOf("_", s.length()));
		System.out.println(s.substring(0, s.lastIndexOf("_", s.length())));
	}
	
	private EmployerOrderListVO changeEmployerOrderListVO(TutoringOrder tutoringOrder,boolean isFirst) {
		String tutoringId=tutoringOrder.getTutoringId();
		Tutoring tutoring=tutoringMapper.selectById(tutoringId);
		EmployerOrderListVO vo=new EmployerOrderListVO();
		BeanUtil.copyProperties(tutoring, vo);
		String type=tutoring.getTutoringType();
		// tutoring_type_xiaoxue_shuxue,tutoring_type_xiaoxue_shengwu,tutoring_type_chuzhong_yingyu,tutoring_type_chuzhong_changge
		String[] types;
		if(type.contains(Constant.STRING_SEPARATOR)) {
			 types=type.split(Constant.STRING_SEPARATOR);
		}
		else {
			types=new String[1];
			types[0]=type;
		}
		Map<String,String>map=new HashMap<>();
		for(String s:types) {
		   String className=s.substring(0, s.lastIndexOf("_", s.length()));
		   String classNameDesc = codeService.queryCodeValueByGroupAndValue("tutoring_type", className).split(",")[0];
		   String subName=codeService.queryCodeValueByGroupAndValue(className, s).split(",")[0];
			if(map.containsKey(classNameDesc)) {
				map.put(classNameDesc, map.get(classNameDesc)+","+subName);
			}
			else {
				map.put(classNameDesc, classNameDesc+subName);
			}
		}
		vo.setTutoringType(JsonUtil.toJson(map.values()));
		vo.setTutoringTypeList(map.values());
		vo.setCommentFlag("0");
		vo.setOrderId(tutoringOrder.getId());
		// 如果是待确状态  则需要返回手机号码
		// 如果雇主是会员  则返回所有的手机号码  不是会员 是返回选择的第一个雇主的手机号码
		//其他返回一个提示
		if(Constant.TUTORING_ORDER_STATUS_DAIQUEREN.equals(tutoringOrder.getStatus())) {
			if("1".equals(tutoring.getLevel())||isFirst) {
				vo.setNumber(tutoring.getNumber());
			}
			else {
				vo.setNumber("请开通会员查看");
			}
		}
		Comment comment=commentService.queryCommentByOrderId(tutoringOrder.getId());
		if(comment!=null) {
			vo.setComment(comment.getContent());
			vo.setCommentFlag("1");
		}
		return vo;
	}
	
	/**
	 * @param demandId
	 * @return
	 */
	@ApiOperation(value = "不合适")
	@PostMapping("/inappropriate")
	public DataResult<Integer> inappropriate(@RequestHeader("token")String token,@RequestParam String orderId) {
		Employer employer=employerService.queryEmployerByToken(token);
		String employerId=employer.getId();
		TutoringOrder tutoringOrder=tutoringOrderMapper.selectById(orderId);
		if(!employerId.equals(tutoringOrder.getEmployerId())) {
			KiteException.throwException("数据异常");
		}
		tutoringOrder.setStatus(Constant.TUTORING_ORDER_STATUS_BUHESHI);
		tutoringOrder.setBhsTime(DateUtil.getFormatedDateTime());
		int i=tutoringOrderMapper.updateById(tutoringOrder);
		return DataResult.getSuccessDataResult(i);
	}
	
	
	/**
	 * @param demandId
	 * @return
	 */
	@ApiOperation(value = "合适")
	@PostMapping("/appropriate")
	@Transactional
	public DataResult<Integer>  appropriate(@RequestHeader("token")String token,@RequestParam String orderId) {
		Employer employer=employerService.queryEmployerByToken(token);
		String employerId=employer.getId();
		TutoringOrder tutoringOrder=tutoringOrderMapper.selectById(orderId);
		if(!employerId.equals(tutoringOrder.getEmployerId())) {
			KiteException.throwException("数据异常");
		}
		
		// 修改需求状态
		Demand demand=demandMapper.selectById(tutoringOrder.getDemandId());
		demand.setStatus(Constant.DEMAND_STATUS_BEIJIE);
		demandMapper.updateById(demand);
		
		tutoringOrder.setStatus(Constant.TUTORING_ORDER_STATUS_HESHI);
		tutoringOrder.setHsTime(DateUtil.getFormatedDateTime());
		int i=tutoringOrderMapper.updateById(tutoringOrder);
		return DataResult.getSuccessDataResult(i);
	}
	
	/**
	 * 
	 * @param demandId
	 * @return
	 *  
	 */
	@ApiOperation(value = "应约并联系")
	@PostMapping("/appointment")
	public DataResult<Integer>  appointment(@RequestHeader("token")String token,@RequestParam String orderId) {
		Employer employer=employerService.queryEmployerByToken(token);
		String employerId=employer.getId();
		TutoringOrder tutoringOrder=tutoringOrderMapper.selectById(orderId);
		if(!employerId.equals(tutoringOrder.getEmployerId())) {
			KiteException.throwException("数据异常");
		}
		tutoringOrder.setStatus(Constant.TUTORING_ORDER_STATUS_DAIQUEREN);
		tutoringOrder.setYyblxTime(DateUtil.getFormatedDateTime());
		int i=tutoringOrderMapper.updateById(tutoringOrder);
		return DataResult.getSuccessDataResult(i);
	}
	
	
	
}
