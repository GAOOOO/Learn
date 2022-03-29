package com.bs.demo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果的类
 *
 * @author gf
 * @createTime 2020/5/15
 */
@Data
@ApiModel(value = "返回结果", description = "")
public class Result<T> implements Serializable {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;


    /**
     * 把构造方法私有
     */
    private Result() {
    }


    /**
     * 成功静态方法
     *
     * @return
     */
    public static Result success() {
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMsg("成功");
        return r;
    }


    /**
     * 失败静态方法
     *
     * @return
     */
    public static Result error() {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMsg("失败");
        return r;
    }

    public static Result judge(int n, String msg) {
        return n > 0 ? success().message(msg + "成功") : error().message(msg + "失败");
    }

    public Result success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Result message(String message) {
        this.setMsg(message);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }


    public Result data(T data) {
        this.data=data;
        return this;
    }



}

