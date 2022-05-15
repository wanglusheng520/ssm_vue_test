package com.beneway.common.system.service.sys_unit.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.system.dao.sys_unit.SysUnitDao;
import com.beneway.common.system.entity.sys_tag.SysTag;
import com.beneway.common.system.entity.sys_tag.enums.SysTagTypeEnum;
import com.beneway.common.system.entity.sys_unit.SysUnit;
import com.beneway.common.system.entity.sys_unit.enums.SysUnitTypeEnum;
import com.beneway.common.system.entity.sys_unit.fo.SysUnitFo;
import com.beneway.common.system.entity.sys_unit.vo.SysUnitComVo;
import com.beneway.common.system.entity.sys_unit.vo.SysUnitVo;
import com.beneway.common.system.service.sys_tag.SysTagMapService;
import com.beneway.common.system.service.sys_unit.SysUnitService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 *
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2022-02-23 19:13:49
 */
@EnableAspectJAutoProxy( proxyTargetClass = true , exposeProxy = true )
@CacheConfig(cacheNames = "sysUnit")
@Service("sysUnitService")
public class SysUnitServiceImpl extends ServiceImpl<SysUnitDao, SysUnit> implements SysUnitService {

    @Autowired
    private SysTagMapService sysTagMapService;

    private SysUnitService getCurrThis(){
        SysUnitService currentProxy = (SysUnitService) AopContext.currentProxy();
        return currentProxy;
    }

    @Cacheable(key = "'list'")
    @Override
    public List<SysUnit> list() {
        return super.list(new LambdaQueryWrapper<SysUnit>().orderByAsc(SysUnit::getSortNum));
    }

