package com.submerge.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.submerge.auth.mapper.SysUserMapper;
import com.submerge.auth.service.SysUserService;
import com.submerge.model.system.SysUser;
import org.springframework.stereotype.Service;

/**
 * ClassName: SysUserServiceImpl
 * Package: com.submerge.auth.service.impl
 * Description:
 *
 * @Author Submerge--WangDong
 * @Create 2024-04-07 10:27
 * @Version 1.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
