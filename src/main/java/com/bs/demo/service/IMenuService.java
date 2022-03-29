package com.bs.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.demo.common.Result;
import com.bs.demo.dto.MenuDto;
import com.bs.demo.dto.MenuIndexDto;
import com.bs.demo.entity.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gf
 * @since 2021-12-30
 */
public interface IMenuService extends IService<Menu> {

    List<MenuIndexDto> getMenu(Integer userId);

    List<MenuIndexDto> getButton(Integer userId, Integer type);

    List<Menu> lazyMenu(Integer menuId);

    Result addMenu(Menu menu);

    Result updateRole(Menu menu);

    Result deleteMenu(List<Menu> menuList);
}
