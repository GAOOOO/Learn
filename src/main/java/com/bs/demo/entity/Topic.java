package com.bs.demo.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "Topic对象", description = "")
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("赛题编号uuid")
    @TableId(value = "topic_id", type = IdType.AUTO)
    private Integer topicId;

    @ApiModelProperty("题库编号")
    private String libraryId;

    @ApiModelProperty("赛题标题")
    private String topicTitle;

    @ApiModelProperty("赛题答案")
    private String topicAnswer;

    @ApiModelProperty("用户选项")
    private String topicInfo;

    @ApiModelProperty("选项信息")
    private String topicOption;
}
