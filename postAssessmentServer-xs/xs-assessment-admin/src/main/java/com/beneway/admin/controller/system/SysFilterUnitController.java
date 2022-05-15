package com.beneway.admin.controller.system;

import com.beneway.common.common.result.Result;
import com.beneway.common.system.entity.sys_filter_unit.enums.SysFilterUnitKeyEnum;
import com.beneway.common.system.entity.sys_unit.SysUnit;
import com.beneway.common.system.entity.sys_unit.vo.SelectUnitVo;
import com.beneway.common.system.service.sys_filter_unit.SysFilterUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create by zhy on 2022/3/11 10:54
 */
@RestController
@RequestMapping("/sys_filter_unit")
public class SysFilterUnitController {

    @Autowired
    private SysFilterUnitService sysFilterUnitService;

    /**
     * 获取前端选择单位公共组件数据
     * @return
     */
    @GetMapping("/getSelectUnitData")
    public Result getSelectUnitData(String key) {
        SysFilterUnitKeyEnum keyEnum = SysFilterUnitKeyEnum.getByKey(key);
        SelectUnitVo selectUnitVo = sysFilterUnitService.getSelectUnitData(keyEnum);
        return Result.ok(selectUnitVo);
    }

    /**
     * 获取子集列表
     * @param pid 父id
     * @param include 是否包含父
     * @return
     */
    @GetMapping("/getChildren")
    public Result getChildren(Integer pid, boolean include) {
        pid = pid != null ? pid : 1;
        List<SysUnit> list = sysFilterUnitService.getChildren(pid, include);
        return Result.ok(list);
    }

    /**
     * 根据id获取一直到第一级的单位列表
     * @param id
     * @return
     */
    @GetMapping("/getTopLineList")
    public Result getTopLineList(Integer id){
        List<SysUnit> topLineList = sysFilterUnitService.getTopLineList(id);
        return Result.ok(topLineList);
    }

}
