package com.app.hupi.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hupi.constant.Constant;
import com.app.hupi.domain.AccountDetail;
import com.app.hupi.domain.Appointment;
import com.app.hupi.domain.Attention;
import com.app.hupi.domain.Code;
import com.app.hupi.domain.CouponDetail;
import com.app.hupi.domain.Demand;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.domain.TutoringOrder;
import com.app.hupi.exception.KiteException;
import com.app.hupi.mapper.AccountDetailMapper;
import com.app.hupi.mapper.DemandMapper;
import com.app.hupi.mapper.TutoringMapper;
import com.app.hupi.mapper.TutoringOrderMapper;
import com.app.hupi.service.AppointmentService;
import com.app.hupi.service.AttentionService;
import com.app.hupi.service.CodeService;
import com.app.hupi.service.CouponDetailService;
import com.app.hupi.service.TutoringService;
import com.app.hupi.util.BeanUtil;
import com.app.hupi.util.DateUtil;
import com.app.hupi.util.DoubleUtil;
import com.app.hupi.util.JsonUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.util.ListUtil;
import com.app.hupi.util.MapUtil;
import com.app.hupi.util.StringUtil;
import com.app.hupi.util.WebUtil;
import com.app.hupi.vo.DeliveryResumeVO;
import com.app.hupi.vo.EduExperienceVO;
import com.app.hupi.vo.SimpleTutoring;
import com.app.hupi.vo.SimpleUserVo;
import com.app.hupi.vo.TutoringAddVO;
import com.app.hupi.vo.TutoringDetailCmsVo;
import com.app.hupi.vo.TutoringDetailVO;
import com.app.hupi.vo.TutoringListVO;
import com.app.hupi.vo.TutoringRegisterVO;
import com.app.hupi.vo.UserVO;
import com.app.hupi.vo.WorkExperienceVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
public class TutoringServiceImpl implements TutoringService {

	@Autowired
	private TutoringMapper tutoringMapper;
	@Autowired
	private CodeService codeService;
	@Autowired
	private DemandMapper demandMapper;
	@Autowired
	private TutoringOrderMapper tutoringOrderMapper;
	@Autowired
	private AttentionService attentionService;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private CouponDetailService couponDetailService;
	@Autowired
	private AccountDetailMapper  accountDetailMapper;
	/**
	 * 投递简历
	 *  1、首先判断是否需要交纳意向金、如果不需要 则直接生成订单
	 *  2、如果需要、则需先进行抵扣券抵扣或者支付
	 */
	@Override
	@Transactional
	public DeliveryResumeVO deliveryResume(String tutoringId,String demandId) {
		DeliveryResumeVO  vo=new DeliveryResumeVO();
		Demand demand=demandMapper.selectById(demandId);
		if(demand==null) {
			KiteException.throwException("数据异常，查不到对应记录");
		}
		TutoringOrder tutoringOrder=new TutoringOrder();
		tutoringOrder.setId(KiteUUID.getId());
		tutoringOrder.setTutoringId(tutoringId);
		tutoringOrder.setDemandId(demandId);
		tutoringOrder.setEmployerId(demand.getEmployerId());
		tutoringOrder.setOrderId("TUTOTING"+KiteUUID.getId());
		tutoringOrder.setEarnestFlag(demand.getEarnestFlag());
		tutoringOrder.setCreateTime(DateUtil.getFormatedDateTime());
		String desc="";
		String payFlag="0";
		// 不需要诚意金
		if(demand.getEarnestFlag()=="0") {
			desc="不需要诚意金,直接生成订单";
			tutoringOrder.setStatus(Constant.TUTORING_ORDER_STATUS_DAIQUEREN);
			tutoringOrder.setPayFlag("0");
			tutoringOrder.setDesc(desc);
		}
		// 需要诚意金
		else {
			// 先抵扣抵扣券 再抵扣账户余额  再进行支付
			Tutoring tutoring=tutoringMapper.selectById(tutoringId);
			int coupon= tutoring.getCoupon();
			if(coupon>=10) {
				coupon=coupon-10;
				tutoringMapper.updateById(tutoring);
				tutoringOrder.setStatus(Constant.TUTORING_ORDER_STATUS_DAIQUEREN);
				tutoringOrder.setPayFlag("0");
				desc="需要诚意金,已进行抵扣券抵扣";
				tutoringOrder.setDesc(desc);
				
				//代金券明细
				CouponDetail  couponDetail=new CouponDetail();
				couponDetail.setId(KiteUUID.getId());
				couponDetail.setCreateTime(DateUtil.getFormatedDateTime());
				couponDetail.setDesc("简历投递使用："+demandId);
				couponDetail.setType("-");
				couponDetail.setAmount(10);
				couponDetail.setUserId(tutoring.getId());
				couponDetailService.addCouponDetail(couponDetail);
				
			}
			else {
				Double totalAccount=tutoring.getTotalAccount();
				if(totalAccount>=10) {
					totalAccount=totalAccount-10;
					tutoringMapper.updateById(tutoring);
					tutoringOrder.setStatus(Constant.TUTORING_ORDER_STATUS_DAIQUEREN);
					tutoringOrder.setPayFlag("0");
					desc="需要诚意金,已进行账户余额抵扣";
					tutoringOrder.setDesc(desc);
				}
				// 抵扣券账户余额都不够抵扣，需要进行支付
				else {
					tutoringOrder.setStatus(Constant.TUTORING_ORDER_STATUS_DAIZHIFU);
					tutoringOrder.setPayFlag("1");
					desc="需要诚意金,需要支付";
					payFlag="1";
					tutoringOrder.setDesc(desc);
				}
			}
		}
		tutoringOrderMapper.insert(tutoringOrder);
		tutoringOrder=tutoringOrderMapper.selectById(tutoringOrder.getId());
		vo.setDesc(desc);
		vo.setOrderId(tutoringOrder.getOrderId());
		vo.setPayFlag(payFlag);
		return vo;
	}
	
	
	
