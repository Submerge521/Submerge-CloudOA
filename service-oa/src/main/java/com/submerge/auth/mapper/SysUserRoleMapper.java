package com.submerge.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.submerge.model.system.SysRole;
import com.submerge.model.system.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: SysRoleMapper
 * Package: com.submerge.auth.mapper
 * Description:
 *
 * @Author Submerge--WangDong
 * @Create 2024-04-01 18:26
 * @Version 1.0
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
}
