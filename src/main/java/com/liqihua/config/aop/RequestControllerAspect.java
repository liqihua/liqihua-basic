package com.liqihua.config.aop;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.liqihua.common.basic.WebResult;
import com.liqihua.common.constant.ApiConstant;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;

/**
 * 监控controller的请求
 * 对@RequestParam 为 true 的参数进行值校验
 * @author liqihua
 * @since 2018/11/19
 */
@Aspect
@Component
public class RequestControllerAspect {

    private static final Logger log = LoggerFactory.getLogger(RequestControllerAspect.class);

    /**
     * controller层aop处理
     * @param joinPoint
     * @return
     * @throws Throwable
     */
	@Around("execution (* com..*.*Controller.*(..))")
    public Object controllerAround(ProceedingJoinPoint joinPoint) throws Throwable{
        /**
         * 请求日志打印
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
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Class<?>[] paramTypes = methodSignature.getParameterTypes();
            String methodName = joinPoint.getSignature().getName();
            //获取当前执行方法
            Method currentMethod = target.getMethod(methodName, paramTypes);
            //拼接输出字符串
            classAndMethodName = target.getName() + " 的 " + currentMethod.getName() + " 方法";
            log.info("正在执行：{}", classAndMethodName);
            //打印参数
            Enumeration<String> enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()){
                String param = enumeration.nextElement();
                log.info("参数 " + param+" : "+request.getParameter(param));
            }

            /**
             * RequestParam注解参数非空参数拦截
             */
            Parameter[] params = currentMethod.getParameters();
            if(params != null && params.length > 0){
                Object[] args = joinPoint.getArgs();
                for(int i=0; i< params.length; i++){
                    Annotation[] annos = params[i].getDeclaredAnnotations();
                    if(annos != null && annos.length > 0){
                        for(Annotation anno : annos){
                            if(anno instanceof RequestParam){
                                RequestParam annoObj = (RequestParam)anno;
                                if(annoObj.required()){
                                    if(args[i] == null || (args[i] instanceof String && StrUtil.isBlank(args[i]+""))){
                                        String paramName = methodSignature.getParameterNames()[i];
                                        log.info(paramName+":"+args[i]);
                                        log.info("参数 "+paramName+" 要求非空，参数值："+(args[i]==null?"":args[i].toString()));
                                        WebResult webResult = new WebResult(ApiConstant.PARAM_IS_NULL, ApiConstant.getMessage(ApiConstant.PARAM_IS_NULL)+"->"+paramName);
                                        return webResult;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            log.error("controllerAround 发生异常:", e);
        }
        Object object = joinPoint.proceed();
        log.info("返回: {}", object==null?"空":JSONObject.toJSONString(object));
        long endTime = System.currentTimeMillis();
        log.info("响应时间 {} 毫秒", endTime-startTime);
        return object;
    }



}