	@Override
	public Tutoring addTutoring(TutoringAddVO tutoringAddVO) {
		UserVO userVO=(UserVO) WebUtil.getSession().getAttribute("user");
		if(userVO==null) {
			KiteException.throwException("登录信息失效，请重新登录");
		}
		String number=userVO.getNumber();
		Tutoring t=queryTutoringByNumber(number);
		BeanUtil.copyProperties(tutoringAddVO, t);
		t.setEduExperience(JsonUtil.toJson(tutoringAddVO.getEduExperience()));
		t.setWorkExperience(JsonUtil.toJson(tutoringAddVO.getWorkExperienceList()));
		tutoringMapper.updateById(t);
		return t;
	}
	@Override
	public Tutoring queryTutoringByNumber(String number) {
		Tutoring tutoring=new Tutoring();
		tutoring.setNumber(number);
		tutoring=tutoringMapper.selectOne(tutoring);
		return tutoring;
	}
	@Override
	public Tutoring register(TutoringRegisterVO tutoringRegisterVO) {
		Tutoring  t = queryTutoringByNumber(tutoringRegisterVO.getNumber());
		if(t!=null) {
			KiteException.throwException("号码已注册、请直接登录");
		}
	    t=new Tutoring();
	    String yqm=KiteUUID.randomString(6);
		BeanUtil.copyProperties(tutoringRegisterVO, t);
		t.setId(KiteUUID.getId());
		t.setHeadImage(Constant.DEFAULT_HEAD_IMAGE_TUTOTING);
		t.setName(yqm);
		t.setCreateTime(DateUtil.getFormatedDateTime());
		t.setYqmSelf(yqm);
		tutoringMapper.insert(t);
		return t;
	}
	@Override
	public List<TutoringListVO> listTutoringList(String employerId, String tutoringType,String lng, String lat, int pageIndex,
			int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		String sqlSelect="id, name ,head_image as headImage , tags ,"
				+ "tutoring_type as tutoringType , auth_info  as authInfo,"
				+ "tutoring_identity as tutoringIdentity,"
				+ "teache_time as teacheTime,sex ,age,lng,lat,cert_flag ";
		EntityWrapper<Tutoring> wrapper=new EntityWrapper<Tutoring>();
		wrapper.setSqlSelect(sqlSelect);
		wrapper.like("tutoring_type", tutoringType);
		List<TutoringListVO> result=new ArrayList<>();
		List<Map<String, Object>> list=tutoringMapper.selectMaps(wrapper);
		for(Map<String, Object> map:list) {
			TutoringListVO vo=MapUtil.mapToBean(map, TutoringListVO.class,"tags");
			String tags=MapUtil.getString(map, "tags");
			if(tags!=null) {
				vo.setTags(Arrays.asList(tags.split(",")));
			}
			List<String>subs=changeSubjectList(MapUtil.getString(map, "tutoringType"));
			vo.setSubjectList(subs);
			if(vo.getAuthInfo()==null) {
				vo.setAuthInfo("未实名认证");
			}
			else {
				vo.setAuthInfo("已实名认证");
			}
			//vo.setTutoringIdentity(codeService.queryCodeValueByGroupAndValue("tutoring_identity", vo.getTutoringIdentity()));
			vo.setTutoringIdentity(MapUtil.getString(map, "tutoringIdentity"));
			result.add(vo);
		}
		// 个性标签  评价   是否关注  是否预约
		return result;
	}
	
