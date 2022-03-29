package com.bs.demo.service;

import com.bs.demo.common.Result;
import com.bs.demo.entity.LearnRes;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * @author gf
 * @since 2021-12-28
 */
public interface ILearnResService extends IService<LearnRes> {


    Result addLearnRes(LearnRes learnRes);

    Result updateLearnRes(LearnRes learnRes);

    Result deleteLearnRes(List<LearnRes> learnResList);
}
