package com.submerge.auth.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.submerge.auth.exception.BusinessException;
import com.submerge.auth.service.SysUserService;
import com.submerge.common.result.BaseResponse;
import com.submerge.common.result.ErrorCode;
import com.submerge.common.result.ResultUtils;
import com.submerge.model.system.SysUser;
import com.submerge.vo.system.SysUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Past;
import java.util.List;

/**
 * ClassName: SysUserController
 * Package: com.submerge.auth.Controller
 * Description: 用户控制器
 *
 * @Author Submerge--WangDong
 * @Create 2024-04-07 10:06
 * @Version 1.0
 */
@RestController
@Api(tags = "用户相关接口")
@Slf4j
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 用户分页查询
     *
     * @param page
     * @param limit
     * @param sysUserQueryVo
     * @return
     */
    @GetMapping("{page}/{limit}")
    @ApiOperation("分页查询用户")
    public BaseResponse<Page<SysUser>> index(@PathVariable Long page,
                                             @PathVariable Long limit,
                                             SysUserQueryVo sysUserQueryVo) {
        Page<SysUser> sysUserPage = new Page<>(page, limit);
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        String username = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();
        if (StringUtils.isBlank(username) || StringUtils.isAnyBlank(createTimeBegin, createTimeEnd)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        sysUserQueryWrapper.like("username", username);
        sysUserQueryWrapper.ge("create_time", createTimeBegin);
        sysUserQueryWrapper.le("create_time", createTimeEnd);
        Page<SysUser> resultPage = sysUserService.page(sysUserPage, sysUserQueryWrapper);
        return ResultUtils.success(resultPage);
    }

    /**
     * 添加用户
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("添加用户")
    public BaseResponse<Boolean> save(@RequestBody SysUser sysUser) {
        if (sysUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = sysUserService.save(sysUser);
        return ResultUtils.success(result);

    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @GetMapping("get/{id}")
    @ApiOperation("根据id查询用户")
    public BaseResponse<SysUser> get(@PathVariable Long id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SysUser result = sysUserService.getById(id);
        return ResultUtils.success(result);
    }

    @ApiOperation("修改用户信息")
    @PutMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody SysUser sysUser) {
        if (sysUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = sysUserService.updateById(sysUser);
        return ResultUtils.success(result);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id删除用户")
    @DeleteMapping("remove/{id}")
    public BaseResponse<Boolean> remove(@PathVariable Long id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = sysUserService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 批量删除用户
     *
     * @param ids
     * @return
     */
    @DeleteMapping("batchRemove")
    @ApiOperation("批量删除用户")
    public BaseResponse<Boolean> batchRemove(List<SysUser> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = sysUserService.removeByIds(ids);
        return ResultUtils.success(result);
    }

    @GetMapping("/updateStatus/{id}/{status}")
    @ApiOperation("更新用户状态")
    public BaseResponse<SysUser> updateStatus(@PathVariable Long id,
                                              @PathVariable Integer status){
        sysUserService.updateStatus(id,status);
        return ResultUtils.success(null);
    }


}
