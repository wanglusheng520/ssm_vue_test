package com.beneway.common.system.entity.sys_tag.fo;

import com.beneway.common.common.utils.page.PageQuery;
import lombok.Data;

/**
 */
@Data
public class SysTagQueryFo extends PageQuery {


    /**
     *
     */
    private Integer id;

    /**
     * 分类
     */
    private String type;

    /**
     * 说明
     */
    private String remake;

}
