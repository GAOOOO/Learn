package com.bs.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.bs.demo.entity.UserClass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gf
 * @since 2021-12-30
 */
@Mapper
public interface UserClassMapper extends BaseMapper<UserClass> {
    
}
