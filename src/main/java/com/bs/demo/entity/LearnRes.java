package com.bs.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author gf
 * @since 2022-03-09
 */
@Data
@TableName("learn_res")
@ApiModel(value = "LearnRes对象", description = "")
public class LearnRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("资源编号")
    @TableId(value = "res_id", type = IdType.AUTO)
    private Integer resId;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("资源地址")
    private String resAddress;

    @ApiModelProperty("资源描述")
    private String resTitle;

    @ApiModelProperty("下载次数")
    private Integer resDownload;

}
