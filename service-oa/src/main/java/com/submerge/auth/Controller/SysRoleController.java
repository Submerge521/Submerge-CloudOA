package com.submerge.auth.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.submerge.auth.exception.BusinessException;
import com.submerge.auth.service.SysRoleService;
import com.submerge.auth.service.SysUserRoleService;
import com.submerge.common.result.BaseResponse;
import com.submerge.common.result.ErrorCode;
import com.submerge.common.result.ResultUtils;
import com.submerge.model.system.SysRole;
import com.submerge.vo.system.AssginRoleVo;
import com.submerge.vo.system.SysRoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public BaseResponse<List<SysRole>> getAll() {
        return ResultUtils.success(sysRoleService.list());
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public BaseResponse<IPage<SysRole>> getRoleByPage(@PathVariable Long page,
                                                      @PathVariable Long limit,
                                                      SysRoleQueryVo sysRoleQueryVo) {
        Page<SysRole> pageParam = new Page<>(page, limit);
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if (!StringUtils.isBlank(roleName)) {
            sysRoleQueryWrapper.like("role_name", roleName);
        }
        IPage<SysRole> resultPage = sysRoleService.page(pageParam, sysRoleQueryWrapper);
        return ResultUtils.success(resultPage);
    }

    /**
     * 添加角色
     */
    @ApiOperation("添加角色")
    @PostMapping("/save")
    public BaseResponse<Boolean> addRole(@RequestBody SysRole sysRole) {
        boolean save = sysRoleService.save(sysRole);
        if (save) {
            return ResultUtils.success(true);
        } else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    /**
     * 根据id进行查询
     */
    @ApiOperation("根据id查询角色")
    @GetMapping("/get/{id}")
    public BaseResponse<SysRole> getRoleById(@PathVariable Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        return ResultUtils.success(sysRole);
    }

    /**
     * 修改角色
     */
    @ApiOperation("修改角色")
    @PostMapping("/update")
    public BaseResponse<Boolean> updateRole(@RequestBody SysRole sysRole) {
        boolean result = sysRoleService.updateById(sysRole);
        if (result) {
            return ResultUtils.success(true);

        } else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

    }

    /**
     * 删除角色
     */
    @ApiOperation("根据id删除角色")
    @DeleteMapping("/remove/{id}")
    public BaseResponse<Boolean> removeRole(@PathVariable Long id) {
        boolean result = sysRoleService.removeById(id);
        if (result) {
            return ResultUtils.success(true);
        } else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除角色")
    @DeleteMapping("/batchRemove")
    public BaseResponse<Boolean> removeBatch(@RequestBody List<Long> ids) {
        boolean result = sysRoleService.removeByIds(ids);
        if (result) {
            return ResultUtils.success(true);

        } else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    /**
     * 查询所有角色和当前用户所属角色
     * @param userId
     * @return
     */
    @GetMapping("/toAssign/{userId}")
    @ApiOperation("获取角色")
    public BaseResponse<Map<String,Object>> toAssign(@PathVariable Long userId) {
        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Map<String,Object> map = sysRoleService.findRoleDataByUserId(userId);
        return ResultUtils.success(map);

    }

    @ApiOperation("分配角色")
    @PostMapping("/doAssign")
    public void doAssign(@RequestBody AssginRoleVo assginRoleVo){
        if(assginRoleVo == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        sysRoleService.doAssign(assginRoleVo);
    }




}