    @CacheEvict(key = "'list'", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result add(SysUnitFo sysUnitFo) {
        this.save(sysUnitFo);

        List<Integer> tagList = sysUnitFo.getTagList();
        sysTagMapService.addTagMap(String.valueOf(sysUnitFo.getId()), tagList, SysTagTypeEnum.AGENCY);

        return Result.ok();
    }

    @CacheEvict(key = "'list'", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result edit(SysUnitFo sysUnitFo) {
        this.updateById(sysUnitFo);

        List<Integer> tagList = sysUnitFo.getTagList();
        sysTagMapService.updateTagMap(String.valueOf(sysUnitFo.getId()), tagList, SysTagTypeEnum.AGENCY);

        return Result.ok();
    }

    @CacheEvict(key = "'list'", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result del(Integer id) {
        this.removeById(id);
        sysTagMapService.removeTagMap(String.valueOf(id), SysTagTypeEnum.AGENCY);
        return Result.ok();
    }

    @Override
    public List<SysUnitVo> getTreeListByPid(Integer pid) {
        List<SysUnit> list = getCurrThis().list();
        return getTreeListByPid(list, pid);
    }

    @Override
    public List<SysUnitVo> getTreeListByPid(List<SysUnit> list, Integer pid) {
        List<SysUnitVo> sysUnitVoList = new ArrayList<>(list.size());
        for (SysUnit sysUnit : list) {
            SysUnitVo sysUnitVo = ClassUtil.toClass(sysUnit, SysUnitVo.class);
            // 封装标签
            List<SysTag> tagList = sysTagMapService.getByAssId(String.valueOf(sysUnitVo.getId()), SysTagTypeEnum.AGENCY);
            sysUnitVo.setTagList(tagList);

            sysUnitVoList.add(sysUnitVo);
        }

        List<SysUnitVo> fList = sysUnitVoList.stream().filter(sysUnitVo -> pid.equals(sysUnitVo.getPid())).collect(Collectors.toList());
        children(fList, sysUnitVoList);
        return fList;
    }

    private void children(List<SysUnitVo> fList, List<SysUnitVo> list){
        for (SysUnitVo sysUnitVo : fList) {
            Integer id = sysUnitVo.getId();
            List<SysUnitVo> children = list.stream().filter(sysUnit -> sysUnit.getPid().equals(id)).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(children)){
                sysUnitVo.setChildren(children);
                children(children, list);
            }
        }
    }

    @Override
    public Set<Integer> getIdListByPid(Integer pid) {
        List<SysUnit> list = getCurrThis().list();
        return getIdListByPid(list, pid);
    }

    @Override
    public Set<Integer> getIdListByPid(List<SysUnit> list, Integer pid) {
        Set<Integer> unitIdSet = new HashSet<>();
        unitIdSet.add(pid);
        Set<Integer> pidSet = new HashSet<>();
        pidSet.add(pid);
        while (CollUtil.isNotEmpty(pidSet)){
            Set<Integer> set = new HashSet<>();
            for (SysUnit sysUnit : list) {
                if (pidSet.contains(sysUnit.getPid())){
                    set.add(sysUnit.getId());
                }
            }
            unitIdSet.addAll(set);
            pidSet = new HashSet<>(set);
        }
        return unitIdSet;
    }

    /**
     * 获取区域下的下级所有单位id列表
     * @param areaId
     * @return
     */
    @Override
    public Set<Integer> getAreaUnderIdList(Integer areaId) {
        List<SysUnit> list = getCurrThis().list();

        Set<Integer> unitIdSet = new HashSet<>();
        unitIdSet.add(areaId);
        Set<Integer> pidSet = new HashSet<>();
        pidSet.add(areaId);
        while (CollUtil.isNotEmpty(pidSet)){
            Set<Integer> set = new HashSet<>();
            for (SysUnit sysUnit : list) {
                if (pidSet.contains(sysUnit.getPid())
                        && !sysUnit.getType().equals(SysUnitTypeEnum.AREA)){
                    set.add(sysUnit.getId());
                }
            }
            unitIdSet.addAll(set);
            pidSet = new HashSet<>(set);
        }

        return unitIdSet;
    }

    @Override
    public List<SysUnit> getInList(Integer unitId) {
        List<SysUnit> list = getCurrThis().list();
        return getInList(list, unitId);
    }

    @Override
    public List<SysUnit> getInList(List<SysUnit> sysUnitList, Integer unitId) {
        List<SysUnit> list = new LinkedList<>();
        while (unitId != null && unitId != 0) {
            for (SysUnit sysUnit : sysUnitList) {
                if (unitId.equals(sysUnit.getId())) {
                    list.add(sysUnit);
                    unitId = sysUnit.getPid();
                    break;
                }
            }
        }
        Collections.reverse(list);
        return list;
    }

    /**
     * 将单位列表转为tree
     * @param list
     * @return
     */
    @Override
    public List<SysUnitVo> toTreeVo(List<SysUnitVo> list) {
        Set<Integer> isChildrenList = new HashSet<>();
        for (SysUnitVo sysUnitVo : list) {
            Integer id = sysUnitVo.getId();
            for (SysUnitVo unitVo : list) {
                if (id.equals(unitVo.getPid())) {
                    List<SysUnitVo> children = sysUnitVo.getChildren();
                    if (children == null) {
                        children = new ArrayList<>();
                        sysUnitVo.setChildren(children);
                    }
                    children.add(unitVo);
                    isChildrenList.add(unitVo.getId());
                }
            }
        }

        return list.stream()
                .filter(sysUnitVo -> !isChildrenList.contains(sysUnitVo.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SysUnitVo> toTree(List<SysUnit> list) {
        List<SysUnitVo> voList = ClassUtil.toClassList(list, SysUnitVo.class);
        return toTreeVo(voList);
    }

    @Override
    public void setChildrenId(Collection<Integer> unitIdList) {
        // 根据父级单位id列表获取所有子集单位id集合
        List<SysUnit> list = getCurrThis().list();
        boolean b;
        do {
            b = false;
            for (SysUnit sysUnit : list) {
                if (unitIdList.contains(sysUnit.getPid()) && !unitIdList.contains(sysUnit.getId())) {
                    unitIdList.add(sysUnit.getId());
                    b = true;
                }
            }
        } while (b);
    }

    /**
     * 获取前端统一组件的单位信息
     * @return
     */
    @Override
    public SysUnitComVo getComUnitInfo(Integer unitId) {
        List<SysUnit> inList = getCurrThis().getInList(unitId);
        String collect = inList.stream().map(SysUnit::getUnitName).collect(Collectors.joining("/"));
        SysUnitComVo sysUnitComVo = new SysUnitComVo();
        sysUnitComVo.setUnitName(collect);
        return sysUnitComVo;
    }

    /**
     * 获取前端统一组件的单位信息
     * @return
     */
    @Override
    public SysUnitComVo getUnitInfo(Integer unitId) {
        SysUnit sysUnit = getCurrThis().getById(unitId);
        SysUnitComVo sysUnitComVo = new SysUnitComVo();
        sysUnitComVo.setUnitName(sysUnit.getUnitName());
        return sysUnitComVo;
    }

    /**
     * 获取前端统一组件的单位信息
     * @return
     */
    @Override
    public List<SysUnitComVo> getUnitInfoList(String unitIds) {
        List<SysUnitComVo> sysUnitComVoList = new ArrayList<>();
        if(StringUtils.isNotEmpty(unitIds)){
            String ids[] = unitIds.split(",");
            for(String id:ids){
                Integer unitId = Integer.parseInt(id);
                SysUnitComVo sysUnitComVo = getCurrThis().getUnitInfo(unitId);
                sysUnitComVoList.add(sysUnitComVo);
            }
        }
        return sysUnitComVoList;
    }

    /**
     * 获取前端统一组件的单位信息
     * @return
     */
    @Override
    public List<SysUnitComVo> getComUnitInfoList(String unitIds) {
        List<SysUnitComVo> sysUnitComVoList = new ArrayList<>();
        if(StringUtils.isNotEmpty(unitIds)){
            String ids[] = unitIds.split(",");
            for(String id:ids){
                Integer unitId = Integer.parseInt(id);
                List<SysUnit> inList = getCurrThis().getInList(unitId);
                String collect = inList.stream().map(SysUnit::getUnitName).collect(Collectors.joining("/"));
                SysUnitComVo sysUnitComVo = new SysUnitComVo();
                sysUnitComVo.setUnitName(collect);
                sysUnitComVoList.add(sysUnitComVo);
            }
        }
        return sysUnitComVoList;
    }

    /**
     * 获取单位区域树集合
     * @return
     */
    @Override
    public List<SysUnitVo> getAreaTree() {
        List<SysUnit> list = getCurrThis().list();
        list = list.stream().filter(sysUnit -> SysUnitTypeEnum.AREA.equals(sysUnit.getType())).collect(Collectors.toList());
        List<SysUnitVo> voList = toTree(list);
        return voList;
    }

    @Override
    public List<SysUnitVo> getAreaTree(List<SysUnit> list) {
        list = list.stream()
                .filter(sysUnit -> SysUnitTypeEnum.AREA.equals(sysUnit.getType())
                        || SysUnitTypeEnum.NODE.equals(sysUnit.getType()))
                .collect(Collectors.toList());
        List<SysUnitVo> voList = toTree(list);
        return voList;
    }

}
