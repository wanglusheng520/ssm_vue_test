package com.beneway.common.system.service.sys_filter_unit.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.system.dao.sys_filter_unit.SysFilterUnitDao;
import com.beneway.common.system.entity.sys_filter_unit.SysFilterUnit;
import com.beneway.common.system.entity.sys_filter_unit.enums.SysFilterUnitKeyEnum;
import com.beneway.common.system.entity.sys_filter_unit.enums.SysFilterUnitModeEnum;
import com.beneway.common.system.entity.sys_filter_unit.fo.SysFilterUnitFo;
import com.beneway.common.system.entity.sys_filter_unit.vo.SysFilterUnitVo;
import com.beneway.common.system.entity.sys_unit.SysUnit;
import com.beneway.common.system.entity.sys_unit.enums.SysUnitTypeEnum;
import com.beneway.common.system.entity.sys_unit.vo.SelectUnitVo;
import com.beneway.common.system.entity.sys_unit.vo.SysUnitVo;
import com.beneway.common.system.service.sys_filter_unit.SysFilterUnitService;
import com.beneway.common.system.service.sys_tag.SysTagMapService;
import com.beneway.common.system.service.sys_tag.SysTagService;
import com.beneway.common.system.service.sys_unit.SysUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户筛选配置
 *
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022-03-10 20:14:11
 */
@Service("sysFilterUnitService")
public class SysFilterUnitServiceImpl implements SysFilterUnitService {

    @Autowired
    private SysFilterUnitDao sysFilterUnitDao;

    @Autowired
    private SysUnitService sysUnitService;

    @Autowired
    private SysTagService sysTagService;

    @Autowired
    private SysTagMapService sysTagMapService;

    @Override
    public List<SysFilterUnitVo> getList() {
        SysFilterUnitKeyEnum[] keyEnums = SysFilterUnitKeyEnum.values();
        List<SysFilterUnit> sysFilterUnitList = sysFilterUnitDao.selectList(new LambdaQueryWrapper<SysFilterUnit>()
                .in(SysFilterUnit::getKey, keyEnums));
        List<SysFilterUnitVo> voList = ClassUtil.toClassList(sysFilterUnitList, SysFilterUnitVo.class);
        for (SysFilterUnitVo sysFilterUnitVo : voList) {
            SysFilterUnitModeEnum unitMode = sysFilterUnitVo.getUnitMode();
            sysFilterUnitVo.setUnitModeDesc(unitMode.getDesc());

            // 封装数据
            String unitData = sysFilterUnitVo.getUnitData();
            List<Integer> idList = Arrays.stream(StrUtil.split(unitData, ","))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
            if (SysFilterUnitModeEnum.UNIT_LIST.equals(unitMode)
                || SysFilterUnitModeEnum.UNIT_AREA.equals(unitMode)) {
                sysFilterUnitVo.setUnitIdList(idList);
            } else if (SysFilterUnitModeEnum.UNIT_TAG.equals(unitMode)) {
                if (CollUtil.isNotEmpty(idList)) {
                    sysFilterUnitVo.setTagList(idList);
                }
            }
        }
        return voList;
    }

    @Override
    public Result edit(SysFilterUnitFo sysFilterUnitFo) {
        SysFilterUnitKeyEnum key = sysFilterUnitFo.getKey();
        SysFilterUnit sysFilterUnit = sysFilterUnitDao.selectOne(new LambdaQueryWrapper<SysFilterUnit>().eq(SysFilterUnit::getKey, key));
        SysFilterUnitModeEnum unitMode = sysFilterUnit.getUnitMode();

        String remark = sysFilterUnitFo.getRemark();
        LambdaUpdateWrapper<SysFilterUnit> wrapper = new LambdaUpdateWrapper<SysFilterUnit>()
                .set(SysFilterUnit::getRemark, remark)
                .eq(SysFilterUnit::getKey, key);

        if (SysFilterUnitModeEnum.UNIT_TAG.equals(unitMode)
                || SysFilterUnitModeEnum.UNIT_LIST.equals(unitMode)) {
            List<Integer> idList = sysFilterUnitFo.getIdList();
            String unitData = idList.stream().map(String::valueOf).collect(Collectors.joining(","));
            wrapper.set(SysFilterUnit::getUnitData, unitData);
            sysFilterUnitDao.update(null, wrapper);
        } else if (SysFilterUnitModeEnum.UNIT_AREA.equals(unitMode)) {
            String unitData = sysFilterUnitFo.getUnitData();
            wrapper.set(SysFilterUnit::getUnitData, unitData);
            sysFilterUnitDao.update(null, wrapper);
        }

        return Result.ok();
    }

    @Override
    public Set<Integer> getUnitIdSet(SysFilterUnitKeyEnum keyEnum) {
        SysFilterUnit sysFilterUnit = sysFilterUnitDao.selectOne(new LambdaQueryWrapper<SysFilterUnit>().eq(SysFilterUnit::getKey, keyEnum));
        return getUnitIdSet(sysFilterUnit.getUnitMode(), sysFilterUnit.getUnitData());
    }

