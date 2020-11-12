package com.app.hupi.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

@Component
public class MySimpleMappingExceptionResolver  implements HandlerExceptionResolver{
	
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) {
       
            try {
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                String mes="";
                // 为安全起见，只有业务异常我们对前端可见，否则统一归为系统异常
                if (exception instanceof BusinessException) {
                	mes=exception.getMessage();
                } 
                else {
                    mes="系统异常、请联系管理人员。\n"+exception.getMessage();
                }
                writer.write(JSONObject.toJSONString(com.app.hupi.constant.DataResult.getFailDataResult(mes)));
                writer.flush();
                writer.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        return null;
	}
}
