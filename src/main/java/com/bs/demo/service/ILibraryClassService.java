package com.bs.demo.service;

import com.bs.demo.common.Result;
import com.bs.demo.entity.LibraryClass;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author gf
 * @since 2021-12-28
 */
public interface ILibraryClassService extends IService<LibraryClass> {

    Result addLibraryClass(LibraryClass libraryClass);

    Result updateLibraryClass(LibraryClass libraryClass);

    Result deleteLibraryClass(List<LibraryClass> libraryClassList);
}
