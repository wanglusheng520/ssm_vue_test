package com.beneway.common.common.utils.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.entity.system.Result;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2019/11/22
 * @time: 18:13
 */
public class PageUtils {


    /**
     * 获取分页对象
     * @param pageStr
     * @param limitStr
     * @return
     */
    public static Page getPage(String pageStr,String limitStr){

        int pageNum = 0;
        int limit = 10;

        if (!Tools.isEmpty(pageStr)){
            pageNum = Integer.parseInt(pageStr);
        }

        if (!Tools.isEmpty(limitStr)){
            limit = Integer.parseInt(limitStr);
        }

        Page page = new Page(pageNum, limit);
        return page;
    }

    public static Page getPage(Map<String, Object> param){
        String page = String.valueOf(param.get("page"));
        String limit = String.valueOf(param.get("limit"));
        return getPage(page, limit);
    }

    /**
     * 获取分页结果集
     * @param iPage
     * @return
     */
    public static Result getPageResult(IPage iPage){
        return Result.success().setData(iPage.getRecords()).setCount((int)iPage.getTotal());
    }

    public static void toPageClass(IPage page, IPage oldPage, Class c){
        page.setTotal(oldPage.getTotal());
        page.setSize(oldPage.getSize());
        page.setCurrent(oldPage.getCurrent());
        page.setPages(oldPage.getPages());

        List records = oldPage.getRecords();
        List list = new ArrayList(records.size());

        for (Object record : records) {
            Object obj = ClassUtil.toClass(record, c);
            list.add(obj);
        }

        page.setRecords(list);
    }

    public static Map<String, Object> getMaxPageMap(){
        Map<String, Object> params = new HashMap<>();
        params.put("limit", Integer.MAX_VALUE);
        return params;
    }

}
