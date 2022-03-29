package com.bs.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author GHD
 */

@Data
@Table(name = "log")
@ApiModel(value = "Logs", description = "")
@NoArgsConstructor
public class Logs implements Serializable {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id值")
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    /**
     * 操作用户
     */
    @ApiModelProperty("用户名")
    private String userName;

    /**
     * 描述
     */
    private String description;

    /**
     * 方法名
     */
    private String method;

    /**
     * 参数
     */
    private String params;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 地址
     */
    private String address;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 请求耗时
     */
    private Long time;

    /**
     * 异常详细
     */
    private byte[] exceptionDetail;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public Logs(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }


    @Override
    public String toString() {
        return "Logs{" +
                "logId=" + logId +
                ", userName='" + userName + '\'' +
                ", description='" + description + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", logType='" + logType + '\'' +
                ", requestIp='" + requestIp + '\'' +
                ", address='" + address + '\'' +
                ", browser='" + browser + '\'' +
                ", time=" + time +
                ", exceptionDetail=" + Arrays.toString(exceptionDetail) +
                ", createTime=" + createTime +
                '}';
    }
}


