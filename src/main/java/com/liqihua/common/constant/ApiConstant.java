package com.liqihua.common.constant;


import java.util.Map;
import java.util.TreeMap;

/**
 * API统一状态码
 */
public class ApiConstant {

	/**
	 * 存放每个状态码对应的具体信息
	 */
	public static final Map<Integer,String> map = new TreeMap<Integer,String>();

	/**
	 * 成功
	 */
	public final static int BASE_SUCCESS_CODE = 10000;
	/**
	 * 失败
	 */
	public final static int BASE_FAIL_CODE = -1;

	/**
	 * 该请求是文件类型的HTTP请求
	 */
	public final static int NOT_A_FILE_REQUEST = -3;
	/**
	 * 请求过于频繁
	 */
	public final static int REQUEST_BUSY = -4;


	/**
	 * 未认证
	 */
	public final static int NO_AUTHENCATION = 41201;
	/**
	 * 权限不足
	 */
	public final static int NO_AUTHORIZATION = 41202;


	/**
	 * 参数不能为空
	 */
	public final static int PARAM_IS_NULL = 41001;
	/**
	 * 参数有误
	 */
	public final static int PARAM_ERROR = 41002;
	/**
	 * 参数类型有误
	 */
	public final static int PARAM_TYPE_ERROR = 41003;
	/**
	 * 手机号码参数有误
	 */
	public final static int PARAM_PHONE_ERROR = 41004;
	/**
	 * 日期参数有误
	 */
	public final static int PARAM_DATE_ERROR = 41005;
	/**
	 * 数字参数有误
	 */
	public final static int PARAM_NUMBER_ERROR = 41006;
	/**
	 * 小数参数有误
	 */
	public final static int PARAM_DOUBLE_ERROR = 41007;
	/**
	 * json参数格式或类型有误
	 */
	public final static int PARAM_JSON_ERROR = 41008;
	/**
	 * 参数格式有误
	 */
	public final static int PARAM_FORMAT_ERROR = 41009;






	/**
	 * 用户不存在
	 */
	public final static int USER_NOT_EXIST = 43001;
	/**
	 * 用户已存在
	 */
	public final static int USER_EXIST = 43002;
	/**
	 * 用户已被停用
	 */
	public final static int USER_IS_LOCKED = 43003;
	/**
	 * 未登录
	 */
	public final static int NO_LOGIN = 42003;
	/**
	 * 密码错误
	 */
	public final static int PASSWORD_ERROR = 42004;
	/**
	 * 记录不存在
	 */
	public final static int LOG_NOT_EXIST = 42005;


	
	
	static{
		map.put(BASE_SUCCESS_CODE, "success");
		map.put(BASE_FAIL_CODE, "fail");
		map.put(NO_AUTHENCATION, "未认证");
		map.put(NO_AUTHORIZATION, "权限不足");
		map.put(NOT_A_FILE_REQUEST, "该请求是文件类型的HTTP请求");
		map.put(REQUEST_BUSY, "请求过于频繁");

		map.put(PARAM_ERROR, "参数有误");
		map.put(PARAM_IS_NULL, "参数不能为空");
		map.put(PARAM_TYPE_ERROR, "参数类型有误");
		map.put(PARAM_PHONE_ERROR, "手机号码参数有误");
		map.put(PARAM_DATE_ERROR, "日期参数有误");
		map.put(PARAM_NUMBER_ERROR, "数字参数有误");
		map.put(PARAM_DOUBLE_ERROR, "小数参数有误");
		map.put(PARAM_JSON_ERROR, "json参数格式或类型有误");
		map.put(PARAM_FORMAT_ERROR, "参数格式有误");

		map.put(USER_NOT_EXIST, "用户不存在");
		map.put(USER_EXIST, "用户已存在");
		map.put(USER_IS_LOCKED, "用户已被停用");
		map.put(NO_LOGIN, "未登录");
		map.put(PASSWORD_ERROR, "密码错误");
		map.put(LOG_NOT_EXIST, "记录不存在");
	}



	public static String getMessage(int errorCode){
		return map.get(errorCode);
	}
	
	
}
