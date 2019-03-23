/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.tutuniao.tutuniao.util.response;

import com.tutuniao.tutuniao.common.enums.ErrorEnum;
import com.tutuniao.tutuniao.common.exception.CommonException;
import org.apache.commons.lang3.StringUtils;

public class ResponseUtil {
    public static <T> Response<T> buildResponse(T data) {
        return new Response(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> Response<T> buildSuccessResponse() {
        return new Response(ResponseCode.SUCCESS.getCode());
    }

    public static <T> Response<T> buildSuccessResponse(boolean flag, T data) {
        return new Response(ResponseCode.SUCCESS.getCode(), flag, data);
    }

    public static <T> Response<T> buildErrorResponse(String code, String msg) {
        return new Response(code, msg);
    }

    public static <T> Response<T> buildErrorResponse(ErrorEnum errorEnum) {
        return new Response(errorEnum.getCode(), errorEnum.getName());
    }

    public static <T> Response<T> buildErrorResponse(ResponseCode responseCode, String msg) {
        return new Response(responseCode.getCode(), msg);
    }

    public static <T> Response<T> buildErrorResponse(ResponseCode responseCode) {
        return new Response(responseCode.getCode(), responseCode.getDescription());
    }

    public static <T> Response<T> buildErrorResponse(CommonException sgException) {
        if ((null == sgException.getCode()) || (StringUtils.isBlank(sgException.getErrorMessage()))) {
            return buildErrorResponse();
        }
        return new Response(sgException.getCode(), sgException.getErrorMessage());
    }

    public static <T> Response<T> buildErrorResponse(Throwable sgException) {
        if ((null == sgException) || (StringUtils.isBlank(sgException.getLocalizedMessage()))) {
            return buildErrorResponse();
        }

        return new Response(ResponseCode.SYSTEM_ERROR.getCode(), sgException.getLocalizedMessage());
    }

    public static <T> Response<T> buildErrorResponse() {
        return new Response(ResponseCode.SYSTEM_ERROR.getCode(), ResponseCode.SYSTEM_ERROR.getDescription());
    }


}
