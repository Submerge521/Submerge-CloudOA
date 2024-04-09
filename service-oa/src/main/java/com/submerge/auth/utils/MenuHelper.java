package com.submerge.auth.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.submerge.auth.exception.BusinessException;
import com.submerge.auth.service.SysMenuService;
import com.submerge.common.result.ErrorCode;
import com.submerge.model.system.SysMenu;
import com.submerge.model.system.SysRole;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * ClassName: MenuHelper
 * Package: com.submerge.auth.utils
 * Description: 构建树形结构
 *
 * @Author Submerge--WangDong
 * @Create 2024-04-09 9:53
 * @Version 1.0
 */
public class MenuHelper {

    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        // 创建list集合，用于最终数据
        ArrayList<SysMenu> trees = new ArrayList<>();
        // 把所有菜单数据进行遍历
        for (SysMenu sysMenu : sysMenuList) {
            if (sysMenu.getParentId() == 0) {
                trees.add(getChildren(sysMenu, sysMenuList));
            }
        }
        return trees;
    }

    public static SysMenu getChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        if (sysMenu == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (CollectionUtils.isEmpty(sysMenuList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 初始化
        sysMenu.setChildren(new ArrayList<>());
        // 遍历所有菜单数据，判断 id 和 parentId 的关系
        for (SysMenu it : sysMenuList) {
            if (Objects.equals(sysMenu.getId(), it.getParentId())) {
                if (sysMenu.getChildren() == null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(getChildren(it, sysMenuList));
            }
        }
        return sysMenu;


    }

}
