package com.bs.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.demo.entity.Logs;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gf
 * @since 2021-12-30
 */

@Mapper
public interface LogsMapper extends BaseMapper<Logs> {
}
