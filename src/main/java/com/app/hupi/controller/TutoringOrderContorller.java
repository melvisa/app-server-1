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
import com.app.hupi.mapper.DemandMapper;
import com.app.hupi.mapper.EmployerMapper;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.service.CodeService;
import com.app.hupi.service.CommentService;
import com.app.hupi.service.TutoringOrderService;
import com.app.hupi.service.TutoringService;
import com.app.hupi.util.BeanUtil;
import com.app.hupi.util.JsonUtil;
import com.app.hupi.util.UserUtil;
import com.app.hupi.util.WebUtil;
import com.app.hupi.vo.DeliveryResumeVO;
import com.app.hupi.vo.EmployerOrderListVO;
import com.app.hupi.vo.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/tutoringOrder")
@Api(tags = {"家教模块"})
@RestController
public class TutoringOrderContorller {

	
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
	private CodeService codeService;
	@Autowired
	private TutoringService tutoringService;
	
	/**
	 * 
	 * @param demandId
	 * @return
	 *  如果需要诚意金 则需要先抵扣用户抵扣券或者先支付诚意金
	 */
	@ApiOperation(value = "投递简历")
	@PostMapping("/deliveryResume")
	public DataResult<DeliveryResumeVO> deliveryResume(@RequestParam String demandId) {
		UserVO userVO=(UserVO) WebUtil.getSession().getAttribute("user");
		DeliveryResumeVO deliveryResumeVO=tutoringService.deliveryResume(userVO.getId(), demandId);
		return DataResult.getSuccessDataResult(deliveryResumeVO);
	}
	
	@ApiOperation(value = "家教查询订单列表")
	@GetMapping("/listTutoringOrder")
	@ApiImplicitParams({
	        @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "status", value = "状态", required = true, dataType = "String")
	 })
	public DataResult<List<Demand>> listTutoringOrder(
			@RequestParam(name="pageNum",required=true)int pageNum,
			@RequestParam(name="pageSize",required=true)int pageSize,
			@RequestParam(name="status",required=true)String status) {
		UserVO userVO=UserUtil.getUserVO();
		List<TutoringOrder> list=tutoringOrderService.listTutoringOrderWithEmployer(pageNum, pageSize, userVO.getId(), status);
		List<Demand> demandList=new ArrayList<>();
		for(TutoringOrder t:list) {
			Demand d=demandMapper.selectById(t.getDemandId());
			demandList.add(d);
		}
		return DataResult.getSuccessDataResult(demandList);
	}
	
	
	
	
}
