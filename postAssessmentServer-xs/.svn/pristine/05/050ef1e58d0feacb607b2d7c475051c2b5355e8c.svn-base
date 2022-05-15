package com.beneway.common.system.service.sys_tag;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.common.mybatis.MyIService;
import com.beneway.common.common.result.Result;
import com.beneway.common.system.entity.sys_tag.SysTag;
import com.beneway.common.system.entity.sys_tag.enums.SysTagTypeEnum;
import com.beneway.common.system.entity.sys_tag.fo.SysTagFo;
import com.beneway.common.system.entity.sys_tag.fo.SysTagQueryFo;
import com.beneway.common.system.entity.sys_tag.vo.SysTagVo;

import java.util.List;

public interface SysTagService extends MyIService<SysTag> {

    /**
     * 新增
     * @param sysTagFo
     * @return
     */
    Result add(SysTagFo sysTagFo);

    /**
     * x编辑
     * @param sysTagFo
     * @return
     */
    Result edit(SysTagFo sysTagFo);

    /**
     * 用户删除
     * @param tagId
     * @return
     */
    Result del(Integer tagId);

    /**
     * 分页查询
     * @param sysTagQueryFo
     * @return
     */
    Page<SysTagVo> queryPage(SysTagQueryFo sysTagQueryFo);

    /**
     * 根据类型获取标签列表
     * @param type
     * @return
     */
    List<SysTag> getListByType(SysTagTypeEnum type);
}
