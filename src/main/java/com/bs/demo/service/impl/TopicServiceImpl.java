package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.common.ResultCode;

import com.bs.demo.service.ITopicService;
import com.bs.demo.entity.Topic;
import com.bs.demo.mapper.TopicMapper;

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
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements ITopicService {

    @Autowired
    private TopicMapper topicMapper;


    @Override
    public Result addTopic(Topic topic) {
        topicMapper.insert(topic);
        return Result.success().message("添加成功！");
    }

    @Override
    public Result updateTopic(Topic topic) {
        topicMapper.updateById(topic);
        return Result.success().message("修改成功");
    }

    @Override
    public Result deleteTopic(List<Topic> topicList) {
        List<Integer> ids = topicList.stream().map(Topic::getTopicId).collect(Collectors.toList());
        topicMapper.deleteBatchIds(ids);
        return Result.success().message("删除成功！");
    }
}