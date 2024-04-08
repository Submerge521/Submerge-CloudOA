package com.submerge.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.submerge.model.system.SysRole;
import com.submerge.vo.system.AssginRoleVo;

import java.util.Map;

/**
 * ClassName: SysRoleService
 * Package: com.submerge.auth.service
 * Description:
 *
 * @Author Submerge--WangDong
 * @Create 2024-04-01 18:48
 * @Version 1.0
 */
public interface SysRoleService extends IService<SysRole> {
   /**
     * 查询所有角色和当前用户所属角色
     * @param userId
     * @return
     */
    Map<String, Object> findRoleDataByUserId(Long userId);

    /**
     * 为用户分配角色
     * @param assginRoleVo
     */
    void doAssign(AssginRoleVo assginRoleVo);
}
