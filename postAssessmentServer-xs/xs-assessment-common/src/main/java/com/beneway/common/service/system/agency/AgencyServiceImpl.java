package com.beneway.common.service.system.agency;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.enums.AgencyPositionEnum;
import com.beneway.common.common.utils.base.PageUtils;
import com.beneway.common.common.utils.exception.RRException;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.system.agency.AgencyDao;
import com.beneway.common.dao.system.login.LoginuserDao;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.agency.Agency;
import com.beneway.common.entity.system.agency.AgencyGroupVo;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.service.system.login.LoginuserService;
import com.beneway.common.service.userposition.UserPositionService;
import com.beneway.common.system.entity.sys_user.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AgencyServiceImpl extends ServiceImpl<AgencyDao, Agency> implements AgencyService{

    @Autowired
    private AgencyDao agencyDao;

    @Autowired
    private LoginuserDao loginuserDao;

    @Autowired
    private UserPositionService userPositionService;

    @Autowired
    private LoginuserService loginuserService;

    @Override
    public void packLqw(LambdaQueryWrapper<Agency> wrapper){
        wrapper.eq(Agency::getDel, false);
    }

    private List<Agency> getList(LambdaQueryWrapper<Agency> wrapper){
        this.packLqw(wrapper);
        List<Agency> list = this.list(wrapper);
        return list;
    }

    @Override
    public Result insert(Agency agency) {
        checkAgency(agency, false);
        agency.setCreateMan(LoginUserUtils.getLoginUserId());
        agency.setCreateTime(new Date());
        agency.setPosition(AgencyPositionEnum.PT.getPosition());
        agency.setDel(false);
        agencyDao.insert(agency);
        return Result.success();
    }

    @Override
    public Result update(Agency agency) {
        checkAgency(agency, true);
        agency.setCreateMan(LoginUserUtils.getLoginUserId());
        agency.setCreateTime(new Date());
        agencyDao.updateById(agency);
        return Result.success();
    }

    @Override
    public Result delete(Integer id) {
        this.update(new LambdaUpdateWrapper<Agency>().set(Agency::getDel, true).eq(Agency::getId, id));
        return Result.success();
    }

    /**
     * 检查单位信息
     * @param agency
     */
    private void checkAgency(Agency agency, boolean isUpdate){
        int count = this.count(new QueryWrapper<Agency>()
                .lambda()
                .eq(Agency::getId, agency.getPid())
                .eq(Agency::getAgencyType, "3"));
        if (count > 0){
            throw new RRException("父级不能为科室");
        }

        String agencyType = agency.getAgencyType();
        if ("2".equals(agencyType)){
            agency.setAgencyCode(agency.getAgencyCode().toUpperCase());
            count = this.count(new LambdaQueryWrapper<Agency>()
                    .ne(isUpdate, Agency::getId, agency.getId())
                    .eq(Agency::getAgencyType, "2")
                    .eq(Agency::getAgencyCode, agency.getAgencyCode()));

            if (count > 0){
                throw new RRException("单位编码重复");
            }
        }
    }

    @Override
    public Result find(Agency agency) {
        agency = agencyDao.selectById(agency);
        return Result.check(agency);
    }

    @Override
    public Result getTreeAgency() {
        LambdaQueryWrapper<Agency> qw = new LambdaQueryWrapper<>();
        List<Agency> agencies = this.getList(qw);
        List<Agency> list = new ArrayList<>();
        for (Agency agency : agencies) {
            if(agency.getPid()==0){
                list.add(agency);
                comp(agencies,agency);
            }
        }
        return Result.success(list);
    }

    /**
     * 递归整理菜单,xm-select用
     */
    private void comp(List<Agency> agencies,Agency agency){
        List<Agency> list = new ArrayList<>();

        for (Agency son : agencies) {
            if (son.getPid().equals(agency.getId())){
                list.add(son);
                comp(agencies,son);
            }
        }
        if (CollUtil.isNotEmpty(list)){
            agency.setChildren(list);
        }
    }

    @Override
    public List<Integer> getChildById(Integer id) {
        List<Integer> idList = new ArrayList<>();
        compsId(idList, id);
        return idList;
    }

    private void compsId(List<Integer> idList, Integer id){
        idList.add(id);
        List<Agency> list = this.getList(new QueryWrapper<Agency>().lambda().select(Agency::getId).eq(Agency::getPid, id));
        for (Agency agency : list) {
            Integer agencyId = agency.getId();
            compsId(idList, agencyId);
        }
    }

    /*
        新的方法
     */

    @Override
    public List<Agency> getTree(Integer agencyId){
        if (agencyId != null){
            Agency agency = this.getById(agencyId);
            List<Agency> list = this.getList(new LambdaQueryWrapper<Agency>()
                    .eq(Agency::getPid, agencyId)
                    .eq(Agency::getAgencyType, "3"));
            agency.setChildren(list);
            List<Agency> agencyList = new LinkedList<>();
            agencyList.add(agency);
            return agencyList;
        }else{
            List<Agency> list = this.getList(new LambdaQueryWrapper<Agency>().orderByAsc(Agency::getSeq));
            // 筛选出最外层的单位
            List<Agency> pList = list.stream().filter(agency -> agency.getPid() == 0).collect(Collectors.toList());
            // 获取各个单位的子单位
            getAgencyChildren(list, pList);
            return pList;
        }
    }

    @Override
    public List<Agency> getDw(Integer agencyId) {
        if (agencyId != null){
            Agency agency = this.getById(agencyId);
            List<Agency> list = this.getList(new LambdaQueryWrapper<Agency>()
                    .eq(Agency::getPid, agencyId)
                    .eq(Agency::getAgencyType, "3"));
            agency.setChildren(list);
            List<Agency> agencyList = new LinkedList<>();
            agencyList.add(agency);
            return agencyList;
        }else{
            List<Agency> list = this.getList(new LambdaQueryWrapper<Agency>().orderByAsc(Agency::getSeq).ne(Agency::getAgencyType , 3));
            // 筛选出最外层的单位
            List<Agency> pList = list.stream().filter(agency -> agency.getPid() == 0).collect(Collectors.toList());
            // 获取各个单位的子单位
            getAgencyChildren(list, pList);
            return pList;
        }
    }

    private void getAgencyChildren(List<Agency> list, List<Agency> agencyList){
        if (CollUtil.isNotEmpty(agencyList)){
            for (Agency agency : agencyList) {
                if (agency == null){
                    continue;
                }
                Integer agencyId = agency.getId();
                List<Agency> collect = list.stream().filter(a -> a.getPid().equals(agencyId)).collect(Collectors.toList());
                if (CollUtil.isNotEmpty(collect)){
                    collect.forEach(a -> a.setPosition(agency.getPosition()));
                    agency.setChildren(collect);
                    getAgencyChildren(list, collect);
                }
            }

        }
    }

    /**
     * 获取单位不包含科室
     * @return
     */
    @Override
    public List<Agency> getAgencyTree() {
        List<Agency> list = this.getList(new LambdaQueryWrapper<Agency>()
                .eq(Agency::getAgencyType, "2")
                .orderByAsc(Agency::getSeq));
        // 筛选出最外层的单位
        List<Agency> pList = list.stream().filter(agency -> agency.getPid() == 0).collect(Collectors.toList());
        // 获取各个单位的子单位
        getAgencyChildren(list, pList);
        return list;
    }

    /**
     * 获取单位不包含科室 根据grouping进行分组
     * @return
     */
    @Override
    public List<AgencyGroupVo> getAgencyTreeGroup(){
        List<Agency> list = this.getList(new LambdaQueryWrapper<Agency>()
                .eq(Agency::getAgencyType, "2")
                .isNotNull(Agency::getAgencyGrouping)
                .orderByAsc(Agency::getSeq));
        List<AgencyGroupVo> groupList = new ArrayList<>();
        for (Agency agency : list) {
            String grouping = agency.getAgencyGrouping();
            AgencyGroupVo agencyGroupVo = null;
            for (AgencyGroupVo a : groupList) {
                if (a.getAgencyName().equals(grouping)){
                    agencyGroupVo = a;
                }
            }
            if (agencyGroupVo == null){
                agencyGroupVo = new AgencyGroupVo();
                agencyGroupVo.setAgencyName(grouping);
                List<Agency> agencyList = new ArrayList<>();
                agencyList.add(agency);
                agencyGroupVo.setChildren(agencyList);
                groupList.add(agencyGroupVo);
            }else{
                agencyGroupVo.getChildren().add(agency);
            }
        }
        return groupList;
    }

    /**
     * 获取发文单位列表
     * @return
     */
    @Override
    public List<Agency> getDispatchAgencyTree() {

        LoginUserInfo user = LoginUserUtils.getLoginUserInfo();

        List<Agency> list = this.getList(new LambdaQueryWrapper<Agency>()
                .eq(Agency::getAgencyType, "2")
                .and(qw -> qw.eq(Agency::getPosition, AgencyPositionEnum.XZF.getPosition())
                    .or().eq(Agency::getPosition, AgencyPositionEnum.GOV.getPosition())
                    .or().eq(Agency::getId, user.getSupAgencyId()))
                    .orderByDesc(Agency::getSeq));

        return list;
    }

    /**
     * 发文单位判断
     * @return
     */
    @Override
    public String isUnitJudge(String AgencyId) {
        LoginUserInfo user = LoginUserUtils.getLoginUserInfo();
        if (user.getSupAgencyId().toString().equals(AgencyId)) {
            //本单位判断
            return "T";
        } else if (AgencyId.equals("989") || AgencyId.equals("1246")){
            //根据单位id判断是否为县政府
            return "S";
        } else {
            return null;
        }
    }

    @Override
    public Page<Agency> queryPage(Map<String, Object> params) {
        boolean b = userPositionService.judgeCurrUserAdminOrSupAdmin();
        if (!b){
            return new Page<Agency>();
        }

        Page page = PageUtils.getPage(params);
        String pid = (String) params.get("pid");
        String keyword = (String) params.get("keyword");

        LambdaQueryWrapper<Agency> wrapper = new LambdaQueryWrapper<Agency>();
        packLqw(wrapper);
        wrapper.eq(StrUtil.isNotEmpty(pid), Agency::getPid, pid)
                .and(StrUtil.isNotEmpty(keyword), q -> {
                    q.like(Agency::getAgencyName, keyword)
                            .or()
                            .like(Agency::getAgencyShort, keyword)
                            .or()
                            .like(Agency::getAgencyCode, keyword);
                });

        if (!LoginUserUtils.isAdmin()){
            wrapper.eq("0".equals(pid), Agency::getId, LoginUserUtils.getLoginUserInfo().getSupAgencyId());
        }

        wrapper.orderByAsc(Agency::getSeq);
        Page<Agency> iPage = this.page(page, wrapper);
        return iPage;
    }

    /**
     * 获取单位用户选择列表
     * @param agencyId
     * @return
     */
    @Override
    public List<Map<String, Object>> getAgencyUserSelectList(Integer agencyId){
        List<Agency> agencyList = this.list(new LambdaQueryWrapper<Agency>()
                .eq(Agency::getPid, agencyId)
                .orderByAsc(Agency::getSeq));
        List<Map<String, Object>> list = new ArrayList<>();
        for (Agency agency : agencyList) {
            Map<String, Object> map = new HashMap<>();
            map.put("label", agency.getAgencyName());
            List<Loginuser> loginuserList = loginuserService.list(new LambdaQueryWrapper<Loginuser>()
                    .eq(Loginuser::getAgencyId, agency.getId())
                    .orderByAsc(Loginuser::getCreateTime));
            List<Map<String, Object>> userMapList = new ArrayList<>();
            for (Loginuser loginuser : loginuserList) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("label", loginuser.getUsername());
                userMap.put("value", loginuser.getId());
                userMapList.add(userMap);
            }
            map.put("children", userMapList);
            list.add(map);
        }
        return list;
    }

    @Override
    public String getWholeAgencyNameById(Integer agencyId) {
        String sj = "";
        while (agencyId != null && agencyId != 0){
            Agency agency = this.getById(agencyId);
            if (agency != null){
                String agencyName = agency.getAgencyName();
                sj = agencyName + "-" + sj;
                agencyId = agency.getPid();
            }else{
                break;
            }
        }
        if (sj != ""){
            sj = sj.substring(0, sj.length() - 1);
        }
        return sj;
    }

    @Override
    public Agency getAgencyById(Integer agencyId) {
        Agency agency = this.getById(agencyId);
        if (agency != null){
            String agencyType = agency.getAgencyType();
            if ("2".equals(agencyType)){
                return agency;
            }else if ("3".equals(agencyType)){
                return getAgencyById(agency.getPid());
            }
        }
        return null;
    }

    @Override
    public String getAgencyNameById(Integer agencyId) {
        Agency agency = getAgencyById(agencyId);
        if (agency != null){
            return agency.getAgencyName();
        }
        return null;
    }

    /**
     * 根据单位类型获取单位
     * @param agencyPositionEnum
     * @return
     */
    @Override
    public Agency getAgencyByPosition(AgencyPositionEnum agencyPositionEnum) {
        Integer position = agencyPositionEnum.getPosition();
        Agency agency = this.getOne(new LambdaQueryWrapper<Agency>().eq(Agency::getPosition, position));
        return agency;
    }

    @Override
    public String getAgencyIdsByPositions(AgencyPositionEnum... agencyPositionEnums){
        List<Integer> collect = Arrays.stream(agencyPositionEnums)
                .map(AgencyPositionEnum::getPosition)
                .collect(Collectors.toList());
        List<Agency> agencyList = this.getList(new LambdaQueryWrapper<Agency>().select(Agency::getId).in(Agency::getPosition, collect));
        if (CollUtil.isNotEmpty(agencyList)){
            StringJoiner stringJoiner = new StringJoiner(",");
            for (Agency agency : agencyList) {
                stringJoiner.add(String.valueOf(agency.getId()));
            }
            return stringJoiner.toString();
        }else {
            return null;
        }
    }

    /**
     * 判断单位是否为规定类型
     * @param agencyId
     * @param agencyPositionEnum
     * @return
     */
    @Override
    public boolean judgeAgencyPosition(Integer agencyId, AgencyPositionEnum agencyPositionEnum) {
        Agency agency = this.getAgencyById(agencyId);
        Integer position = agency.getPosition();
        AgencyPositionEnum positionEnum = AgencyPositionEnum.getByPosition(position);
        return positionEnum.equals(agencyPositionEnum);
    }



}
