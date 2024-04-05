package com.submerge.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.submerge.auth.mapper.SysRoleMapper;
import com.submerge.auth.service.SysRoleService;
import com.submerge.model.system.SysRole;
import org.springframework.stereotype.Service;

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
}
