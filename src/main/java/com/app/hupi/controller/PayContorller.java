package com.app.hupi.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.app.hupi.constant.DataResult;
import com.app.hupi.enums.PayWay;
import com.app.hupi.wxpay.PayCommonUtil;
import com.app.hupi.wxpay.XMLUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/pay")
@Api(tags = {"支付模块"})
@RestController
public class PayContorller {
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
