package com.bs.demo.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.demo.dto.LoginUserDto;
import com.bs.demo.dto.MenuIndexDto;
import com.bs.demo.entity.Role;
import com.bs.demo.entity.RoleUser;
import com.bs.demo.entity.User;
import com.bs.demo.mapper.MenuMapper;
import com.bs.demo.mapper.UserMapper;
import com.bs.demo.service.IRoleService;
import com.bs.demo.service.IRoleUserService;
import com.bs.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public LoginUserDto loadUserByUsername(String userName) throws UsernameNotFoundException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User user = userMapper.selectOne(wrapper.eq("user_name",userName));
        if (user == null) {
            throw new BadCredentialsException("用户名不存在");
        }else if (user.getStatus().equals(User.Status.LOCKED)){
            throw new LockedException("用户被锁定,请联系管理员解锁");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<MenuIndexDto> menuDtoList = menuMapper.listByUserId(user.getUserId());
        List<String> collect = menuDtoList.stream().map(MenuIndexDto::getPermission).collect(Collectors.toList());
        for (String authority : collect){
            if (!("").equals(authority) & authority !=null){
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
                authorities.add(grantedAuthority);
            }
        }
        LoginUserDto loginUser = new LoginUserDto(user,authorities);
        loginUser.setRoleList(getRoleInfo(user));
        return loginUser;
    }

    public List<Role> getRoleInfo(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User userByName = iUserService.getOne(wrapper.eq("user_name",user.getUserName()));
        List<RoleUser> roleUserByUserId = iRoleUserService.getRoleUserByUserId(userByName.getUserId());
        List <Role> roleList = new ArrayList<>();
        for (RoleUser roleUser:roleUserByUserId){
            Integer roleId = roleUser.getRoleId();
            Role roleById = iRoleService.getRoleById(roleId);
            roleList.add(roleById);
        }
        return roleList;
    }
}
