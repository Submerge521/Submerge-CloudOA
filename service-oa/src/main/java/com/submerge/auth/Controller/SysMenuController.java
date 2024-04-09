package com.submerge.auth.Controller;

import com.submerge.auth.exception.BusinessException;
import com.submerge.auth.service.SysMenuService;
import com.submerge.common.result.BaseResponse;
import com.submerge.common.result.ErrorCode;
import com.submerge.common.result.ResultUtils;
import com.submerge.model.system.SysMenu;
import com.submerge.vo.system.AssginMenuVo;
import com.submerge.vo.system.AssginRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: MenuController
 * Package: com.submerge.auth.Controller
 * Description: 菜单管理
 *
 * @Author Submerge--WangDong
 * @Create 2024-04-09 9:24
 * @Version 1.0
 */
@RestController
@Api(tags = "菜单管理相关接口")
@Slf4j
@RequestMapping("admin/system/sysMenu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;


    /**
     * 菜单列表
     *
     * @return
     */
    @ApiOperation("菜单列表")
    @GetMapping("/findNodes")
    public BaseResponse<List<SysMenu>> findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return ResultUtils.success(list);
    }


    /**
     * 新增菜单
     *
     * @param sysMenu
     * @return
     */
    @ApiOperation("新增菜单")
    @PostMapping("/save")
    public BaseResponse<Boolean> save(@RequestBody SysMenu sysMenu) {
        if (sysMenu == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = sysMenuService.save(sysMenu);
        return ResultUtils.success(result);
    }

    /**
     * 根据id查询菜单
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询菜单")
    @GetMapping("get/{id}")
    public BaseResponse<SysMenu> search(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SysMenu sysMenu = sysMenuService.getById(id);
        return ResultUtils.success(sysMenu);
    }

    /**
     * 更新菜单
     *
     * @param sysMenu
     * @return
     */
    @ApiOperation("更新菜单")
    @PostMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody SysMenu sysMenu) {
        if (sysMenu == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = sysMenuService.updateById(sysMenu);
        return ResultUtils.success(result);
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @ApiOperation("删除菜单")
    @PostMapping("/remove/{id}")
    public BaseResponse<Boolean> remove(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = sysMenuService.removeMenuById(id);
        return ResultUtils.success(result);
    }

    // 查询所有菜单和角色已经分配的菜单
    @ApiOperation("查询所有菜单和角色已经分配的菜单")
    @GetMapping("/toAssign/{roleId}")
    public BaseResponse<List<SysMenu>> toAssign(@PathVariable Long roleId) {
        if (roleId == null || roleId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<SysMenu> list = sysMenuService.findMenuByRoleId(roleId);
        return ResultUtils.success(list);
    }

    // 角色分配菜单
    @ApiOperation("角色分配菜单")
    @PostMapping("doAssign")
    public BaseResponse doAssign(@RequestBody AssginMenuVo assginMenuVo) {
        if (assginMenuVo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long roleId = assginMenuVo.getRoleId();
        if (roleId == null || roleId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        sysMenuService.doAssign(assginMenuVo);
        return ResultUtils.success(true);
    }

}
