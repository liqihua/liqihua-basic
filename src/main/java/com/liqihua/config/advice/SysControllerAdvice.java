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
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 全局异常处理类
 */
@ControllerAdvice(basePackages = {"com"})
public class SysControllerAdvice extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(SysControllerAdvice.class);


    /**
     * 其他异常
     * @param ex
     * @return
     */
    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
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
    @ResponseBody
    public WebResult requestMissingServletRequest(MissingServletRequestParameterException ex){
        return buildFailedInfo(ApiConstant.PARAM_IS_NULL,"："+ex.getParameterName());
    }


    /**
     *400错误->参数类型异常
     */
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public WebResult paramTypeError(TypeMismatchException ex){
        return buildFailedInfo(ApiConstant.PARAM_TYPE_ERROR," "+ex.getValue()+"："+ex.getRequiredType().getName());
    }



    /**
     * 400错误->参数格式有误
     */
    @ExceptionHandler({InvalidFormatException.class})
    @ResponseBody
    public WebResult invalidFormatException(InvalidFormatException ex){
        return buildFailedInfo(ApiConstant.PARAM_FORMAT_ERROR);
    }


    /**
     * 400错误->json参数格式有误
     */
    @ExceptionHandler({JsonParseException.class})
    @ResponseBody
    public WebResult jsonParamError1(JsonParseException ex){
        return buildFailedInfo(ApiConstant.PARAM_JSON_ERROR);
    }

    /**
     * 400错误->json参数格式有误
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public WebResult jsonParseError1(HttpMessageNotReadableException ex){
        String message = ex.getMessage();
        String rgex = "Unrecognized field \"(.*?)\" ";
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(ex.getMessage());
        if(m.find()){
            message = "非法参数 -> "+m.group(1);
        }
        return buildFailedInfo(ApiConstant.getMessage(ApiConstant.PARAM_JSON_ERROR)+" : "+message);
    }




    
    
}
