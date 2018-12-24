package com.liqihua.config.aop;

import com.liqihua.common.basic.BaseController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @author liqihua
 * @since 2018/4/23
 * 针对controller层requestBody的spring validator校验统一返回格式
 */
@Aspect
@Component
public class RequestBodyValidateAspect extends BaseController {


    /**
     * 统一错误返回信息为参数名+错误信息
     * @param point
     * @param bindingResult
     * @return
     */
    @Around("execution (* com..*.*Controller.*(..)) && args(..,bindingResult)")
    public Object validatorAround(ProceedingJoinPoint point, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            return buildFailedInfo(fieldError.getField()+fieldError.getDefaultMessage());
        }else{
            try {
                return point.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return null;
    }


}
