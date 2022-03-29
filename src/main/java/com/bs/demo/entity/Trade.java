package com.bs.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 交易详情，按需应该存入数据库，这里存入数据库，仅供临时测试
 * @author Zheng Jie
 * @date 2018-12-31
 */
@Data
public class Trade {

    /** （必填）支付宝交易号 */
    @ApiModelProperty(hidden = true)
    private String tradeNo;

    /** （必填）商户订单号 */
    @ApiModelProperty(hidden = true)
    private String outTradeNo;

    /** （必填）用户ID */
    @ApiModelProperty(hidden = true)
    private Integer userId;

    /** （必填）商品名称 */
    @NotBlank
    private String subject;

    /** （必填）商品描述 */
    @NotBlank
    private String body;


    /** （必填）价格 */
    @NotBlank
    private String totalAmount;

    /** 订单状态,已支付，未支付，作废 */
    @ApiModelProperty(hidden = true)
    private String state;

    /** 创建时间，存入数据库时需要 */
    @ApiModelProperty(hidden = true)
    private LocalDateTime createTime;

    /** 作废时间，存入数据库时需要 */
    @ApiModelProperty(hidden = true)
    private LocalDateTime cancelTime;
}
