package com.beneway.common.system.service.sys_unit;

import com.beneway.common.common.mybatis.MyIService;
import com.beneway.common.common.result.Result;
import com.beneway.common.system.entity.sys_unit.SysUnit;
import com.beneway.common.system.entity.sys_unit.fo.SysUnitFo;
import com.beneway.common.system.entity.sys_unit.vo.SysUnitComVo;
import com.beneway.common.system.entity.sys_unit.vo.SysUnitVo;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 *
 *
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022-02-23 19:13:49
 */
public interface SysUnitService extends MyIService<SysUnit> {

    Result add(SysUnitFo sysUnitFo);

    Result edit(SysUnitFo sysUnitFo);

    Result del(Integer id);

    /**
     * 根据父级id获取单位树列表
     * @param pid
     * @return
     */
    List<SysUnitVo> getTreeListByPid(Integer pid);

    List<SysUnitVo> getTreeListByPid(List<SysUnit> sysUnitList, Integer pid);

    /**
     * 根据父级id获取下级所有单位id列表
     * @return
     */
    Set<Integer> getIdListByPid(Integer pid);

    Set<Integer> getIdListByPid(List<SysUnit> sysUnitList, Integer pid);

    /**
     * 获取区域下的下级所有单位id列表
     * @param areaId
     * @return
     */
    Set<Integer> getAreaUnderIdList(Integer areaId);

    /**
     * 获取单位从大到小的列表
     * @param unitId
     * @return
     */
    List<SysUnit> getInList(Integer unitId);

    /**
     * 获取单位从大到小的列表
     * @param sysUnitList 单位列表
     * @param unitId
     * @return
     */
    List<SysUnit> getInList(List<SysUnit> sysUnitList, Integer unitId);

    /**
     * 将单位列表转为tree
     * @param list
     * @return
     */
    List<SysUnitVo> toTreeVo(List<SysUnitVo> list);

    List<SysUnitVo> toTree(List<SysUnit> list);

    /**
     * 设置所有子单位id
     * @param unitIdList
     */
    void setChildrenId(Collection<Integer> unitIdList);

    /**
     * 获取前端统一组件的单位信息
     * @return
     */
     SysUnitComVo getComUnitInfo(Integer unitId);

    /**
     * 获取单个单位信息
     */
    SysUnitComVo getUnitInfo(Integer unitId);

    /**
     * 获取前端统一组件的单位信息多个
     * @return
     */
    List<SysUnitComVo> getComUnitInfoList(String unitIds);

    /**
     * 获取前端统一组件的单位信息多个
     * @return
     */
    List<SysUnitComVo> getUnitInfoList(String unitIds);

    /**
     * 获取单位区域树集合
     * @return
     */
    List<SysUnitVo> getAreaTree();

    List<SysUnitVo> getAreaTree(List<SysUnit> sysUnitList);

}

