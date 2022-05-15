package com.beneway.common.dao.system.dict;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.entity.system.dict.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Mapper
public interface DictDao extends BaseMapper<Dict> {
    Integer findKeyMax();
    String findValueMax(Dict dict);
    Dict findAttr(Dict dict);

    String findByNeValue(Integer neKey,String neValue);

    IPage<Dict> queryPage(Page page, Map<String, Object> params);
}
