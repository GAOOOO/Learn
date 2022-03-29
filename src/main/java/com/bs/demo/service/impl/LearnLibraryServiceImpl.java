package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;

import com.bs.demo.service.ILearnLibraryService;
import com.bs.demo.entity.LearnLibrary;
import com.bs.demo.mapper.LearnLibraryMapper;

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
public class LearnLibraryServiceImpl extends ServiceImpl<LearnLibraryMapper, LearnLibrary> implements ILearnLibraryService {

    @Autowired
    private LearnLibraryMapper learnLibraryMapper;



    @Override
    public Result addLearnLibrary(LearnLibrary learnLibrary) {
        learnLibraryMapper.insert(learnLibrary);
        return Result.success().message("添加成功！");
    }

    @Override
    public Result updateLearnLibrary(LearnLibrary learnLibrary) {
        learnLibraryMapper.update(learnLibrary,new QueryWrapper<LearnLibrary>()
                .eq("library_id",learnLibrary.getLibraryId()));
        return Result.success().message("修改成功");
    }

    @Override
    public Result deleteLearnLibrary(List<LearnLibrary> learnLibraryList) {
        List<Integer> ids = learnLibraryList.stream().map(LearnLibrary::getLibraryId).collect(Collectors.toList());
        learnLibraryMapper.delete(new QueryWrapper<LearnLibrary>()
                .in("library_id",ids));
        return Result.success().message("删除成功！");
    }
}