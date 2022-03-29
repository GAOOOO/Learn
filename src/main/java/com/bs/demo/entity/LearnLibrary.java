package com.bs.demo.entity;

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
 * @since 2022-03-10
 */
@Data
@TableName("learn_library")
@ApiModel(value = "LearnLibrary对象", description = "")
public class LearnLibrary implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("题库编号")
    private Integer libraryId;

    @ApiModelProperty("教师编号")
    private String userId;

    @ApiModelProperty("题库信息")
    private String libraryInfo;

    @ApiModelProperty("是否私有")
    private Integer libraryCode;

    @ApiModelProperty("题库分类")
    private String libraryClass;

    @ApiModelProperty("题库图片")
    private String libraryImg;

}
