package com.beneway.common.common.utils.dd.entity.msg;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/12/15
 * @time: 13:44
 *
 * 钉钉工作通知，卡片消息
 */
@Data
public class Card implements Serializable {

    /**
     * 标题
     * 透出到会话列表和通知的文案，最长64个字符
     */
    private String title;

    /**
     * 消息内容
     * 消息内容，目前不支持markdown，只能传纯文本
     */
    private String markdown;

    /**
     * 使用整体跳转ActionCard样式时的标题，必须与single_url同时设置，最长20个字符
     */
    private String single_title;

    /**
     * 消息点击链接地址，当发送消息为小程序时支持小程序跳转链接，最长500个字符
     */
    private String single_url;

    /**
     * 使用独立跳转ActionCard样式时的按钮排列方式，竖直排列(0)，横向排列(1)；必须与btn_json_list同时设置
     */
    private String btn_orientation;

    /**
     * 使用独立跳转ActionCard样式时的按钮列表；必须与btn_orientation同时设置
     */
    private List<CardBtn> btn_json_list;

    public void addBtn(CardBtn cardBtn){
        if (this.btn_json_list == null){
            this.btn_json_list = new LinkedList<>();
        }
        this.btn_json_list.add(cardBtn);
    }

}
