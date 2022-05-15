package com.beneway.common.service.userposition;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.enums.AgencyPositionEnum;
import com.beneway.common.common.enums.UserPositionEnum;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.userposition.UserPositionDao;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.agency.Agency;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.entity.userposition.UserPosition;
import com.beneway.common.service.system.agency.AgencyService;
import com.beneway.common.service.system.login.LoginuserService;
import com.beneway.common.system.entity.sys_user.vo.LoginUserInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.User;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 *
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2021-07-29 09:53:20
 */
@Log4j2
@EnableAspectJAutoProxy( proxyTargetClass = true , exposeProxy = true )
@CacheConfig(cacheNames = "userPosition")
@Service("userPositionService")
public class UserPositionServiceImpl extends ServiceImpl<UserPositionDao, UserPosition> implements UserPositionService {

    @Autowired
    private UserPositionDao userPositionDao;

    @Autowired
    private LoginuserService loginuserService;

    @Autowired
    private AgencyService agencyService;

    private UserPositionService getCurrThis(){
        UserPositionService currentProxy = (UserPositionService) AopContext.currentProxy();
        return currentProxy;
    }

    @Cacheable(key = "'list'")
    @Override
    public List<UserPosition> list() {
        return super.list();
    }

    /**
     * 设置
     */
    @CacheEvict(key = "'list'", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setUserPosition(String userId, List<Integer> positionList) {
        if (StrUtil.isEmpty(userId) || CollUtil.isEmpty(positionList)){
            return;
        }

        // 获取用户单位类型
        Loginuser loginuser = loginuserService.getById(userId);
        Integer supAgencyId = loginuser.getSupAgencyId();
        Agency agency = agencyService.getAgencyById(supAgencyId);
        Integer agencyPosition = agency.getPosition();

        // 删除原先的记录
        this.remove(new LambdaQueryWrapper<UserPosition>()
                .eq(UserPosition::getUserId, userId));

        // 保存现在的
        List<UserPosition> userPositionList = new LinkedList<>();
        for (Integer p : positionList) {
            UserPosition userPosition = new UserPosition();
            userPosition.setUserId(userId);
            userPosition.setAgencyPosition(agencyPosition);
            userPosition.setPosition(p);
            userPositionList.add(userPosition);
        }
        this.saveBatch(userPositionList);
    }

    /**
     * 获取list
     * @param userId
     * @return
     */
    @Override
    public List<Integer> getListByUserId(String userId) {
//        List<UserPosition> userPositionList = this.list(new LambdaQueryWrapper<UserPosition>()
//                .select(UserPosition::getPosition)
//                .eq(UserPosition::getUserId, userId));
//
//        List<Integer> list = userPositionList.stream().map(UserPosition::getPosition).collect(Collectors.toList());

        List<UserPosition> userPositionList = getCurrThis().list();
        List<Integer> list = userPositionList.stream()
                .filter(userPosition -> userId.equals(userPosition.getUserId()))
                .map(UserPosition::getPosition)
                .sorted()
                .collect(Collectors.toList());
        return list;
    }

    /**
     * 根据单位和角色获取用户列表
     * @param agencyId
     * @param userPositionEnum
     * @return
     */
    @Override
    public List<String> getUserIdList(Integer agencyId, UserPositionEnum userPositionEnum) {
        List<Loginuser> loginuserList = loginuserService.list(new LambdaQueryWrapper<Loginuser>()
                .select(Loginuser::getId)
                .and(q -> q.eq(Loginuser::getSupAgencyId, agencyId).or().eq(Loginuser::getAgencyId, agencyId))
                .eq(Loginuser::getDel,false));

        List<String> idUserList = loginuserList.stream().map(Loginuser::getId).collect(Collectors.toList());

//        List<UserPosition> userPositionList = this.list(new LambdaQueryWrapper<UserPosition>()
//                .select(UserPosition::getUserId)
//                .in(UserPosition::getUserId, idUserList)
//                .eq(UserPosition::getPosition, userPositionEnum.getPosition()));
//
//        idUserList = userPositionList.stream().map(UserPosition::getUserId).collect(Collectors.toList());

        Predicate<UserPosition> predicate = userPosition -> {
            return idUserList.contains(userPosition.getUserId()) && userPositionEnum.getPosition().equals(userPosition.getPosition());
        };
        List<String> list = getUserIdListFilter(predicate);
        return list;

    }

    @Override
    public List<String> getUserIdList(Integer agencyId, UserPositionEnum... userPositionEnums) {
        List<UserPositionEnum> enums = Arrays.stream(userPositionEnums).distinct().collect(Collectors.toList());
        List<Integer> positionList = enums.stream().map(UserPositionEnum::getPosition).collect(Collectors.toList());

        List<Loginuser> loginuserList = loginuserService.list(new LambdaQueryWrapper<Loginuser>()
                .select(Loginuser::getId)
                .and(q -> q.eq(Loginuser::getSupAgencyId, agencyId).or().eq(Loginuser::getAgencyId, agencyId))
                .eq(Loginuser::getDel,false));

        List<String> idUserList = loginuserList.stream().map(Loginuser::getId).collect(Collectors.toList());

        Predicate<UserPosition> predicate = userPosition -> {
            return idUserList.contains(userPosition.getUserId()) && positionList.contains(userPosition.getPosition());
        };
        List<String> list = getUserIdListFilter(predicate);
        return list;
    }

    @Override
    public List<String> getUserIdList(List<Integer> agecnyIdList, UserPositionEnum userPositionEnum) {
        List<String> list = new LinkedList<>();
        for (Integer agencyId : agecnyIdList) {
            List<String> userIdList = getUserIdList(agencyId, userPositionEnum);
            list.addAll(userIdList);
        }
        list = list.stream().distinct().collect(Collectors.toList());
        return list;
    }

    @Override
    public List<String> getUserIdList(AgencyPositionEnum agencyPositionEnum, UserPositionEnum userPositionEnum) {
//        List<UserPosition> userPositionList = this.list(new LambdaQueryWrapper<UserPosition>()
//                .select(UserPosition::getUserId)
//                .eq(UserPosition::getAgencyPosition, agencyPositionEnum.getPosition())
//                .eq(UserPosition::getPosition, userPositionEnum.getPosition()));
//
//        List<String> idUserList = userPositionList.stream().map(UserPosition::getUserId).collect(Collectors.toList());

        Predicate<UserPosition> predicate = userPosition -> {
            return agencyPositionEnum.getPosition().equals(userPosition.getAgencyPosition()) && userPositionEnum.getPosition().equals(userPosition.getPosition());
        };
        List<String> list = getUserIdListFilter(predicate);
        return list;
    }

    @Override
    public List<String> getUserIdList(UserPositionEnum userPositionEnum) {
        Predicate<UserPosition> predicate = userPosition -> userPositionEnum.getPosition().equals(userPosition.getPosition());
        return getUserIdListFilter(predicate);
    }

    private List<String> getUserIdListFilter(Predicate<UserPosition> predicate){
        List<UserPosition> userPositionList = getCurrThis().list();

        List<String> idUserList = userPositionList.stream()
                .filter(predicate)
                .map(UserPosition::getUserId)
                .distinct()
                .collect(Collectors.toList());

        return idUserList;
    }

    /**
     * 根据角色判断用户是否有该角色
     * @return
     */
    @Override
    public boolean judgeUserPosition(String userId, UserPositionEnum userPositionEnum) {
//        int count = this.count(new LambdaQueryWrapper<UserPosition>()
//                .eq(UserPosition::getUserId, userId)
//                .eq(UserPosition::getPosition, userPositionEnum.getPosition()));
//        return count > 0;

        Predicate<UserPosition> predicate = userPosition -> {
            return userId.equals(userPosition.getUserId())
                    && userPositionEnum.getPosition().equals(userPosition.getPosition());
        };

        return judgeUserPositionFilter(predicate);
    }

    @Override
    public boolean judgeUserPosition(String userId, AgencyPositionEnum agencyPositionEnum) {
//        int count = this.count(new LambdaQueryWrapper<UserPosition>()
//                .eq(UserPosition::getUserId, userId)
//                .eq(UserPosition::getAgencyPosition, agencyPositionEnum.getPosition()));
//        return count > 0;

        Predicate<UserPosition> predicate = userPosition -> {
            return userId.equals(userPosition.getUserId())
                    && agencyPositionEnum.getPosition().equals(userPosition.getAgencyPosition());
        };

        return judgeUserPositionFilter(predicate);
    }

    @Override
    public boolean judgeUserPosition(String userId, AgencyPositionEnum... agencyPositionEnums) {
        List<Integer> pList = new LinkedList<>();
        for (AgencyPositionEnum agencyPositionEnum : agencyPositionEnums) {
            pList.add(agencyPositionEnum.getPosition());
        }

        Predicate<UserPosition> predicate = userPosition -> {
            return userId.equals(userPosition.getUserId())
                    && pList.contains(userPosition.getAgencyPosition());
        };

        return judgeUserPositionFilter(predicate);
    }

    @Override
    public boolean judgeUserPosition(String userId, AgencyPositionEnum agencyPositionEnum, UserPositionEnum userPositionEnum) {
//        int count = this.count(new LambdaQueryWrapper<UserPosition>()
//                .eq(UserPosition::getUserId, userId)
//                .eq(UserPosition::getAgencyPosition, agencyPositionEnum.getPosition())
//                .eq(UserPosition::getPosition, userPositionEnum.getPosition()));
//        return count > 0;

        Predicate<UserPosition> predicate = userPosition -> {
            return userId.equals(userPosition.getUserId())
                    && agencyPositionEnum.getPosition().equals(userPosition.getAgencyPosition())
                    && userPositionEnum.getPosition().equals(userPosition.getPosition());
        };
        return judgeUserPositionFilter(predicate);
    }

    private boolean judgeUserPositionFilter(Predicate<UserPosition> predicate){
        List<UserPosition> list = getCurrThis().list();
        long count = list.stream().filter(predicate).count();
        return count > 0;
    }

    /**
     * 判断当前登陆用户角色是不是管理员或超级管理员
     * @return
     */
    @Override
    public boolean judgeCurrUserAdminOrSupAdmin(){
        boolean b = judgeUserPosition(LoginUserUtils.getLoginUserId(), UserPositionEnum.GLY);
        if (b || LoginUserUtils.isAdmin()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Loginuser> getAgencyUserListByPosition(UserPositionEnum userPositionEnum) {
        // 获取登录用户
        LoginUserInfo user = LoginUserUtils.getLoginUserInfo();
        Integer supAgencyId = user.getSupAgencyId();
        // 根据登录用户所在单位和角色 查询所有用户
        List<String> userIdList = this.getUserIdList(supAgencyId, userPositionEnum);
        if (CollUtil.isNotEmpty(userIdList)){
            return loginuserService.list(new LambdaQueryWrapper<Loginuser>()
                    .select(Loginuser::getId, Loginuser::getUsername)
                    .in(Loginuser::getId, userIdList));
        }else{
            return new ArrayList<>();
        }
    }

    /**
     * 根据角色 用户名和id 列表
     * @param userPositionEnum
     * @return
     */
    @Override
    public List<Loginuser> getUserListByPosition(UserPositionEnum userPositionEnum) {
        List<String> userIdList = this.getUserIdList(userPositionEnum);
        List<Loginuser> list = loginuserService.list(new LambdaQueryWrapper<Loginuser>()
                .select(Loginuser::getId, Loginuser::getUsername)
                .in(Loginuser::getId, userIdList));
        return list;
    }

    /**
     * 获取公平竞争审查或合法性审查人员列表
     * @param type F：公平竞争审查 L：合法性审查
     * @return
     */
    @Override
    public List<Loginuser> getFairLegalUserList(String type){
        UserPositionEnum userPositionEnum;
        if ("F".equals(type)){
            userPositionEnum = UserPositionEnum.GPJZSCRY;
        }else{
            userPositionEnum = UserPositionEnum.HFXSCRY;
        }

        LoginUserInfo user = LoginUserUtils.getLoginUserInfo();
        Integer supAgencyId = user.getSupUnitId();
        Agency agency = agencyService.getById(supAgencyId);
        Integer sfAgencyId = agency.getSfAgencyId();
        if (sfAgencyId != null){
            // 司法所人员
            List<String> userIdList = getCurrThis().getUserIdList(sfAgencyId, userPositionEnum);
            // 本单位人员
            List<String> userIdList1 = getCurrThis().getUserIdList(supAgencyId, userPositionEnum);
            // 全部审查人员
            userIdList.addAll(userIdList1);
            List<Loginuser> list = loginuserService.list(new LambdaQueryWrapper<Loginuser>()
                    .select(Loginuser::getId, Loginuser::getUsername)
                    .in(Loginuser::getId, userIdList));
            return list;
        }else{
            // 判断单位是否为司法局，如果是人员则不包括司法所的人员
            boolean isSfj = agencyService.judgeAgencyPosition(supAgencyId, AgencyPositionEnum.SFJ);
            if (isSfj){
                // 获取司法所单位id列表
                List<Agency> sfAgencyList = agencyService.list(new LambdaQueryWrapper<Agency>()
                        .select(Agency::getSfAgencyId)
                        .isNotNull(Agency::getSfAgencyId));
                List<Integer> sfAgencyIdList = sfAgencyList.stream().map(Agency::getSfAgencyId).collect(Collectors.toList());

                List<Agency> list = agencyService.list(new LambdaQueryWrapper<Agency>()
                        .select(Agency::getId)
                        .eq(Agency::getPid, supAgencyId)
                        .notIn(Agency::getId, sfAgencyIdList));
                List<Integer> deptIdList = list.stream().map(Agency::getId).collect(Collectors.toList());

                List<String> userIdList = getCurrThis().getUserIdList(deptIdList, userPositionEnum);
                return loginuserService.list(new LambdaQueryWrapper<Loginuser>()
                        .select(Loginuser::getId, Loginuser::getUsername)
                        .in(Loginuser::getId, userIdList));
            }else{
                return getCurrThis().getAgencyUserListByPosition(userPositionEnum);
            }
        }
    }

    @Override
    public int getFairUserSup() {
        String userId = LoginUserUtils.getLoginUserId();
        boolean a = this.judgeUserPosition(userId, UserPositionEnum.PTRY);
        if (a) {
            return UserPositionEnum.GPJZSCFGLD.getPosition();
        }
        boolean b = this.judgeUserPosition(userId, UserPositionEnum.GPJZSCFGLD);
        if (b) {
            return UserPositionEnum.ZYLD.getPosition();
        }
        boolean c = this.judgeUserPosition(userId, UserPositionEnum.ZYLD);
        if (c) {
            return UserPositionEnum.FXZ.getPosition();
        }
        return 0;
    }

    @Override
    public int getNodeUserSup() {
        String userId = LoginUserUtils.getLoginUserId();
        // 分管主任
        boolean b = this.judgeUserPosition(userId, UserPositionEnum.XFBFGZR);
        if (b) {
            return UserPositionEnum.FXZ.getPosition();
        }
        // 县政府操作人员
        boolean a = this.judgeUserPosition(userId,AgencyPositionEnum.XZF);
        if (a) {
            return UserPositionEnum.XFBFGZR.getPosition();
        }

        return 0;
    }

}
