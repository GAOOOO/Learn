package com.bs.demo.entity;

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
 * @since 2021-12-30
 */
@Data
@TableName("role_user")
@ApiModel(value = "RoleUser对象", description = "")
public class RoleUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    @TableId
    private Integer userId;

    @ApiModelProperty("规则ID")
    private Integer roleId;


}
