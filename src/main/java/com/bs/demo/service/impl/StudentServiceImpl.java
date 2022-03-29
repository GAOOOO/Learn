package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;

import com.bs.demo.service.IStudentService;
import com.bs.demo.entity.Student;
import com.bs.demo.mapper.StudentMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author gf
 * @since 2021-12-28
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public Result addStudent(Student student) {
        return Result.success().message("添加成功！");
    }

    @Override
    public Result updateStudent(Student student) {
        return Result.success().message("修改成功");
    }

    @Override
    public Result deleteStudent(List<Student> studentList) {
        List<Integer> ids = studentList.stream().map(Student::getClassId).collect(Collectors.toList());
        studentMapper.deleteBatchIds(ids);
        return Result.success().message("删除成功！");
    }
}