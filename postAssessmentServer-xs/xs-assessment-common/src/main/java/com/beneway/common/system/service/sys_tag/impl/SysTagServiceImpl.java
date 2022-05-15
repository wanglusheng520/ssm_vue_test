package com.beneway.common.system.service.sys_tag.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.page.PageUtils;
import com.beneway.common.system.dao.sys_tag.SysTagDao;
import com.beneway.common.system.entity.sys_tag.SysTag;
import com.beneway.common.system.entity.sys_tag.enums.SysTagTypeEnum;
import com.beneway.common.system.entity.sys_tag.fo.SysTagFo;
import com.beneway.common.system.entity.sys_tag.fo.SysTagQueryFo;
import com.beneway.common.system.entity.sys_tag.vo.SysTagVo;
import com.beneway.common.system.service.sys_tag.SysTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("SysTagService")
public class SysTagServiceImpl extends ServiceImpl<SysTagDao, SysTag> implements SysTagService {


    @Autowired
    private SysTagDao sysTagDao;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result add(SysTagFo sysTagFo) {
        this.save(sysTagFo);
        return Result.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result edit(SysTagFo sysTagFo) {
        this.updateById(sysTagFo);
        return Result.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result del(Integer tagId) {
        this.removeById(tagId);
        return Result.ok();
    }

    @Override
    public Page<SysTagVo> queryPage(SysTagQueryFo sysTagQueryFo) {
        Page page = PageUtils.getPage(sysTagQueryFo);
        Page<SysTagVo> voPage = sysTagDao.queryPage(page, sysTagQueryFo);
        return voPage;
    }

    @Override
    public List<SysTag> getListByType(SysTagTypeEnum type) {
        List<SysTag> list = this.list(new LambdaQueryWrapper<SysTag>()
                .eq(SysTag::getTagType, type));
        return list;
    }
}
