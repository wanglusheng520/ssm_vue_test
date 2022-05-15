package com.beneway.common.system.service.sys_tag.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.system.dao.sys_tag.SysTagMapDao;
import com.beneway.common.system.entity.sys_tag.SysTag;
import com.beneway.common.system.entity.sys_tag.SysTagMap;
import com.beneway.common.system.entity.sys_tag.enums.SysTagTypeEnum;
import com.beneway.common.system.service.sys_tag.SysTagMapService;
import com.beneway.common.system.service.sys_tag.SysTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 *
 * @author wkx
 * @email 1181597045@qq.com
 * @date 2022-03-04 14:04:38
 */
@Service("sysTagMapService")
public class SysTagMapServiceImpl extends ServiceImpl<SysTagMapDao, SysTagMap> implements SysTagMapService {

    @Autowired
    private SysTagMapDao sysTagMapDao;

    @Autowired
    private SysTagService sysTagService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addTagMap(String assId, List<Integer> tagList, SysTagTypeEnum tagTypeEnum) {
        List<SysTagMap> list = new LinkedList<>();
        for (Integer tagId : tagList) {
            SysTagMap sysTagMap = new SysTagMap();
            sysTagMap.setSysTagId(tagId);
            sysTagMap.setAssId(assId);
            sysTagMap.setType(tagTypeEnum);
            list.add(sysTagMap);
        }
        this.saveBatch(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTagMap(String assId, List<Integer> tagList, SysTagTypeEnum tagTypeEnum) {
        removeTagMap(assId,tagTypeEnum);
        addTagMap(assId, tagList, tagTypeEnum);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeTagMap(String assId, SysTagTypeEnum tagTypeEnum) {
        this.remove(new LambdaQueryWrapper<SysTagMap>()
                .eq(SysTagMap::getAssId, assId)
                .eq(SysTagMap::getType, tagTypeEnum));
    }

    @Override
    public List<SysTag> getByAssId(String assId, SysTagTypeEnum sysTagTypeEnum) {
        List<SysTagMap> list = this.list(new LambdaQueryWrapper<SysTagMap>()
                .eq(SysTagMap::getAssId, assId)
                .eq(SysTagMap::getType, sysTagTypeEnum));
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> collect = list.stream().map(SysTagMap::getSysTagId).collect(Collectors.toList());
        List<SysTag> tagList = sysTagService.listByIds(collect);
        return tagList;
    }

    @Override
    public Set<String> getAssIdSetByTagIdList(List<Integer> idList) {
        List<SysTagMap> list = this.list(new LambdaQueryWrapper<SysTagMap>()
                .select(SysTagMap::getAssId)
                .in(SysTagMap::getSysTagId, idList));
        return list.stream().map(SysTagMap::getAssId).collect(Collectors.toSet());
    }

}
