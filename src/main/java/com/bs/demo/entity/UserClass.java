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
@TableName("user_class")
@ApiModel(value = "UserClass对象", description = "")
public class UserClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("班级编号")
      @TableId(value = "class_id", type = IdType.AUTO)
    private Integer classId;

    @ApiModelProperty("班级信息")
    private String classInfo;

    @ApiModelProperty("教师编号")
    private String userId;


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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserClass{" +
        "classId=" + classId +
        ", classInfo=" + classInfo +
        ", userId=" + userId +
        "}";
    }
}
