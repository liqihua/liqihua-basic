package com.liqihua.config.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.liqihua.common.basic.BaseController;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;


@ControllerAdvice(basePackages = {"com"})
public class SysControllerAdvice extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(SysControllerAdvice.class);


    /**
     * 其他异常
     * @param ex
     * @return
     */
    @ExceptionHandler({RuntimeException.class})
    public WebResult runtimeException(RuntimeException ex){
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw,true));
        LOG.error("ExceptionReport|"+ex.getClass().getName()+sw.toString(), this.getClass());
        return buildFailedInfo("服务器发生异常："+ex.getMessage());
    }


    /**
     * 400错误->缺少参数异常
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public WebResult requestMissingServletRequest(MissingServletRequestParameterException ex){
        return buildFailedInfo(ApiConstant.PARAM_IS_NULL,"："+ex.getParameterName());
    }


    /**
     *400错误->参数类型异常
     */
    @ExceptionHandler({TypeMismatchException.class})
    public WebResult requestTypeMismatch(TypeMismatchException ex){
    	return buildFailedInfo(ApiConstant.PARAM_TYPE_ERROR," "+ex.getValue()+"："+ex.getRequiredType().getName());
    }

    /**
     * 400错误->参数格式有误
     */
    @ExceptionHandler({InvalidFormatException.class})
    public WebResult invalidFormatException(InvalidFormatException ex){
        return buildFailedInfo(ApiConstant.PARAM_FORMAT_ERROR);
    }


    /**
     * 400错误->json参数格式有误
     */
    @ExceptionHandler({JsonParseException.class})
    public WebResult jsonParamError(JsonParseException ex){
        return buildFailedInfo(ApiConstant.PARAM_JSON_ERROR);
    }

    /**
     * 400错误->json参数格式有误
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public WebResult jsonParseError1(HttpMessageNotReadableException ex){
        return buildFailedInfo(ApiConstant.getMessage(ApiConstant.PARAM_JSON_ERROR)+":"+ex.getCause());
    }




    
    
}
