package com.app.hupi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.Constant;
import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Comment;
import com.app.hupi.domain.Demand;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.domain.TutoringOrder;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.DemandMapper;
import com.app.hupi.mapper.EmployerMapper;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.mapper.TutoringOrderMapper;
import com.app.hupi.service.CodeService;
import com.app.hupi.service.CommentService;
import com.app.hupi.service.TutoringOrderService;
import com.app.hupi.util.BeanUtil;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.JsonUtil;
import com.app.hupi.util.UserUtil;
import com.app.hupi.util.WebUtil;
import com.app.hupi.vo.EmployerOrderListVO;
import com.app.hupi.vo.UserVO;

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
	private CodeService codeService;
	@ApiOperation(value = "雇主查询订单列表")
	@GetMapping("/listEmployerOrder")
	@ApiImplicitParams({
	        @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "String")
	 })
	public DataResult<List<EmployerOrderListVO>> listEmployerOrder(
			@RequestParam(name="pageNum",required=true)int pageNum,
			@RequestParam(name="pageSize",required=true)int pageSize,
			@RequestParam(name="status",required=true)String status) {
		UserVO userVO=UserUtil.getUserVO();
		List<TutoringOrder> list=tutoringOrderService.listTutoringOrderWithEmployer(pageNum, pageSize, userVO.getId(), status);
		List<EmployerOrderListVO> voList=new ArrayList<>();
		for(TutoringOrder tutoringOrder:list) {
			EmployerOrderListVO vo=changeEmployerOrderListVO(tutoringOrder);
			voList.add(vo);
		}
		return DataResult.getSuccessDataResult(voList);
	}
	
	public static void main(String[] args) {
		String s="tutoring_type_xiaoxue_shuxue";
		System.out.println(s.lastIndexOf("_", s.length()));
		System.out.println(s.substring(0, s.lastIndexOf("_", s.length())));
	}
	
	private EmployerOrderListVO changeEmployerOrderListVO(TutoringOrder tutoringOrder) {
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
		   String classNameDesc = codeService.queryCodeValueByGroupAndValue("tutoring_type", className);
		   String subName=codeService.queryCodeValueByGroupAndValue(className, s);
			if(map.containsKey(className)) {
				map.put(className, map.get(classNameDesc)+","+subName);
			}
			else {
				map.put(classNameDesc, subName);
			}
		}
		vo.setTutoringType(JsonUtil.toJson(map.values()));
		vo.setCommentFlag("0");
		vo.setOrderId(tutoringOrder.getId());
		if("待确认".equals(tutoringOrder.getStatus())) {
			vo.setNumber(tutoring.getNumber());
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
	public DataResult<Integer> inappropriate(@RequestParam String orderId) {
		UserVO userVO=(UserVO) WebUtil.getSession().getAttribute("user");
		TutoringOrder tutoringOrder=tutoringOrderMapper.selectById(orderId);
		if(!userVO.getId().equals(tutoringOrder.getEmployerId())) {
			KiteException.throwException("数据异常");
		}
		tutoringOrder.setStatus("不合适");
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
	public DataResult<Integer>  appropriate(@RequestParam String orderId) {
		UserVO userVO=(UserVO) WebUtil.getSession().getAttribute("user");
		TutoringOrder tutoringOrder=tutoringOrderMapper.selectById(orderId);
		if(!userVO.getId().equals(tutoringOrder.getEmployerId())) {
			KiteException.throwException("数据异常");
		}
		tutoringOrder.setStatus("合适");
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
	public DataResult<Integer>  appointment(@RequestParam String orderId) {
		UserVO userVO=(UserVO) WebUtil.getSession().getAttribute("user");
		TutoringOrder tutoringOrder=tutoringOrderMapper.selectById(orderId);
		if(!userVO.getId().equals(tutoringOrder.getEmployerId())) {
			KiteException.throwException("数据异常");
		}
		tutoringOrder.setStatus("应约并联系");
		tutoringOrder.setYyblxTime(DateUtil.getFormatedDateTime());
		int i=tutoringOrderMapper.updateById(tutoringOrder);
		return DataResult.getSuccessDataResult(i);
	}
	
	
	
}
