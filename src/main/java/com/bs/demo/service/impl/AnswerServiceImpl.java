package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;

import com.bs.demo.service.IAnswerService;
import com.bs.demo.entity.Answer;
import com.bs.demo.mapper.AnswerMapper;

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
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

    @Autowired
    private AnswerMapper answerMapper;


    @Override
    public Result addAnswer(Answer answer) {
        return Result.success().message("添加成功！");
    }

    @Override
    public Result updateAnswer(Answer answer) {
        return Result.success().message("修改成功");
    }

    @Override
    public Result deleteAnswer(List<Answer> answerList) {
        List<Integer> ids = answerList.stream().map(Answer::getAnswerId).collect(Collectors.toList());
        answerMapper.deleteBatchIds(ids);
        return Result.success().message("删除成功！");
    }
}