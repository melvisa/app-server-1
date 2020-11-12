package com.app.hupi.constant;


import io.swagger.annotations.ApiModelProperty;

public class DataResult<T> {

	 @ApiModelProperty("返回码")
	    private String resultCode;

	    @ApiModelProperty("返回信息")
	    private String resultMsg;

	    @ApiModelProperty("返回对象")
	    private T resultObject;
	    

	    public DataResult() {
	    }

	    public DataResult(String resultCode, String resultMsg) {
	        this.resultCode = resultCode;
	        this.resultMsg = resultMsg;
	    }

	    public DataResult(String resultCode, String resultMsg, T resultObject) {
	        this.resultCode = resultCode;
	        this.resultMsg = resultMsg;
	        this.resultObject = resultObject;
	    }


	    public String getResultCode() {
	        return resultCode;
	    }

	    public void setResultCode(String resultCode) {
	        this.resultCode = resultCode;
	    }

	    public String getResultMsg() {
	        return resultMsg;
	    }

	    public void setResultMsg(String resultMsg) {
	        this.resultMsg = resultMsg;
	    }

	    public T getResultObject() {
	        return resultObject;
	    }

	    public void setResultObject(T resultObject) {
	        this.resultObject = resultObject;
	    }

		public static <T> DataResult<T> getSuccessDataResult(T object){
	    	 return new DataResult<>(Constant.DATE_RESULT_RESULTCODE_SUCCESS,Constant.DATE_RESULT_RESULTMSG_SUCCESS,object);
	    }
		

		public static <T> DataResult<T> getFailDataResult(T object){
	    	 return new DataResult<>(Constant.DATE_RESULT_RESULTCODE_FAIL,Constant.DATE_RESULT_RESULTMSG_FAIL,object);
	    }
}
