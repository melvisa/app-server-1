package com.app.hupi.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.app.hupi.constant.DataResult;
import com.app.hupi.enums.PayWay;
import com.app.hupi.util.AlipayConfig;
import com.app.hupi.vo.PayParamVo;
import com.app.hupi.wxpay.ConfigUtil;
import com.app.hupi.wxpay.PayCommonUtil;
import com.app.hupi.wxpay.XMLUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/pay")
@Api(tags = {"支付模块"})
@RestController
public class PayContorller {
	
	 /**
     * 雇主支付雇佣费，支付方：雇主，收款方：工人
     *
     * @param request
     * @param response
     * @param userId
     * @param payParams
     * @return
     */
	@PostMapping(value = "/orderString")
	@ApiOperation(value = "支付参数获取")
    public DataResult<Object> orderString(HttpServletRequest request,
    		HttpServletResponse response,  @RequestBody PayParamVo payParamVo) {
        String baseUrl = request.getRequestURL().toString();
        baseUrl = baseUrl.substring(0, baseUrl.indexOf("app-server") + "app-server".length());
        String payWay=payParamVo.getPayWay();
       // 支付宝支付
        if("A".equals(payWay)) {
        	return DataResult.getSuccessDataResult(aliPay(baseUrl, "1", "Hupu",  payParamVo.getOrderId()));
        }
        // 微信支付
        else if("W".equals(payWay)) {
        	return DataResult.getSuccessDataResult(wxPrePay(request, baseUrl, "1", payParamVo.getOrderId()));
        }
        return DataResult.getSuccessDataResult("未知支付方式");
	}

