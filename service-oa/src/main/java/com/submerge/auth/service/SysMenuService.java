package com.submerge.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.submerge.model.system.SysMenu;
import com.submerge.vo.system.AssginMenuVo;

import java.util.List;


/**
 * ClassName: SysRoleService
 * Package: com.submerge.auth.service
 * Description: 用户service
 *
 * @Author Submerge--WangDong
 * @Create 2024-04-01 18:48
 * @Version 1.0
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 菜单列表
     * @return
     */
    List<SysMenu> findNodes();

    /**
     * 根据id删除菜单
     * @param id
     * @return
     */
    boolean removeMenuById(Long id);

    /**
     * 查询所有菜单和角色已经分配的菜单
     *
     * @param roleId
     * @return
     */
    List<SysMenu> findMenuByRoleId(Long roleId);

    /**
     * 角色分配菜单
     * @param assginMenuVo
     * @return
     */
   void doAssign(AssginMenuVo assginMenuVo);
}
