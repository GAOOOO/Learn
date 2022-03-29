package com.bs.demo.service;

import com.bs.demo.common.Result;
import com.bs.demo.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author gf
 * @since 2021-12-28
 */
public interface IStudentService extends IService<Student> {

    Result addStudent(Student student);

    Result updateStudent(Student student);

    Result deleteStudent(List<Student> studentList);
}
