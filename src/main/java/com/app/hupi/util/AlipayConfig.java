package com.app.hupi.util;

public class AlipayConfig {
	  public static final String COMMODITY_SUBJECT = "普拉订单支付款";//商品的标题
	    /**
	     * 支付宝网关（固定）
	     */
	    public static final String URL = "https://openapi.alipay.com/gateway.do";
	    /**
	     * APPID即创建应用后生成
	     * release 2017092508920497
	     */
	    public static String APP_ID = "2017092508921563";
	    /**
	     * 开发者应用私钥，由开发者自己生成
	     */
	    public static final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCwnM5C8vPBW0GhkIUwoat8Qcjxd/BTm9Ki6+AkieBMEEmdTbAqO6hfhS+kBQ1q+pWzlG8ewep+JUIYuFY1XEJUsJgN50rWekWpRlVBpuNFcgOQLys4h9wCIjqxduAMiK611XLTVDFOX+nmUbmKJM1StztvpMulnD4eLK9cr6itBr+2nr+uf+gnl1OWJlkYmdnOt5f7BBApmsDmYFpu2zW6AKAjkybCY4Pa7z03urrdPghqRdI9XMXWQ2hFfEXUp9DVBB7FUimeVshJ8qhL45EXOBEqjPlFk7vcvvdml4Pl0GE5I2BAB3+RnQTNK8HtEYPB4nKo3xMQy8EaX55H6EWhAgMBAAECggEAFGRWkHZa3LV7s3rrVf0kvrkdyXuuvQid3n/bpnxg8Idk6IvCzm2K+FR5dre6n+KFDHIUDnoXaC9HHXGTSp/3bK3jMJ/HDVKa3nundmgdj7QlYy1/EQzmwzxOiJUQM4JIoegeVPZApyr958EB6khriKRtcWuWs+2BYpyINux1Tlzqe1ipcPEZEBjgD3idnl1rij59YQSmNsX/ctzIOc6B+o3Tpr753IF+0MqQIEeXI+HKb5hjWRVWIXuqOIc+uZdLWICSl05NWYhdwCjlPG7e4O+pg40xmfKHuH0cFCLnFJA8TLzBKisn7oplW8afZeD7lNR+KU64boWEkN2Pqhh5UQKBgQD2mAvrsXutMvwe692hH1AgXAkPgn4vTFWg914V1fRyXYbIqTSQgzMvPZJ+Bkuja6sMsll8UPGmE70dxeRk9YpsPHAo0HKSbbpFDQV5IK2LfKfH7ZBvt/yJQG1xW/yI7dBV48DsMu6Lh5l+2KYHxlcEUZlxO6kQxnfk9pY7QzABrQKBgQC3WWam3v12wyW0d6ZcMM2z6TmXeYDLvUreVv90/Vrg9hwOD+G6xKP0UliN+WKpTrJVfj9B8nwiRPv3Mx6EhKZX6o2y6Eq2JqQMInuIPCgj/ZpRllglk8+jo8Rca7wx9l2Xh9MukI3nbi0049giRDdi/jREc+ndK1+ypl7VrwVaRQKBgQCa+wJ69kgZmbKVKH5ZdlmgjNdw9P2jcmFa0j1Ckf6ZJijA0n9j1+GCbjs0k3ZUViXhy/GrzR+i1kMEhExAG4YM8os3S5LGbsoj/cQT1bLcuNKmN6uQW10PUfu2xC5aTMSkRokIqhX8gZO6AiWPO1uTLkm2vd1K2sGdmFDhHB/+3QKBgHi8MlCofpf6IZYZRD9pE8vpRLdul1WSrP6Eo2vSekmxRwArjSWD38M/hnjXRBThcaXCoL04hG28p3889lelUjSKjAzH0oroHxaujlJD6troYPpg2mLEVvMXIHMLrr3n0fG6y2HaZteDQdmWP/kwmphhdOGuws2VhksKK2DjA89RAoGBAJ7JO4odIJmu1XRxrfhYEqTq6BG0epKV3gyyjqrKrpsmQpVbjEa3TF05F21aOjaAgr2xQs9xzc8ZPJc0Fr2/EJSzE+gXmYrFo3xaQz02hPCBC8BFXoJdGwW1qcYseUX+E3kLGvTOBl+3fpLUYLz4UonfbIVfhhBC5UoH/+Ekwimr";//设置成自己，也可以需改为可配置的
	    /**
	     * 参数返回格式，只支持json
	     */
	    public static final String FORMAT = "json";
	    /**
	     * 请求和签名使用的字符编码格式，支持GBK和UTF-8
	     */
	    public static final String CHARSET = "UTF-8";
	    /**
	     * 支付宝公钥，由支付宝生成
	     */
	    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA30+wkzWnKcFpQiPsJqjL7i81hvNVB88yF1rI7xE/BaczL9SP9SPMO3FOkG8X9bGROA4jlDgebue7vS8hBNZOwbSg2AhA7ihMK0KjTvCdekABLXwqKOd2voD1eNrclEWEbsU3zKOFYj/sp8nRGWovU/1tDd4xY8GF5TizoJaMAC5FGc9I02Zy/aG52FXqWmLvyM9684yADhqA21UMIEiln/2m9SOGbZRtmGPtJ6FPh5jPo5ufQ5khjx1Ao/KeEWwcIHgYBtxueA06ipbLlhK6vFYigmCfSafDfwgE2CWqrwXfofWR9GwxvD7wcfAXtHkvFbRKGA4zx7GGKfh6wY7HZwIDAQAB";
	    
	    /**
	     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
	     */
	    public static final String SIGN_TYPE = "RSA2";
	    /**
	     * 支付宝异步通知地址
	     */
	    public static String ALIPAY_NOTIFY_URL = "/pay/getAsynchronousNotificationREQ";
	    /**
	     * 支付超时时间
	     */
	    public static String TIMEOUT_EXPRESS = "5000";
	    /**
	     * 可支付的渠道
	     */
	    public static String ENABLE_PAY_CHANNELS = "";
	   
	    /**
	     * 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
	     */
	    public static final String PRODUCT_CODE = "QUICK_MSECURITY_PAY";    
	}
