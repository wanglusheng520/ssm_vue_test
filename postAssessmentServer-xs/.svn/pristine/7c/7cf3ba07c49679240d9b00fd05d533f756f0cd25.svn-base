package com.beneway.common.service.system.dict;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.utils.base.PageUtils;
import com.beneway.common.common.utils.base.Tools;
import com.beneway.common.dao.system.dict.DictDao;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.dict.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DictServiceImp extends ServiceImpl<DictDao, Dict> implements DictService {
    @Autowired
    DictDao dictDao;


    /**
     * 根据key获取子级字典
     */
    @Override
    public Result getDictByKey(Dict dict) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.ne("ne_value","!")
                .eq("is_delete",0)
                .eq("ne_key",dict.getNeKey())
                .orderByAsc("ne_value");
        if(Tools.notEmpty(dict.getOption01())){
            wrapper.eq("option01",dict.getOption01());
        }
        return Result.check(dictDao.selectList(wrapper));
    }

    /**
     * 获取父级字典，不传条件则是查出所有的
     */
    @Override
    public Result getParent(Dict dict) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>(dict);
        wrapper.eq("ne_value","!")
                .eq("is_delete",0)
                .orderByAsc("ne_key");
        List<Dict> list = dictDao.selectList(wrapper);
        for (Dict d : list) {
            d.setTitle(d.getNeKey()+"."+d.getContent());
        }
        return Result.check(list);
    }

    /**
     * 根据key获取子级字典，管理页用，有分页
     */
    @Override
    public Result getSon(Integer page, Integer limit, Dict dict) {
        if (null==page) page=1;
        if (null==limit) limit=10;

        QueryWrapper<Dict> wrapper = new QueryWrapper<>(null);
        wrapper.ne("ne_value","!")
                .eq("ne_key",dict.getNeKey())
                .eq("is_delete",0)
                .orderByAsc("ne_value");
        if (!Tools.isEmpty(dict.getTitle())){
            wrapper.like("title",dict.getTitle());
        }
        IPage<Dict> iPage = dictDao.selectPage(new Page<>(page, limit), wrapper);
        return Result.success(iPage.getRecords()).setCount((int) iPage.getTotal());
    }

    @Override
    public Result getSon1(Map<String, Object> params) {
        IPage<Dict> iPage = dictDao.queryPage(PageUtils.getPage(params) , params);
        return PageUtils.getPageResult(iPage);
    }

    @Override
    public Result find(Dict dict) {
        return Result.check(dictDao.selectById(dict));
    }

    @Override
    public Result insert(Dict dict) {
        dict.setIsDelete(0);
        //插入时判断是子还是父，新增父字典应无key值，新增子字典应有key值
        if (null==dict.getNeKey()){
            //查询一下key值已经到多少了,并赋值key
            dict.setNeKey(dictDao.findKeyMax()).setNeValue("!");
            if (null == dict.getNeKey()){
                dict.setNeKey(1);
            }
        }else{
            //查询value到多少了，并赋值value
            dict.setNeValue(dictDao.findValueMax(dict));
        }
        return Result.check(dictDao.insert(dict));
    }

    @Override
    public Result update(Dict dict) {
        //逻辑删除应放在delete请求里
        dict.setIsDelete(null);
        return Result.check(dictDao.updateById(dict));
    }

    /**
     * 逻辑删除
     */
    @Override
    public Result delete(Dict dict) {
        dict.setIsDelete(1);
        if(null!=dict.getNeKey()){
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("ne_key",dict.getNeKey());
            Dict d = Dict.builder()
                    .isDelete(1)
                    .build();
            dictDao.update(d,wrapper);
        }
        return Result.check(dictDao.updateById(dict));
    }

    /**
     * 逻辑还原
     */
    @Override
    public Result restore(Dict dict) {
        dict.setIsDelete(0);
        return Result.check(dictDao.updateById(dict));
    }

    /**
     * 获取所有已删除的记录
     */
    @Override
    public Result getAllDelete(Integer page,Integer limit,Dict dict) {
        if (null==page) page=1;
        if (null==limit) limit=10;

        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete",1);
        if (!Tools.isEmpty(dict.getTitle())){
            wrapper.like("title",dict.getTitle());
        }
        IPage<Dict> iPage = dictDao.selectPage(new Page<>(page, limit), wrapper);
        return Result.success(iPage.getRecords()).setCount((int) iPage.getTotal());
    }

    /**
     * 真正的删除接口
     */
    @Override
    public Result realDel(Dict dict) {
        return Result.check(dictDao.deleteById(dict));
    }


}
