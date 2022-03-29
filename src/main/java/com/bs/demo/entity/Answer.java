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
@ApiModel(value = "Answer对象", description = "")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("答题记录编号")
      @TableId(value = "answer_id", type = IdType.AUTO)
    private Integer answerId;

    @ApiModelProperty("用户编号")
    private String userId;

    @ApiModelProperty("题库编号")
    private String libraryId;

    @ApiModelProperty("最终得分")
    private Integer answerNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
