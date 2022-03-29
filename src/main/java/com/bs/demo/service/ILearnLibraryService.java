package com.bs.demo.service;

import com.bs.demo.common.Result;
import com.bs.demo.entity.LearnLibrary;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author gf
 * @since 2021-12-28
 */
public interface ILearnLibraryService extends IService<LearnLibrary> {

    Result addLearnLibrary(LearnLibrary learnLibrary);

    Result updateLearnLibrary(LearnLibrary learnLibrary);

    Result deleteLearnLibrary(List<LearnLibrary> learnLibraryList);
}
