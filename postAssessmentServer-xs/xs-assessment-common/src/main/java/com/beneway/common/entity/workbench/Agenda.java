package com.beneway.common.entity.workbench;

import lombok.Data;

import java.io.Serializable;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/11/24
 * @time: 10:45
 */
@Data
public class Agenda implements Serializable {

    private String title;

    private String type;

    private String itemType;

    private String itemTitle;

    private String itemOpt;

    private Object obj;

    private String id;

}