    @Override
    public Set<Integer> getUnitIdSet(SysFilterUnitModeEnum unitModeEnum, String unitData) {
        Set<Integer> parentUnitIdList = new HashSet<>();
        if (SysFilterUnitModeEnum.UNIT_TAG.equals(unitModeEnum)) {
            List<Integer> tagIdList = Arrays.stream(StrUtil.split(unitData, ","))
                   .map(Integer::valueOf)
                   .collect(Collectors.toList());
            Set<String> idList = sysTagMapService.getAssIdSetByTagIdList(tagIdList);
            Set<Integer> collect = idList.stream().map(Integer::valueOf).collect(Collectors.toSet());
            return collect;
        } else if (SysFilterUnitModeEnum.UNIT_LIST.equals(unitModeEnum)) {
            Set<Integer> unitIdList = Arrays.stream(StrUtil.split(unitData, ","))
                   .map(Integer::valueOf)
                   .collect(Collectors.toSet());
            parentUnitIdList.addAll(unitIdList);
        } else if (SysFilterUnitModeEnum.UNIT_LOGIN.equals(unitModeEnum)) {
            Integer unitId = LoginUserUtils.getCurrentAgencyId();
            parentUnitIdList.add(unitId);
        } else if (SysFilterUnitModeEnum.UNIT_AREA.equals(unitModeEnum)) {
            Integer areaUnitId = Integer.parseInt(unitData);
//            parentUnitIdList.add(areaUnitId);
            return sysUnitService.getAreaUnderIdList(areaUnitId);
        } else if (SysFilterUnitModeEnum.UNIT_AREA_LOGIN.equals(unitModeEnum)) {
            Integer areaUnitId = LoginUserUtils.getCurrentAreaId();
//            parentUnitIdList.add(areaUnitId);
            return sysUnitService.getAreaUnderIdList(areaUnitId);
        } else if (SysFilterUnitModeEnum.UNIT_AREA_LOGIN_TAG.equals(unitModeEnum)) {
            Integer areaUnitId = LoginUserUtils.getCurrentAreaId();
            Set<Integer> unitIdSet = sysUnitService.getAreaUnderIdList(areaUnitId);

            List<Integer> tagIdList = Arrays.stream(StrUtil.split(unitData, ","))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
            Set<String> tagUnitidSet = sysTagMapService.getAssIdSetByTagIdList(tagIdList);

            return unitIdSet.stream()
                    .filter(unitId -> tagUnitidSet.contains(String.valueOf(unitId)))
                    .collect(Collectors.toSet());
        } else {
            throw new RuntimeException("mode错误");
        }

        // 根据父级单位id列表获取所有子集单位id集合
        sysUnitService.setChildrenId(parentUnitIdList);

        return parentUnitIdList;
    }

    /**
     * 获取前端选择单位公共组件数据
     * @return
     */
    @Override
    public SelectUnitVo getSelectUnitData(SysFilterUnitKeyEnum keyEnum) {
        List<SysUnit> list = sysUnitService.list();
        if (keyEnum != null) {
            Set<Integer> set = getUnitIdSet(keyEnum);
            list = list.stream().filter(sysUnit -> set.contains(sysUnit.getId())).collect(Collectors.toList());
        }
        // 获取区域选择树
        List<SysUnitVo> areaUnitTree = sysUnitService.getAreaTree(list);
        // 获取单位列表
        List<SysUnitVo> unitList = getUnitList(list);

        SelectUnitVo selectUnitVo = new SelectUnitVo();
        selectUnitVo.setSysUnitTree(areaUnitTree);
        selectUnitVo.setSysUnitList(unitList);

        return selectUnitVo;
    }

    private List<SysUnitVo> getUnitList(List<SysUnit> sysUnitList) {
        List<SysUnit> agencyList = sysUnitList.stream()
                .filter(sysUnit -> SysUnitTypeEnum.UNIT.equals(sysUnit.getType()))
                .collect(Collectors.toList());

        List<SysUnitVo> agencyVoList = ClassUtil.toClassList(agencyList, SysUnitVo.class);

        for (SysUnitVo sysUnitVo : agencyVoList) {
            List<SysUnit> inList = sysUnitService.getInList(sysUnitList, sysUnitVo.getId());
            inList = inList.subList(0, inList.size() - 1);
            sysUnitVo.setParentList(inList);
        }

        return agencyVoList;
    }

    /**
     * 获取子集列表
     * @param pid 父id
     * @param include 是否包含父
     * @return
     */
    @Override
    public List<SysUnit> getChildren(Integer pid, boolean include) {
        List<SysUnit> list = sysUnitService.list(new LambdaQueryWrapper<SysUnit>()
                .eq(SysUnit::getPid, pid)
                .or()
                .eq(include, SysUnit::getId, pid));
        return list;
    }

    /**
     * 根据id获取一直到第一级的单位列表
     * @param id
     * @return
     */
    @Override
    public List<SysUnit> getTopLineList(Integer id) {
        List<SysUnit> list = sysUnitService.list();
        List<SysUnit> sysUnitList = new LinkedList<>();
        Function<Integer, Integer> getPid = uid -> {
            for (SysUnit sysUnit : list) {
                if (sysUnit.getId().equals(uid)) {
                    return sysUnit.getPid();
                }
            }
            return null;
        };
        while (true) {
            Integer pid = getPid.apply(id);
            if (pid != null) {
                List<SysUnit> collect = list.stream().filter(sysUnit -> sysUnit.getPid().equals(pid)).collect(Collectors.toList());
                sysUnitList.addAll(collect);
            } else {
                break;
            }
            id = pid;
        }
        return sysUnitList;
    }

}
