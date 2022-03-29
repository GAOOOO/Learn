package com.bs.demo.service;

import com.bs.demo.common.Result;
import com.bs.demo.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author gf
 * @since 2021-12-28
 */
public interface ITeacherService extends IService<Teacher> {

    Result addTeacher(Teacher teacher);

    Result updateTeacher(Teacher teacher);

    Result deleteTeacher(List<Teacher> teacherList);
}
