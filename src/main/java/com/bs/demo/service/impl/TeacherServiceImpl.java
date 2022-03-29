package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;

import com.bs.demo.service.ITeacherService;
import com.bs.demo.entity.Teacher;
import com.bs.demo.mapper.TeacherMapper;

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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Autowired
    private TeacherMapper teacherMapper;


    @Override
    public Result addTeacher(Teacher teacher) {
        return Result.success().message("添加成功！");
    }

    @Override
    public Result updateTeacher(Teacher teacher) {
        teacherMapper.updateById(teacher);
        return Result.success().message("修改成功");
    }

    @Override
    public Result deleteTeacher(List<Teacher> teacherList) {
        List<Integer> ids = teacherList.stream().map(Teacher::getTeacherId).collect(Collectors.toList());
        teacherMapper.deleteBatchIds(ids);
        return Result.success().message("删除成功！");
    }
}