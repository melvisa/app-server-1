package com.app.hupi.constant;

public class Constant {
	
	
	public static  final String DEMAND_STATUS_ZHENGCHENG="1";
	
	public static  final String DEMAND_STATUS_BEIJIE="2";
	
	// 默认头像-家教
	public static final String DEFAULT_HEAD_IMAGE_TUTOTING="http://liujiancheng.5gzvip.idcfengye.com/images/product/1.png";
	// 默认头像-雇主
	public static final String DEFAULT_HEAD_IMAGE_EMPLOYER="http://liujiancheng.5gzvip.idcfengye.com/images/product/1.png";
	
	public  static final String DEFAULT_MONEY="10";
	
	// 待支付
	public static final String TUTORING_ORDER_STATUS_DAIZHIFU="0";
	// 待应约
	public static final String TUTORING_ORDER_STATUS_DAIYINGYUE="1";
	// 待确认
	public static final String TUTORING_ORDER_STATUS_DAIQUEREN="2";
	// 合适
	public static final String TUTORING_ORDER_STATUS_HESHI="3";
	// 不合适
	public static final String TUTORING_ORDER_STATUS_BUHESHI="4";
	
	
	
	public static final String STRING_SEPARATOR=",";
	
	public static final String LOGIN_USER="loginUser";
	
	public static final String DATE_RESULT_RESULTCODE_SUCCESS="000001";
	
	public static final String DATE_RESULT_RESULTCODE_FAIL="000000";
	
	public static final String DATE_RESULT_RESULTMSG_SUCCESS="调用成功";
	
	public static final String DATE_RESULT_RESULTMSG_FAIL="调用失败";
	
	
	public static final String DATE_RESULT_RESULT_FLAG_SUCCESS="1";
	
	public static final String DATE_RESULT_RESULT_FLAG_FAIL="0";
	
	/** 是否删除--否 **/
	public static final String IS_DEL_NO = "0";
	/** 是否删除--是 **/
	public static final String IS_DEL_YES = "1";

	/** 状态--有效 **/
	public static final String STATUS_USE = "1";
	/** 状态--失效 **/
	public static final String STATUS_INVALID = "0";
	
	
	/**评论状态 --待审核*/
	public static final String COMMENT_STATUS_CHECK="0";
	/**评论状态 --审核通过*/
	public static final String COMMENT_STATUS_YES="1";
	/**评论状态 --审核不通过*/
	public static final String COMMENT_STATUS_NO="2";
	
	

	/** 商品状态--正常售卖 **/
	public static final String PROD_STATUS_NORMAL = "1";
	/** 商品状态--失效 下架 **/
	public static final String PROD_STATUS_INVALID = "0";
	/** 商品状态--售薪 **/
	public static final String PROD_STATUS_SELL_OUT = "2";

	/** 商品类型-团购类型 **/
	public static final String PROD_TYPE_APPOINTMENT = "1";
	/** 商品类型-直接订购 **/
	public static final String PROD_TYPE_DIRECT = "2";
	/** 商品类型-积分团购类型 **/
	public static final String PROD_TYPE_APPOINTMENT_INTEGRAL = "3";
	/** 商品类型-积分直接订购 **/
	public static final String PROD_TYPE_DIRECT_INTEGRAL = "4";

	/** 首页商品展示方式-横向展示 **/
	public static final String SHOW_TYPE_CROSSRANGE = "1";
	/** 首页商品展示方式-垂直展示 **/
	public static final String SHOW_TYPE_VERTICAL = "2";

	/** 垂直展示类别 **/
	public static final String SHOW_TYPE_VERTICAL_CATEGORY = "2";

	/** 是否展示在首页-不展示 **/
	public static final String SHOW_TYPE_ON_INDEX_HIDDEN = "0";
	/** 是否展示在首页-展示 **/
	public static final String SHOW_TYPE_ON_INDEX_SHOW = "1";

	/** 返回评论数量**/
	public static final int COMMENT_NUM = 10;
	
	public static final int PAGE_SIZE = 3;
	
	/** 短主键长度*/
	public static final int SHORT_STRING_LENGTH=6;
	
