package com.qianwang.enums;

public enum HttpCodeEnum {


    // 成功段0
    SUCCESS(200,"操作成功"),
    // 登录段1~20
    NEED_LOGIN(1,"需要登录后操作"),
    LOGIN_PASSWORD_ERROR(2,"密码错误"),
    //注册段20~30
    REGISTER_USERNAME_NOTNULL(21,"用户名不能为空"),
    REGISTER_USERNAME_EXIST(22,"用户名已存在"),
    REGISTER_PASSWORD_NOTNULL(23,"密码不能为空"),
    //手机和验证码校验30~40,时间格式
    PHONE_FORMAT_ERROR(30,"手机号码格式错误"),
    CODE_ERROR(31,"验证码错误"),
    APPOINTMENT_TIME_NOT_NULL(32,"预约时间不能为空"),
    APPOINTMENT_TIME_INVALID(33,"预约时间不能早于当前时间"),
    APPOINTMENT_TIME_TOO_FAR(34,"预约时间不能超过14天"),
    PHONE_ALREADY_RESERVED(35,"该手机号已预约过"),
    // TOKEN50~100
    TOKEN_INVALID(50,"无效的TOKEN"),
    TOKEN_EXPIRE(51,"TOKEN已过期"),
    TOKEN_REQUIRE(52,"TOKEN是必须的"),
    // SIGN验签 100~120
    SIGN_INVALID(100,"无效的SIGN"),
    SIG_TIMEOUT(101,"SIGN已过期"),
    // 参数错误 500~1000
    PARAM_REQUIRE(500,"缺少参数"),
    PARAM_INVALID(501,"无效参数"),
    PARAM_IMAGE_FORMAT_ERROR(502,"图片格式有误"),
    SERVER_ERROR(503,"服务器内部错误"),
    // 数据错误 1000~2000
    DATA_EXIST(1000,"数据已经存在"),
    AP_USER_DATA_NOT_EXIST(1001,"ApUser数据不存在"),
    DATA_NOT_EXIST(1002,"数据不存在"),
    // 数据错误 3000~3500
    NO_OPERATOR_AUTH(3000,"无权限操作"),
    NEED_ADMIND(3001,"需要管理员权限"),
    LOGIN_OPERATE(3002,"管理员需要进行登录操作");

    int code;
    String errorMessage;

    HttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
