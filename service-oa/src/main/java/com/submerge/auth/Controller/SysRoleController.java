package com.submerge.auth.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.submerge.auth.service.SysRoleService;
import com.submerge.common.result.Result;
import com.submerge.model.system.SysRole;
import com.submerge.vo.system.SysRoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: SysRoleController
 * Package: com.submerge.auth.Controller
 * Description: 角色管理控制器
 *
 * @Author Submerge--WangDong
 * @Create 2024-04-01 18:52
 * @Version 1.0
 */
@RestController
@Slf4j
@Api(tags = "角色管理相关接口")
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 获取所有用户
     *
     * @return list
     */
    @GetMapping("/all")
    @ApiOperation("获取所有用户")
    public Result<List<SysRole>> getAll() {
        return Result.ok(sysRoleService.list());
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result getRoleByPage(@PathVariable Long page,
                                @PathVariable Long limit,
                                SysRoleQueryVo sysRoleQueryVo) {
        Page<SysRole> pageParam = new Page<>(page, limit);
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if (!StringUtils.isBlank(roleName)) {
            sysRoleQueryWrapper.like("role_name", roleName);
        }
        IPage<SysRole> resultPage = sysRoleService.page(pageParam, sysRoleQueryWrapper);
        return Result.ok(resultPage);
    }

    /**
     * 添加角色
     */
    @ApiOperation("添加角色")
    @PostMapping("/save")
    public Result addRole(@RequestBody SysRole sysRole) {
        boolean save = sysRoleService.save(sysRole);
        if (save) {
            return Result.ok(save);
        } else {
            return Result.fail();
        }
    }

    /**
     * 根据id进行查询
     */
    @ApiOperation("根据id查询角色")
    @GetMapping("/get/{id}")
    public Result getRoleById(@PathVariable Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    /**
     * 修改角色
     */
    @ApiOperation("修改角色")
    @PostMapping("/update")
    public Result updateRole(@RequestBody SysRole sysRole) {
        boolean result = sysRoleService.updateById(sysRole);
        if (result) {
            return Result.ok(result);

        } else {
            return Result.fail();
        }

    }

    /**
     * 删除角色
     */
    @ApiOperation("根据id删除角色")
    @DeleteMapping("/remove/{id}")
    public Result removeRole(@PathVariable Long id) {
        boolean result = sysRoleService.removeById(id);
        if (result) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除角色")
    @DeleteMapping("/batchRemove")
    public Result removeBatch(@RequestBody List<Long> ids) {
        boolean result = sysRoleService.removeByIds(ids);
        if (result) {
            return Result.ok();

        } else {
            return Result.fail();
        }
    }

}