	@Override
	public List<TutoringListVO> listTutoringListByParams(int pageIndex, int pageSize, Map<String, String> params) {
		PageHelper.startPage(pageIndex, pageSize);
		String sqlSelect="id, name ,head_image as headImage , tags ,"
				+ "tutoring_type as tutoringType , auth_info  as authInfo,"
				+ "tutoring_identity as tutoringIdentity,"
				+ "teache_time as teacheTime,sex ,age,lng,lat,cert_flag ";
		EntityWrapper<Tutoring> wrapper=new EntityWrapper<Tutoring>();
		wrapper.setSqlSelect(sqlSelect);
		
		String classType=MapUtil.getString(params, "classType");
		String tutoringType=MapUtil.getString(params, "tutoringType");
		if(tutoringType!=null) {
			classType=null;
		}
		String sex=MapUtil.getString(params, "sex");
		if(StringUtil.isNotEmpty(sex)) {
			if("1".equals(sex)) {
				sex="男";
			}
			else if("0".equals(sex)) {
				sex="女";
			}
		}
		String tutoringIdentity=MapUtil.getString(params, "tutoringIdentity");
		
		wrapper.like(StringUtil.isNotEmpty(classType), "class_type",classType);
		wrapper.like(StringUtil.isNotEmpty(tutoringType), "tutoring_type",tutoringType);
		wrapper.eq(StringUtil.isNotEmpty(sex), "sex",sex);
		wrapper.eq(StringUtil.isNotEmpty(tutoringIdentity), "tutoring_identity",tutoringIdentity);
		
		List<TutoringListVO> result=new ArrayList<>();
		List<Map<String, Object>> list=tutoringMapper.selectMaps(wrapper);
		for(Map<String, Object> map:list) {
			TutoringListVO vo=MapUtil.mapToBean(map, TutoringListVO.class,"tags");
			String tags=MapUtil.getString(map, "tags");
			if(tags!=null) {
				vo.setTags(Arrays.asList(tags.split(",")));
			}
			List<String>subs=changeSubjectList(MapUtil.getString(map, "tutoringType"));
			vo.setSubjectList(subs);
			if(vo.getAuthInfo()==null) {
				vo.setAuthInfo("未实名认证");
			}
			else {
				vo.setAuthInfo("已实名认证");
			}
			vo.setTutoringIdentity(MapUtil.getString(map, "tutoringIdentity"));
			result.add(vo);
		}
		// 个性标签  评价   是否关注  是否预约
		return result;
	}
	


