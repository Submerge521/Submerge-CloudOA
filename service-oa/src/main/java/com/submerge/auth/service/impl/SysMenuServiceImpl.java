package com.submerge.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.submerge.auth.exception.BusinessException;
import com.submerge.auth.mapper.SysMenuMapper;
import com.submerge.auth.service.SysMenuService;
import com.submerge.auth.service.SysRoleMenuService;
import com.submerge.auth.utils.MenuHelper;
import com.submerge.common.result.ErrorCode;
import com.submerge.model.system.SysMenu;
import com.submerge.model.system.SysRoleMenu;
import com.submerge.vo.system.AssginMenuVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: MenuServiceImpl
 * Package: com.submerge.auth.service.impl
 * Description:
 *
 * @Author Submerge--WangDong
 * @Create 2024-04-09 9:26
 * @Version 1.0
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 菜单列表
     *
     * @return
     */
    @Override
    public List<SysMenu> findNodes() {

        // 查询所有菜单列表
        List<SysMenu> menuList = baseMapper.selectList(null);
        //构建树形数据
        return MenuHelper.buildTree(menuList);


    }

    @Override
    public boolean removeMenuById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断当前菜单是否有下一层菜单
        QueryWrapper<SysMenu> sysMenuQueryWrapper = new QueryWrapper<>();
        sysMenuQueryWrapper.eq("parent_id", id);
        Long count = baseMapper.selectCount(sysMenuQueryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "菜单不能删除");
        }
        baseMapper.deleteById(id);
        return true;
    }

    /**
     * 查询所有菜单和角色已经分配的菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenu> findMenuByRoleId(Long roleId) {
        // 查询所有菜单（status为1的）
        QueryWrapper<SysMenu> sysMenuQueryWrapper = new QueryWrapper<>();
        sysMenuQueryWrapper.eq("status", 1);
        List<SysMenu> allSysMenuList = baseMapper.selectList(sysMenuQueryWrapper);
        // 根据 roleId 查询菜单id
        QueryWrapper<SysRoleMenu> sysRoleMenuQueryWrapper = new QueryWrapper<>();
        sysRoleMenuQueryWrapper.eq("role_id", roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(sysRoleMenuQueryWrapper);
        // 根据获取的菜单id对应到菜单对象
        List<Long> menuIdList = sysRoleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        // 菜单id 和所有菜单列表的id相同，则进行封装
        for (SysMenu sysMenu : allSysMenuList) {
            if (menuIdList.contains(sysMenu.getId())) {
                sysMenu.setSelect(true);
            } else {
                sysMenu.setSelect(false);
            }
        }
        // 返回规定格式的菜单列表
        return MenuHelper.buildTree(allSysMenuList);

    }

    /**
     * 为角色分配菜单
     *
     * @param assginMenuVo
     * @return
     */
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        // 根据角色id删除表里原有菜单
        QueryWrapper<SysRoleMenu> sysRoleMenuQueryWrapper = new QueryWrapper<>();
        sysRoleMenuQueryWrapper.eq("role_id", assginMenuVo.getRoleId());
        sysRoleMenuService.remove(sysRoleMenuQueryWrapper);
        // 遍历vo中的菜单表，添加到数据库
        List<Long> menuIdList = assginMenuVo.getMenuIdList();
        for (Long menuId : menuIdList) {
            if (CollectionUtils.isEmpty(menuIdList)) {
                break;
            }
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenuService.save(sysRoleMenu);
        }
    }
}
