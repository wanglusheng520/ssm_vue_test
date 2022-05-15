package com.beneway.common.system.dao.sys_tag;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.system.entity.sys_tag.SysTag;
import com.beneway.common.system.entity.sys_tag.fo.SysTagQueryFo;
import com.beneway.common.system.entity.sys_tag.vo.SysTagVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wkx
 * @date 2022-03-03 10:13:49
 */
@Mapper
@Repository
public interface SysTagDao extends BaseMapper<SysTag> {

    Page<SysTagVo> queryPage(Page page, @Param("params") SysTagQueryFo sysTagQueryFo);

}