	private List<String> changeSubjectList(String tutoringType){
		List<String> list=new ArrayList<>();
		if(StringUtil.isEmpty(tutoringType)) {
			return list;
		}
		String[] temp= {};
		if(tutoringType.contains(Constant.STRING_SEPARATOR)) {
			temp=tutoringType.split(Constant.STRING_SEPARATOR);
		}
		else {
			temp=new String[1];
			temp[0]=tutoringType;
		}
		for(String s:temp) {
			s=s.replace("tutoring_type_", "");
			String[] ss=s.split("_");
			String tutoring_type=codeService.queryCodeValueByGroupAndValue("tutoring_type", "tutoring_type_"+ss[0]);
			String sub=codeService.queryCodeValueByGroupAndValue("tutoring_type_"+ss[0], "tutoring_type_"+ss[0]+"_"+ss[1]);
			if(tutoring_type!=null&&sub!=null) {
				list.add(tutoring_type.split(",")[0]+sub.split(",")[0]);
			}
		}
		return list;
	}
	@Override
	public TutoringDetailVO queryTutoringDetail(String id,String employerId) {
		Tutoring tutoring=tutoringMapper.selectById(id);
		if(tutoring==null) {
			return null;
		}
		TutoringDetailVO vo=new TutoringDetailVO();
		BeanUtil.copyProperties(tutoring, vo);
		// 工作经历
		List<WorkExperienceVO> workExperienceList=JsonUtil.parseArray(tutoring.getWorkExperience(), WorkExperienceVO.class);
		// 教育经历
		EduExperienceVO eduExperience=JsonUtil.parseObject(tutoring.getEduExperience(), EduExperienceVO.class);
		vo.setWorkExperienceList(workExperienceList);
		vo.setEduExperience(eduExperience);
		// 证书
		if(tutoring.getAuthInfo()==null) {
			vo.setAuthInfo("未实名认证");
		}
		else {
			vo.setAuthInfo("已实名认证");
		}
		
		// images相册
		String album=tutoring.getAlbum();
		if(album!=null) {
			vo.setImages(Arrays.asList(album.split(",")));
		}
		//vo.setTutoringIdentity(codeService.queryCodeValueByGroupAndValue("tutoring_identity", tutoring.getTutoringIdentity()));
		// 个性标签和评价字段，
		// 是否关注
		if(StringUtil.isNotEmpty(employerId)) {
			Attention attention=attentionService.queryAttention(employerId, tutoring.getId());
			if(attention==null) {
				vo.setAttentionTag("0");
			}
			else {
				vo.setAttentionTag("1");
			}
		}
		String tags=tutoring.getTags();
		if(tags!=null) {
			vo.setTags(Arrays.asList(tags.split(",")));
		}
		// 是否预约
		if(StringUtil.isNotEmpty(employerId)) {
			Appointment appointment=appointmentService.queryAppointmentBy(employerId, tutoring.getId());
			if(appointment==null) {
				vo.setAppointmenTag("0");
			}
			else {
				vo.setAppointmenTag("1");
			}
		}
		return vo;
	}
	@Override
	public Tutoring updateTutoring(Tutoring tutoring) {
		tutoringMapper.updateById(tutoring);
		return tutoring;
	}



	@Override
	public Tutoring queryTutoringByToken(String token) {
		// TODO Auto-generated method stub
		Tutoring tutoring=new Tutoring();
		tutoring.setToken(token);
		tutoring =tutoringMapper.selectOne(tutoring);
		if(tutoring!=null) {
			String tokenTime=tutoring.getTokenTime();
			// 如果当前时间和tokenTime相差6小时、则token失效
			Date minDate=DateUtil.parseStrToDate(tokenTime, DateUtil.DATE_TIME_FORMAT);
			Date maxDate=new Date();
			long subTime=maxDate.getTime()-minDate.getTime();
			if(subTime>6*60*3600*1000) {
				KiteException.throwException("登录已失效。请重新登录");
			}
			//如果没有失效  则更新tokenTime
			tutoring.setTokenTime(DateUtil.getFormatedDateTime());
			tutoringMapper.updateById(tutoring);
		}
		return tutoring;
	}



	@Override
	public List<SimpleUserVo> queryByYqm(String yqm) {
		EntityWrapper<Tutoring> wrapper=new EntityWrapper();
		wrapper.eq("yqm", yqm);
		List<Tutoring> list=tutoringMapper.selectList(wrapper);
		List<SimpleUserVo> voList=new ArrayList<>();
		for(Tutoring t:list) {
			SimpleUserVo vo=new SimpleUserVo();
			vo.setId(t.getId());
			vo.setYqm(t.getYqmSelf());
			vo.setHeadImage(t.getHeadImage());
			vo.setName(t.getName());
			vo.setTime(t.getCreateTime());
			voList.add(vo);
		}
		return voList;
	}



	@Override
	public Tutoring queryTutoringByUnicode(String unicode) {
		Tutoring tutoring=new Tutoring();
		tutoring.setUnicode(unicode);
		tutoring=tutoringMapper.selectOne(tutoring);
		return tutoring;
	}



