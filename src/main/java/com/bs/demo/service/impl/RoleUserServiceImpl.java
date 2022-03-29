package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.entity.Role;
import com.bs.demo.entity.RoleUser;
import com.bs.demo.entity.vo.RoleUserVo;
import com.bs.demo.mapper.RoleUserMapper;
import com.bs.demo.service.IRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gf
 * @since 2021-12-30
 */
@Service
public class RoleUserServiceImpl extends ServiceImpl<RoleUserMapper, RoleUser> implements IRoleUserService {

    @Autowired
    private RoleUserMapper roleUserMapper;


    @Override
    public List<RoleUser> getRoleUserByUserId(Integer userId) {
        return roleUserMapper.getRoleUserByUserId(userId);
    }

    @Override
    public Role getUserRole(Integer userId) {
        return roleUserMapper.getRoleByUserId(userId);
    }

}
