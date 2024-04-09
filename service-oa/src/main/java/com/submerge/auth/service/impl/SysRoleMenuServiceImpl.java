package com.submerge.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.submerge.auth.exception.BusinessException;
import com.submerge.auth.mapper.SysMenuMapper;
import com.submerge.auth.mapper.SysRoleMenuMapper;
import com.submerge.auth.service.SysMenuService;
import com.submerge.auth.service.SysRoleMenuService;
import com.submerge.auth.utils.MenuHelper;
import com.submerge.common.result.ErrorCode;
import com.submerge.model.system.SysMenu;
import com.submerge.model.system.SysRoleMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {


}
