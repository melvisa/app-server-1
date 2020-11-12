package com.app.hupi.enums;

public enum PayWay {
	 // 0-微信，1-支付宝，2-余额支付
    WechatPay(0), ALiPay(1), MoneyPay(2);

    int payway;

    PayWay(int way) {
        payway = way;
    }

    public int getPayway() {
        return payway;
    }

    public void setPayway(int payway) {
        this.payway = payway;
    }

    @Override
    public String toString() {
        return String.valueOf(payway);
    }
}
