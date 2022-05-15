package com.beneway.common.service.system.agency;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.common.enums.AgencyPositionEnum;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.agency.Agency;
import com.beneway.common.entity.system.agency.AgencyGroupVo;

import java.util.List;
import java.util.Map;

public interface AgencyService extends IService<Agency> {

    void packLqw(LambdaQueryWrapper<Agency> wrapper);

    Result getTreeAgency();

    Result insert(Agency agency);

    Result update(Agency agency);

    Result delete(Integer id);

    Result find(Agency agency);

    List<Integer> getChildById(Integer id);

    /*
        新的方法
     */

    List<Agency> getTree(Integer agencyId);

    /**
     * 获取单位不包含科室
     * @return
     */
    List<Agency> getAgencyTree();

    /**
     * 获取单位不包含科室 根据grouping进行分组
     * @return
     */
    List<AgencyGroupVo> getAgencyTreeGroup();

    /**
     * 获取发文单位列表
     * @return
     */
    List<Agency> getDispatchAgencyTree();

    /**
     * 发文单位判断
     * @return
     */
    String isUnitJudge(String AgencyId);

    Page<Agency> queryPage(Map<String, Object> params);

    /**
     * 获取单位用户选择列表
     * @param agencyId
     * @return
     */
    List<Map<String, Object>> getAgencyUserSelectList(Integer agencyId);

    /**
     * 获取完整单位名
     * @param agencyId
     * @return
     */
    String getWholeAgencyNameById(Integer agencyId);

    /**
     * 获取单位
     * @param agencyId
     * @return
     */
    Agency getAgencyById(Integer agencyId);

    /**
     * 获取单位名称
     * @param agencyId
     * @return
     */
    String getAgencyNameById(Integer agencyId);

    /**
     * 根据单位类型获取单位
     * @param agencyPositionEnum
     * @return
     */
    Agency getAgencyByPosition(AgencyPositionEnum agencyPositionEnum);

    String getAgencyIdsByPositions(AgencyPositionEnum... agencyPositionEnums);

    /**
     * 判断单位是否为规定类型
     * @param agencyId
     * @param agencyPositionEnum
     * @return
     */
    boolean judgeAgencyPosition(Integer agencyId, AgencyPositionEnum agencyPositionEnum);

    List<Agency> getDw(Integer agencyId);
}
