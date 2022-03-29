package com.bs.demo.service;

import com.bs.demo.common.Result;
import com.bs.demo.entity.Topic;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author gf
 * @since 2021-12-28
 */
public interface ITopicService extends IService<Topic> {

    Result addTopic(Topic topic);

    Result updateTopic(Topic topic);

    Result deleteTopic(List<Topic> topicList);
}
