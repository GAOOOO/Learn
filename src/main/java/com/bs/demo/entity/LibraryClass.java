package com.bs.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author gf
 * @since 2022-03-09
 */
@TableName("library_class")
@ApiModel(value = "LibraryClass对象", description = "")
public class LibraryClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("题库分类编号")
      @TableId(value = "class_id", type = IdType.AUTO)
    private Integer classId;

    @ApiModelProperty("题库分类信息")
    private String classInfo;


    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    @Override
    public String toString() {
        return "LibraryClass{" +
        "classId=" + classId +
        ", classInfo=" + classInfo +
        "}";
    }
}
