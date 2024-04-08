package com.submerge.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.submerge.auth.exception.BusinessException;
import com.submerge.auth.mapper.SysUserMapper;
import com.submerge.auth.service.SysUserService;
import com.submerge.common.result.BaseResponse;
import com.submerge.common.result.ErrorCode;
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
    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = baseMapper.selectById(id);
        if (sysUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        sysUser.setStatus(status);
        baseMapper.updateById(sysUser);
    }
}