	@Override
	public PageInfo<SimpleTutoring> pageInfo(int pageNum, int pageSize, String name, String number) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<Tutoring> wrapper=new EntityWrapper();
		if(StringUtil.isNotEmpty(number)) {
			wrapper.eq("number", number);
		}
		if(StringUtil.isNotEmpty(name)) {
			wrapper.eq("name", name);
		}
		List<Tutoring> list=tutoringMapper.selectList(wrapper);
		PageInfo<Tutoring> pageInfo=new PageInfo<>(list);
		PageInfo<SimpleTutoring> info=new PageInfo<>();
		BeanUtil.copyProperties(pageInfo, info);
		List<SimpleTutoring>simpleList=BeanUtil.copyPropsForList(list, SimpleTutoring.class);
		info.setList(simpleList);
		return info;
	}



	@Override
	public TutoringDetailCmsVo queryTutoringDetailCmsVo(String id) {
		Tutoring  tutoring=tutoringMapper.selectById(id);
		TutoringDetailCmsVo  vo=new TutoringDetailCmsVo();
		BeanUtil.copyProperties(tutoring, vo);
		List<WorkExperienceVO> workExperienceList=JsonUtil.parseArray(tutoring.getWorkExperience(), WorkExperienceVO.class);
		// 教育经历
		EduExperienceVO eduExperience=JsonUtil.parseObject(tutoring.getEduExperience(), EduExperienceVO.class);
		vo.setWorkExperienceList(workExperienceList);
		vo.setEduExperience(eduExperience);
		// 证书
		if(tutoring.getAuthInfo()==null) {
			vo.setAuthInfo("未实名认证");
		}
		else {
			vo.setAuthInfo("已实名认证");
		}
		// images相册
		String album=tutoring.getAlbum();
		if(album!=null) {
			vo.setImages(Arrays.asList(album.split(",")));
		}
		
		String tags=tutoring.getTags();
		if(tags!=null) {
			vo.setTags(Arrays.asList(tags.split(",")));
		}
		List<String>subs=changeSubjectList(tutoring.getTutoringType());
		vo.setSubjectList(subs);
		return vo;
	}



	@Override
	public Tutoring queryOneByYqm(String yqm) {
			Tutoring tutoring=new Tutoring();
			tutoring.setYqmSelf(yqm);
			tutoring=tutoringMapper.selectOne(tutoring);
			return tutoring;
		}



	/**
	 * tutoringId 支付者ID
	 */
	@Override
	public void updateTutoringAccount(String tutoringId, String money) {
		Tutoring tutoring=tutoringMapper.selectById(tutoringId);
		String yqm=tutoring.getYqm();
		Tutoring tjr=this.queryOneByYqm(yqm);
		fen(tjr,0,money,tutoring);
		if(StringUtil.isNotEmpty(tjr.getYqm())) {
			Tutoring tjr2=this.queryOneByYqm(tjr.getYqm());
			fen(tjr2,1,money,tutoring);
			if(StringUtil.isNotEmpty(tjr2.getYqm())) {
				Tutoring tjr3=this.queryOneByYqm(tjr2.getYqm());
				fen(tjr3,2,money,tutoring);
			}
		}
		
		
	}
	
	
	private void fen(Tutoring tjr,int level,String money,Tutoring tutoring) {
		int num=queryNum(tjr.getYqmSelf());
		if(num<10) {
			return ;
		}
		String param="";
		if(num<100) {
			param="acount_10";
		}
		else if(num<1000) {
			param="acount_100";
		}
		else {
			param="acount_1000";
		}
		List<Code> list=codeService.listCodeByGroup(param);
		if(ListUtil.isNotEmpty(list)) {
			Code code=list.get(0);
			String value=code.getValue();
			String bili=value.split(",")[level];
			double d=DoubleUtil.mul(Double.parseDouble(money), Double.parseDouble(bili));
			tjr.setTotalAccount(tjr.getTotalAccount()+d);
			tutoringMapper.updateById(tjr);
			// TODO 账户流水明细
			AccountDetail accountDetail=new AccountDetail();
			accountDetail.setId(KiteUUID.getId());
			accountDetail.setCreateTime(DateUtil.getFormatedDateTime());
			accountDetail.setMoney(d+"");
			accountDetail.setType("+");
			accountDetail.setDesc((level+1)+"级代理佣金分配"+tutoring.getId());
			accountDetailMapper.insert(accountDetail);
			
		}
	}
	
	
	private  int queryNum(String yqm) {
		EntityWrapper<Tutoring> wrapper=new EntityWrapper<Tutoring>();
		wrapper.eq("yqm", yqm).eq("auth_info", "success");
		return tutoringMapper.selectCount(wrapper);
	}
}



	
	
	
