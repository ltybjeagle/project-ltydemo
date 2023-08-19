package com.sunny.maven.middle.authentication.security.mapper;

import com.sunny.maven.middle.authentication.security.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/8/14 10:30
 */
@Mapper
public interface MenuMapper {
    List<Menu> getAllMenus();
}
