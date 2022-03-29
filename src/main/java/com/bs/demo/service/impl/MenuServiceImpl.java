package com.bs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.demo.common.Result;
import com.bs.demo.dto.MenuDto;
import com.bs.demo.dto.MenuIndexDto;
import com.bs.demo.entity.Menu;
import com.bs.demo.entity.Role;
import com.bs.demo.entity.RoleMenu;
import com.bs.demo.mapper.MenuMapper;
import com.bs.demo.mapper.RoleMenuMapper;
import com.bs.demo.service.IMenuService;
import com.bs.demo.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gf
 * @since 2021-12-30
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<Menu> lazyMenu(Integer menuId) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", menuId).gt("menu_id", 1);
        List<Menu> list = menuMapper.lazyMenu(menuId);
        return list;
    }

    @Override
    public Result addMenu(Menu menu) {
        Menu newMenu = new Menu();
        newMenu.setMenuName(menu.getMenuName());
        newMenu.setIcon(menu.getIcon());
        newMenu.setPermission(menu.getPermission());
        newMenu.setType(menu.getType());
        newMenu.setSort(menu.getSort());
        newMenu.setParentId(menu.getParentId());
        newMenu.setCreateTime(LocalDateTime.now());
        newMenu.setUrl(menu.getUrl());
        if (menu.getParentId() != 0) {
            Menu parentCheck = menuMapper.selectById(menu.getParentId());
            if (parentCheck == null) {
                return Result.error().message("您输入的父菜单有误！");
            } else {
                if (menuMapper.insert(newMenu) == 1) {
                    return Result.success().message("添加成功！").data(newMenu.getMenuName());
                } else {
                    return Result.success().message("添加失败！").data(newMenu.getMenuName());
                }
            }
        } else {
            if (menuMapper.insert(newMenu) == 1) {
                return Result.success().message("添加成功！").data(newMenu.getMenuName());
            } else {
                return Result.success().message("添加失败！").data(newMenu.getMenuName());
            }
        }
    }

    @Override
    public Result updateRole(Menu menu) {
        Menu orMenu = menuMapper.selectById(menu);
        if (orMenu == null) {
            return Result.error().message("修改菜单错误：【不存在该菜单】").data(menu);
        }else {
            int result = menuMapper.updateById(menu);
            if (result == 1) {
                return Result.success().message("修改菜单成功").data(menu.getMenuName());
            } else {
                return Result.error().message("修改菜单信息失败");
            }
        }
    }

    @Override
    public Result deleteMenu(List<Menu> menuList) {
        List<Integer> ids = menuList.stream().map(Menu::getMenuId).collect(Collectors.toList());
        menuMapper.deleteBatchIds(ids);
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("menu_id", ids);
        roleMenuMapper.delete(queryWrapper);
        return Result.success().message("删除成功！");

    }

    @Override
    public List<MenuIndexDto> getMenu(Integer userId) {
        List<MenuIndexDto> list = menuMapper.listByUserId(userId);
        List<MenuIndexDto> result = TreeUtil.parseMenuTree(list);
        return result;
    }

    @Override
    public List<MenuIndexDto> getButton(Integer userId, Integer type) {
        List<MenuIndexDto> result = menuMapper.listByUserIdAndType(userId, type);
        return result;
    }


}
