package com.liqihua.config.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 
 * @author liqihua
 * @since 2018/11/19
 */

@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution (* com..*.*Controller.*(..))")
    public void controllerAspect() {
    }

	@Around("controllerAspect()")
    public Object controllerAround(ProceedingJoinPoint joinPoint) throws Throwable{
        /**
         * 接口增加日志
         */
        String classAndMethodName = null;
        //获取当前请求属性集
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求
        HttpServletRequest request = sra.getRequest();
        //获取请求地址
        String requestUrl = request.getRequestURL().toString();
        //记录请求地址
        log.info("请求路径[{}]", requestUrl);
        //记录请求开始时间
        long startTime = System.currentTimeMillis();
        try {
            Class<?> target = joinPoint.getTarget().getClass();
            Class<?>[] par = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
            String methodName = joinPoint.getSignature().getName();
            //获取当前执行方法
            Method currentMethod = target.getMethod(methodName, par);
            //拼接输出字符串
            classAndMethodName = target.getName() + " 的 " + currentMethod.getName() + " 方法";
            log.info("正在执行：{}", classAndMethodName);
            //打印参数
            Enumeration<String> enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()){
                String param = enumeration.nextElement();
                log.info("参数 " + param+" : "+request.getParameter(param));
            }


            log.info("getAnnotations:"+JSON.toJSONString(currentMethod.getClass().getAnnotation(ApiOperation.class)));
            /*log.info("getAnnotations:"+JSON.toJSONString(currentMethod.getClass().getAnnotations()));
            log.info("getAnnotatedInterfaces:"+JSON.toJSONString(currentMethod.getClass().getAnnotatedInterfaces()));
            log.info("getDeclaredFields:"+JSON.toJSONString(currentMethod.getClass().getDeclaredFields()));
            log.info("getFields:"+JSON.toJSONString(currentMethod.getClass().getFields()));
            log.info("getTypeName:"+JSON.toJSONString(currentMethod.getClass().getTypeName()));
            log.info("getTypeParameters:"+JSON.toJSONString(currentMethod.getClass().getTypeParameters()));*/

            /*Object[] args = joinPoint.getArgs();
            if(args != null && args.length > 0){
                for(Object obj : args){
                    //RequestParam requestParam = obj.getClass().getAnnotation(RequestParam.class);
                    log.info("注解getAnnotations："+JSON.toJSONString(obj.getClass().getAnnotations()));
                    log.info("注解getDeclaredAnnotations："+JSON.toJSONString(obj.getClass().getDeclaredAnnotations()));
                }
            }*/

        } catch (Throwable e) {
            log.error("LogAspect发生异常:", e);
        }
        Object object = joinPoint.proceed();
        log.info("返回: {}", object==null?"空":JSONObject.toJSONString(object));
        long endTime = System.currentTimeMillis();
        log.info("响应时间 {} 毫秒", endTime-startTime);
        return object;
    }
}
