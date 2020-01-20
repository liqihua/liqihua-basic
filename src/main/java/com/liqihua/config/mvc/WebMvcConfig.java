package com.liqihua.config.mvc;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.liqihua.common.utils.SysFileUtil;
import com.liqihua.config.mvc.interceptor.CorsInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * mvc相关配置
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
	@Resource
	private Environment environment;


	@Bean
	CorsInterceptor corsInterceptor() {
		return new CorsInterceptor();
	}



	/**
	 * 添加拦截器
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//跨域拦截器
		registry.addInterceptor(corsInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

	/**
	 * 设置controller的字段json转换
	 * WebMvcConfigurationSupport方式
	 * @param converters
	 */
	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		/**
		 * 设置支持的类型
		 */
		List<MediaType> list = new ArrayList<MediaType>();
		list.add(MediaType.APPLICATION_JSON_UTF8);
		list.add(MediaType.APPLICATION_JSON);
		list.add(MediaType.APPLICATION_ATOM_XML);
		list.add(MediaType.APPLICATION_FORM_URLENCODED);
		list.add(MediaType.APPLICATION_OCTET_STREAM);
		list.add(MediaType.APPLICATION_PDF);
		list.add(MediaType.APPLICATION_RSS_XML);
		list.add(MediaType.APPLICATION_XHTML_XML);
		list.add(MediaType.APPLICATION_XML);
		list.add(MediaType.IMAGE_GIF);
		list.add(MediaType.IMAGE_JPEG);
		list.add(MediaType.IMAGE_PNG);
		list.add(MediaType.TEXT_EVENT_STREAM);
		list.add(MediaType.TEXT_HTML);
		list.add(MediaType.TEXT_MARKDOWN);
		list.add(MediaType.TEXT_PLAIN);
		list.add(MediaType.TEXT_XML);


		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		/**
		 * 对Date的处理 jdk7
		 */
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(smt);
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+08"));//设置时区

		/**
		 * 对LocalDateTime的处理 jdk8
		 */
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		javaTimeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		javaTimeModule.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		javaTimeModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		javaTimeModule.addSerializer(LocalTime.class,new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
		javaTimeModule.addDeserializer(LocalTime.class,new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));


		objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);


		/**
		 * 设置对象转化器和支持的请求类型
		 */
		converter.setObjectMapper(objectMapper);
		converter.setSupportedMediaTypes(list);
		converters.add(converter);

		super.configureMessageConverters(converters);
	}





	/**
	 * 静态路径映射
	 * 继承WebMvcConfigurationSupport时需要配置
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		String dirs = environment.getProperty("mvc.static.dirs");
		if(StrUtil.isNotBlank(dirs)){
			String[] arr = dirs.split(",");
			if(arr != null && arr.length > 0){
				String projectPath = SysFileUtil.getProjectPath();
				for(String dir : arr){
					if(StrUtil.isNotBlank(dir)){
						registry.addResourceHandler( dir + "/**").addResourceLocations("file:" + projectPath + dir +"/");
					}
				}
			}
		}
	}

}
