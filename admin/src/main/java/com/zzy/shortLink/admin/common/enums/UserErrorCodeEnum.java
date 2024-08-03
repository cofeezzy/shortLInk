package com.zzy.shortLink.admin.common.enums;

import com.zzy.shortLink.admin.common.convention.errorcode.IErrorCode;

/**
 * 基础错误码定义
 */
public enum UserErrorCodeEnum implements IErrorCode {

    USER_NULL("B002000", "用户不存在"),

    USER_NAME_EXIST("B002001","用户名已存在"),

    USER_EXIST("B002002","用户记录已存在"),

    USER_SAVE_ERROR("B002003", "用户保存失败");



    private final String code;

    private final String message;

    UserErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
