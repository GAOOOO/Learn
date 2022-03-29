package com.bs.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@ApiModel(value = "Posts对象", description = "")
public class Posts implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("帖子编号")
      @TableId(value = "posts_id", type = IdType.AUTO)
    private Integer postsId;

    @ApiModelProperty("用户编号")
    private String userId;

    @ApiModelProperty("帖子标题")
    private String postsTitle;

    @ApiModelProperty("帖子内容")
    private String postsInfo;

    @ApiModelProperty("发帖时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
