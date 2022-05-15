package com.beneway.common.system.service.sys_tag;

import com.beneway.common.common.mybatis.MyIService;
import com.beneway.common.system.entity.sys_tag.SysTag;
import com.beneway.common.system.entity.sys_tag.SysTagMap;
import com.beneway.common.system.entity.sys_tag.enums.SysTagTypeEnum;

import java.util.List;
import java.util.Set;

/**
 *
 *
 * @author wkx
 * @email 1181597045@qq.com
 * @date 2022-03-04 14:04:38
 */
public interface SysTagMapService extends MyIService<SysTagMap> {


    /**
     * 添加标签映射
     * @param assId     关联表id
     * @param tagList   tag列表
     * @param tagTypeEnum 类型
     */
    void addTagMap(String assId, List<Integer> tagList, SysTagTypeEnum tagTypeEnum);

    /**
     * 更新标签映射
     * @param assId
     * @param tagList
     * @param tagTypeEnum
     */
    void updateTagMap(String assId, List<Integer> tagList, SysTagTypeEnum tagTypeEnum);

    /**
     * 移除标签映射
     * @param assId
     * @param tagTypeEnum
     */
    void removeTagMap(String assId, SysTagTypeEnum tagTypeEnum);

    /**
     * 根据assId获取列表
     * @param assId
     * @param user
     * @return
     */
    List<SysTag> getByAssId(String assId, SysTagTypeEnum user);

    /**
     * 获取assId列表根据id列表
     * @param idList
     * @return
     */
    Set<String> getAssIdSetByTagIdList(List<Integer> idList);
}

