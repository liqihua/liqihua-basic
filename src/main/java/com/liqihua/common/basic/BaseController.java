package com.liqihua.common.basic;

import com.liqihua.common.constant.ApiConstant;

public abstract class BaseController {
	

	protected WebResult buildSuccessInfo(Object resultData) {
		return new WebResult(ApiConstant.BASE_SUCCESS_CODE, ApiConstant.getMessage(ApiConstant.BASE_SUCCESS_CODE), resultData);
	}

    protected WebResult buildFailedInfo(int errorCode) {
        return new WebResult(errorCode, ApiConstant.getMessage(errorCode), null);
	}

    protected WebResult buildFailedInfo(int errorCode, String appendMsg) {
        return new WebResult(errorCode, ApiConstant.getMessage(errorCode)+appendMsg, null);
	}

    protected WebResult buildFailedInfo(String errorMsg) {
        return new WebResult(ApiConstant.BASE_FAIL_CODE, errorMsg, null);
	}
	
}
