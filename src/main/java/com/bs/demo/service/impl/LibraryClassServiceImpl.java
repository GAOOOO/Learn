package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;

import com.bs.demo.service.ILibraryClassService;
import com.bs.demo.entity.LibraryClass;
import com.bs.demo.mapper.LibraryClassMapper;

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
public class LibraryClassServiceImpl extends ServiceImpl<LibraryClassMapper, LibraryClass> implements ILibraryClassService {

    @Autowired
    private LibraryClassMapper libraryClassMapper;


    @Override
    public Result addLibraryClass(LibraryClass libraryClass) {
        libraryClassMapper.insert(libraryClass);
        return Result.success().message("添加成功！");
    }

    @Override
    public Result updateLibraryClass(LibraryClass libraryClass) {
        libraryClassMapper.updateById(libraryClass);
        return Result.success().message("修改成功");
    }

    @Override
    public Result deleteLibraryClass(List<LibraryClass> libraryClassList) {
        List<Integer> ids = libraryClassList.stream().map(LibraryClass::getClassId).collect(Collectors.toList());
        libraryClassMapper.deleteBatchIds(ids);
        return Result.success().message("删除成功！");
    }
}