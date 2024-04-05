package com.submerge.common.result;

import lombok.Getter;

/**
 * ClassName: ResultCodeEnum
 * Package: com.submerge.common.result
 * Description: 返回结果代码枚举
 *
 * @Author Submerge--WangDong
 * @Create 2024-04-05 10:05
 * @Version 1.0
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限");


    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
