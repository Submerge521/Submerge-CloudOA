package com.submerge.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.submerge.auth.exception.BusinessException;
import com.submerge.auth.mapper.SysRoleMapper;
import com.submerge.auth.mapper.SysUserRoleMapper;
import com.submerge.auth.service.SysRoleService;
import com.submerge.auth.service.SysUserRoleService;
import com.submerge.common.result.ErrorCode;
import com.submerge.model.system.SysRole;
import com.submerge.model.system.SysUserRole;
import com.submerge.vo.system.AssginRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.management.ObjectName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: SysRoleServiceImpl
 * Package: com.submerge.auth.service.impl
 * Description:
 *
 * @Author Submerge--WangDong
 * @Create 2024-04-01 18:49
 * @Version 1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public Map<String, Object> findRoleDataByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "用户不存在");
        }
        // 查询所有用户，返回集合
        List<SysRole> allRoleList = baseMapper.selectList(null);
        if (CollectionUtils.isEmpty(allRoleList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<SysUserRole> sysRoleQueryWrapper = new QueryWrapper<>();
        sysRoleQueryWrapper.eq("user_id", userId);
        List<SysUserRole> existUserRoleList = sysUserRoleService.list(sysRoleQueryWrapper);
        if (CollectionUtils.isEmpty(existUserRoleList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Long> existRoleIdList = existUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        ArrayList<SysRole> assignRoleList = new ArrayList<>();
        for (SysRole sysRole : allRoleList) {
            if (existRoleIdList.contains(sysRole.getId())) {
                assignRoleList.add(sysRole);
            }
        }
        // 封装 map
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("assignRoleList", assignRoleList);
        resultMap.put("allRoleList", allRoleList);
        return resultMap;
    }

    /**
     * 为用户分配角色
     * @param assginRoleVo
     */
    @Override
    @Transactional
    public void doAssign(AssginRoleVo assginRoleVo) {
        // 把用户角色删除
        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<>();
        sysUserRoleQueryWrapper.eq("user_id",assginRoleVo.getUserId());
        sysUserRoleService.remove(sysUserRoleQueryWrapper);
        // 重新进行分配
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        for(Long roleId:roleIdList){
            if(roleId == null || roleId <=0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
           SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(assginRoleVo.getUserId());
            sysUserRole.setRoleId(roleId);
            sysUserRoleService.save(sysUserRole);
        }
    }
}
