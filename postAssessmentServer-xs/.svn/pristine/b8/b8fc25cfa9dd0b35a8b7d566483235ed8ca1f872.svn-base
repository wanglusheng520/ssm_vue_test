package com.beneway.common.service.userposition;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.enums.AgencyPositionEnum;
import com.beneway.common.common.enums.UserPositionEnum;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.entity.userposition.UserPosition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2021-07-29 09:53:20
 */
public interface UserPositionService extends IService<UserPosition> {

    /**
     * 设置
     */
    void setUserPosition(String userId, List<Integer> positionList);

    /**
     * 获取list
     */
    List<Integer> getListByUserId(String userId);

    /**
     * 根据单位和角色获取用户id列表
     */
    List<String> getUserIdList(Integer agencyId, UserPositionEnum userPositionEnum);

    List<String> getUserIdList(Integer agencyId, UserPositionEnum... userPositionEnums);

    List<String> getUserIdList(List<Integer> agecnyIdList, UserPositionEnum userPositionEnum);

    List<String> getUserIdList(AgencyPositionEnum agencyPositionEnum, UserPositionEnum userPositionEnum);

    List<String> getUserIdList(UserPositionEnum userPositionEnum);

    /**
     * 根据角色判断用户是否有该角色
     * @return
     */
    boolean judgeUserPosition(String userId, UserPositionEnum userPositionEnum);

    boolean judgeUserPosition(String userId, AgencyPositionEnum agencyPositionEnum);

    boolean judgeUserPosition(String userId, AgencyPositionEnum... agencyPositionEnums);

    boolean judgeUserPosition(String userId, AgencyPositionEnum agencyPositionEnum, UserPositionEnum userPositionEnum);

    /**
     * 判断当前登陆用户角色是不是管理员或超级管理员
     * @return
     */
    boolean judgeCurrUserAdminOrSupAdmin();

    /**
     * 根据角色 获取本单位 用户名和id 列表
     * @return
     */
    List<Loginuser> getAgencyUserListByPosition(UserPositionEnum userPositionEnum);

    /**
     * 根据角色 用户名和id 列表
     * @param userPositionEnum
     * @return
     */
    List<Loginuser> getUserListByPosition(UserPositionEnum userPositionEnum);

    /**
     * 获取公平竞争审查或合法性审查人员列表
     * @param type F：公平竞争审查 L：合法性审查
     * @return
     */
    List<Loginuser> getFairLegalUserList(String type);

    /**
     * 获取用户上级领导
     * @return
     */
    int getFairUserSup();

    /**
     * 获取计划节点上级领导
     * @return
     */
    int getNodeUserSup();
}

