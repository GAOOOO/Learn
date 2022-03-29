package com.bs.demo.service;

import com.bs.demo.common.Result;
import com.bs.demo.entity.Answer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author gf
 * @since 2021-12-28
 */
public interface IAnswerService extends IService<Answer> {

    Result addAnswer(Answer answer);

    Result updateAnswer(Answer answer);

    Result deleteAnswer(List<Answer> answerList);
}