	/** 优惠券状态-新增**/
	public static final String COUPON_STATUS_NEW="0";
	/** 优惠券状态-可用**/
	public static final String COUPON_STATUS_AVAILABLE="1";
	/** 优惠券状态-即将过期**/
	public static final String COUPON_STATUS_NEARLY_XPIRED="2";
	/** 优惠券状态-失效**/  
	public static final String COUPON_STATUS_INVALID="3";
	/** 优惠券状态- 已使用*/
	public static final String COUPON_STATUS_ALREADY_USED="4";
	/** 优惠券状态- 不符合使用条件*/
	public static final String COUPON_STATUS_CAN_NOT_USED="5";
	
	
	/** 优惠券类型- 价格限制类型*/
	public static final String COUPON_TEMPLATE_TYPE_PRICE="1";
	/** 优惠券类型- 商品限制类型*/
	public static final String COUPON_TEMPLATE_TYPE_PRODUCT="2";
	/** 可用优惠券标识- 无可用*/
	public static final String AVAILABLE_COUPON_FLAG_UNUSE="0";
	/** 可用优惠券标识- 有可用*/
	public static final String AVAILABLE_COUPON_FLAG_USE="1";
	
	/** 积分变更类型- 增加*/
	public static final String INTEGRAL_CHANGE_TYPE_SUM="+";
	/** 积分变更类型- 减少*/
	public static final String INTEGRAL_CHANGE_TYPE_SUB="-";
	/** 积分来源- 签到*/
	public static final String INTEGRAL_SOURCE_TYPE_SIGN_IN="signIn";
	/** 积分来源- 订单支付*/
	public static final String INTEGRAL_SOURCE_TYPE_ORDER_PAY="orderPay";
	
	/**签到积分数*/
	public static final int SIGN_IN_INTEGRAL=10;
	
	public static final String INTEGRAL_USER_IN_ORDER_REMARK="支付订单：";
	
	public static final String INTEGRAL_SIGN_IN_ADD="签到积分";
	
	/** 订单状态- 待支付**/
	public static final String ORDER_STATUS_WAIT_PAY="1";
	/** 订单状态- 待商家确认订单、待发货**/
	public static final String ORDER_STATUS_WAIT_DELIVER="2";
	/** 订单状态- 待收货**/
	public static final String ORDER_STATUS_WAIT_HARVEST="3"; 
	/** 订单状态- 待评价**/
	public static final String ORDER_STATUS_WAIT_COMMENT="4";
	/** 订单状态- 正常结束**/
	public static final String ORDER_STATUS_FINISH_NORMAL="5";
	/** 订单状态- 非正常结束**/
	public static final String ORDER_STATUS_FINISH_UN_NORMAL="6";
	
	
	/**购物车状态 -正常订购*/
	public static final String SHOPPING_CAR_STATUS_NORMAL="1";
	public static final String SHOPPING_CAR_STATUS_NORMAL_DESC="正常订购";
	/**购物车状态 积分不足*/
	public static final String SHOPPING_CAR_STATUS_INTEGRAL_LESS="2";
	public static final String SHOPPING_CAR_STATUS_INTEGRAL_LESS_DESC="积分不足";
	
	/** 配送方式-配送*/
	public static final String DELIVERY_TYPE_DELIVERY="1";
	/** 配送方式-自提*/
	public static final String DELIVERY_TYPE_SELF_MENTION="2";
	
	/**地址是否默认-是*/
	public static final String ADDRESS_IS_DEFAULT_YES="1";
	/**地址是否默认-否*/
	public static final String ADDRESS_IS_DEFAULT_NO="0";
	
	/**用户身份-新客户*/
	public static final String USER_IDENTITY_NEW="new";
	/**用户身份-订单客户*/
	public static final String USER_IDENTITY_ORDER="order";
	
	/**用户状态-正常*/
	public static final String USER_STATUS_NORMAL="normal";
	
	/** 新人专享类别id*/
	public static final String NEW_PERSON_CATEGORY_ID="000000";
	
	/** 个人收藏类别id*/
	public static final String PREFERENCE_CATEGORY_ID="000111";
	
	
	
	public static final String SYS_PARAM_CODE_ACCESS_TOKEN="SYS_PARAM_CODE_ACCESS_TOKEN";
	public static final String SYS_PARAM_CODE_SERVICE_NUMBER="SYS_PARAM_CODE_SERVICE_NUMBER";
	
}
