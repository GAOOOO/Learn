package com.bs.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author GHD
 * @createTime 2020/5/8
 */
@Data
public class MyException extends  RuntimeException {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String  msg;

    public MyException(String msg){
        super(msg);
    }

    public MyException(Integer code, String msg){
        super(msg);
        this.code = code;
    }
}
