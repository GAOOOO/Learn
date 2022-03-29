package com.bs.demo.mapper;

import com.bs.demo.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gf
 * @since 2021-12-28
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    Role getRoleById(Integer roleId);
}