	//  微信支付
	private Map<Object, Object> wxPrePay(HttpServletRequest request, String baseUrl, String price, String orderId) {
        Map<Object, Object> resultMap = new HashMap<Object, Object>();
        // 设置回调地址-获取当前的地址拼接回调地址
//        String url = request.getRequestURL().toString();
//        String domain = url.substring(0, url.length() - 13);
        // 生产环境
        String notify_url = baseUrl + ConfigUtil.NOTIFY_URL;
        // 测试环境
        //String notify_url = "http://1f504p5895.51mypc.cn/cia/app/wxNotify.html";
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", ConfigUtil.APPID);
        parameters.put("mch_id", ConfigUtil.MCH_ID);
        parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
        parameters.put("body", AlipayConfig.COMMODITY_SUBJECT);
        parameters.put("out_trade_no", orderId); // 订单id
        parameters.put("fee_type", "CNY");
        parameters.put("total_fee", price);
        parameters.put("spbill_create_ip", request.getRemoteAddr());
        parameters.put("notify_url", notify_url);
        parameters.put("trade_type", "APP");
        // 设置签名
        String sign = PayCommonUtil.createSign("UTF-8", parameters);
        parameters.put("sign", sign);
        // 封装请求参数结束
        String requestXML = PayCommonUtil.getRequestXml(parameters);
        // 调用统一下单接口
        String result = PayCommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
        try {
            /** 统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。参与签名的字段名为appId，partnerId，prepayId，nonceStr，timeStamp，package。注意：package的值格式为Sign=WXPay **/
            Map<String, String> map = XMLUtil.doXMLParse(result);
            SortedMap<Object, Object> parameterMap2 = new TreeMap<Object, Object>();
            parameterMap2.put("appid", ConfigUtil.APPID);
            parameterMap2.put("partnerid", ConfigUtil.MCH_ID);
            parameterMap2.put("prepayid", map.get("prepay_id"));
            parameterMap2.put("package", "Sign=WXPay");
            parameterMap2.put("noncestr", PayCommonUtil.CreateNoncestr());
            // 本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
//            parameterMap2.put("timestamp",
//            		Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10)));
            parameterMap2.put("timestamp",new Date().getTime()/1000);
            String sign2 = PayCommonUtil.createSign("UTF-8", parameterMap2);
            parameterMap2.put("sign", sign2);
            resultMap.put("code", "200");
            resultMap.put("msg", parameterMap2);
            resultMap = parameterMap2;
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

	
	/**
     * 调用支付宝支付接口
     * 生成APP支付订单信息
     *
     * @param amount     总金额
     * @param body       商品描述
     * @param outTradeNo 订单编码
     * @return
     */
    private String aliPay(String baseUrl, String amount, String body, String outTradeNo) {
        // 实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, 
        		AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setPassbackParams(URLEncoder.encode(body)); // 描述信息 添加附加数据
        model.setSubject(AlipayConfig.COMMODITY_SUBJECT); // 商品标题
        model.setOutTradeNo(outTradeNo); // 商家订单编号(13位时间戳+4位认证人员信息表id后四位，不足前面补0)
        model.setTimeoutExpress(AlipayConfig.TIMEOUT_EXPRESS); // 超时关闭该订单时间
        model.setTotalAmount(amount); // 订单总金额
        model.setProductCode(AlipayConfig.PRODUCT_CODE); // 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
        model.setEnablePayChannels(AlipayConfig.ENABLE_PAY_CHANNELS);
        request.setBizModel(model);
        request.setNotifyUrl(baseUrl + AlipayConfig.ALIPAY_NOTIFY_URL); // 回调地址
        String orderStr = "";
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            com.alipay.api.response.AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderStr = response.getBody();
            System.out.println(orderStr);// 就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return orderStr;
    }
	
	  /**
     * 微信异步通知
     */
    @RequestMapping(value = "/wxNotify", method = {RequestMethod.POST, RequestMethod.GET})
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) {
        // 读取参数
        try {
            this.handleWXPayNotify(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handleWXPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        // 解析xml成map
        Map<String, String> m = XMLUtil.doXMLParse(sb.toString());
        // 过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 判断签名是否正确
        String resXml = "";
        if (PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
            if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
                // 这里是支付成功
                ////////// 执行自己的业务逻辑////////////////
                String mch_id = (String) packageParams.get("mch_id"); // 商户号
                String openid = (String) packageParams.get("openid"); // 用户标识
                String out_trade_no = (String) packageParams.get("out_trade_no"); // 商户订单号
                String total_fee = (String) packageParams.get("total_fee");
                String transaction_id = (String) packageParams.get("transaction_id"); // 微信支付订单号
                int payWay = PayWay.WechatPay.getPayway();
                int orderId = -1;
                int orderMoney = (int) Float.parseFloat(total_fee);
                List<Integer> idList = null;
                String info = JSON.toJSONString(packageParams);
                try {
                    if (out_trade_no.startsWith("wo")) {
                        orderId = this.decodeOrderId("wo", out_trade_no);
                    } else if (out_trade_no.startsWith("de")) {
                        idList = this.decodeOrderId("de", "_", out_trade_no);
                    } else {
                        orderId = Integer.parseInt(out_trade_no);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (orderId < 0 && CollectionUtils.isEmpty(idList)) {
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
                } else {
                    if (out_trade_no.startsWith("wo")) {
                        orderId = this.decodeOrderId("wo", out_trade_no);
                    } else if (out_trade_no.startsWith("de")) {
                        // 增加一条浏览记录
                    } else {
                    }
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                }

            } else {
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
        }
        // ------------------------------
        // 处理业务完毕
        // ------------------------------
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }
    
    /**
     * 支付宝回调
     *支付宝回调
     *支付宝回调
     * @param request
     * @param response
     * @throws AlipayApiException
     * @throws IOException
     */
    @RequestMapping(value = "/getAsynchronousNotificationREQ", method = {RequestMethod.POST, RequestMethod.GET})
    public void getAsynchronousNotificationREQ(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
        PrintWriter out = response.getWriter();
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
        if (flag) {
            if ("TRADE_SUCCESS".equals(params.get("trade_status"))) {
                // 订单金额
                String amount = params.get("total_amount");
                // 商户订单号
                String out_trade_no = params.get("out_trade_no");
                // 支付宝交易号
                String trade_no = params.get("trade_no");
                // 买家支付宝用户号
                String buyer_id = params.get("buyer_id");
                // 交易创建时间 格式为yyyy-MM-dd HH:mm:ss
                String gmt_create = params.get("gmt_create");
                // ...
                // 添加自己的业务逻辑，如记录交易成功，支付宝返回的信息
                // ...
                int payWay = PayWay.ALiPay.getPayway();
                int orderId = -1;
                int orderMoney = (int) Float.parseFloat(amount);
                List<Integer> idList = null;
                String info = JSON.toJSON(params).toString();
                out.print(info);
                try {
                    if (out_trade_no.startsWith("wo")) {
                    } else if (out_trade_no.startsWith("de")) {
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                out.print("success");
            } else {
                out.print("failure");
            }
        } else {
            out.print("failure");
        }
    }
    /**
     * 解码，去掉前缀
     *
     * @param orderId
     * @return
     */
    private int decodeOrderId(String prefix, String orderId) {
        return Integer.parseInt(orderId.replace(prefix + "_", ""));
    }
    private String encodeOrderId(String prefix, String separator, Integer userId, Integer dynamicId) {
        return String.format("%s%s%s%s%s", prefix, separator, userId, separator, dynamicId);
    }
    private List<Integer> decodeOrderId(String prefix, String separator, String orderId) {
        List<Integer> list = new LinkedList<>();
        String[] strings = orderId.split(separator);
        list.add(Integer.parseInt(strings[1]));
        list.add(Integer.parseInt(strings[2]));
        return list;
    }
}
