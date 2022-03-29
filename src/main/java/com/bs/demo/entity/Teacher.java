package com.bs.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2022-03-27
 */
@Data
@ApiModel(value = "Teacher对象", description = "")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("优秀教师名")
    private String teacherName;

    @ApiModelProperty("优秀教师描述")
    private String teacherInfo;

    @ApiModelProperty("优秀教师图片")
    private String teacherImage;

    @ApiModelProperty("优秀教师id")
      @TableId(value = "teacher_id", type = IdType.AUTO)
    private Integer teacherId;


}
