package com.bs.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.demo.dto.MenuIndexDto;
import com.bs.demo.entity.Menu;
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
public interface MenuMapper extends BaseMapper<Menu> {

    List<MenuIndexDto> listByUserId(Integer userId);

    List<MenuIndexDto> listByUserIdAndType(Integer userId, Integer type);

    List<Menu> lazyMenu(Integer menuId);
}
