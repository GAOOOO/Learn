package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;

import com.bs.demo.service.ILearnResService;
import com.bs.demo.entity.LearnRes;
import com.bs.demo.mapper.LearnResMapper;

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
public class LearnResServiceImpl extends ServiceImpl<LearnResMapper, LearnRes> implements ILearnResService {

    @Autowired
    private LearnResMapper learnResMapper;


    @Override
    public Result addLearnRes(LearnRes learnRes) {
        return Result.success().message("添加成功！");
    }

    @Override
    public Result updateLearnRes(LearnRes learnRes) {
        return Result.success().message("修改成功");
    }

    @Override
    public Result deleteLearnRes(List<LearnRes> learnResList) {
        List<Integer> ids = learnResList.stream().map(LearnRes::getResId).collect(Collectors.toList());
        learnResMapper.deleteBatchIds(ids);
        return Result.success().message("删除成功！");
    }
}