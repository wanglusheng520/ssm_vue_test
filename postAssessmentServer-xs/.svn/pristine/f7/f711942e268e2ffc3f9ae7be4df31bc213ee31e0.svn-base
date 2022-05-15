package com.beneway.web.controller.system.agency;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.common.utils.base.PageUtils;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.agency.Agency;
import com.beneway.common.entity.system.agency.AgencyGroupVo;
import com.beneway.common.service.system.agency.AgencyService;
import com.beneway.common.service.system.login.LoginuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agency")
public class AgencyController {
    @Autowired
    AgencyService agencyService;
    @Autowired
    LoginuserService loginuserService;

    @GetMapping("/")
    public Result find(Agency agency){
        return agencyService.find(agency);
    }

    @PostMapping("/")
    public Result insert(@RequestBody  Agency agency){
        return agencyService.insert(agency);
    }

    @PutMapping("/")
    public Result update(@RequestBody  Agency agency){
        return agencyService.update(agency);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id){
        return agencyService.delete(id);
    }

    @GetMapping("/getTreeAgency")
    public Result getTreeAgency(){
        return agencyService.getTreeAgency();
    }

    /**
     * 找出所有主体单位，包括平级
     */
    @GetMapping("/getUserAgencyTreePid")
    public Result getUserAgencyTreePid(String id){
//        return agencyService.getUserAgencyTreePid(id);
        List<Agency> agencyTreeData = agencyService.getTree(null);
        return Result.success(agencyTreeData);
    }

    /*
        新的方法
     */

    /**
     * 获取单位及单位下的科室
     * @return
     */
    @GetMapping("/getTree")
    public Result getTree(){
        Integer agencyId = null;
        if (!LoginUserUtils.isAdmin()){
            agencyId = LoginUserUtils.getLoginUserInfo().getSupAgencyId();
        }
        List<Agency> agencyTreeData = agencyService.getTree(agencyId);
        return Result.success(agencyTreeData);
    }

    /**
     * 只获取单位
     * @return
     */
    @GetMapping("/getDw")
    public Result getDw(){
        Integer agencyId = null;
        if (!LoginUserUtils.isAdmin()){
            agencyId = LoginUserUtils.getLoginUserInfo().getSupAgencyId();
        }
        List<Agency> agencyTreeData = agencyService.getDw(agencyId);
        return Result.success(agencyTreeData);
    }

    /**
     * 获取单位不包含科室
     * @return
     */
    @GetMapping("/getAgencyTree")
    public Result getAgencyTree(){
        List<Agency> agencyTree = agencyService.getAgencyTree();
        return Result.success(agencyTree);
    }

    /**
     * 获取单位不包含科室 根据grouping进行分组
     * @return
     */
    @GetMapping("/getAgencyTreeGroup")
    public Result getAgencyTreeGroup(){
        List<AgencyGroupVo> treeGroup = agencyService.getAgencyTreeGroup();
        return Result.success(treeGroup);
    }

    /**
     * 发文单位判断
     */
    @GetMapping("/isUnitJudge")
    public Result isisUnitJudge(@RequestParam("AgencyId") String AgencyId) {
        return Result.success(agencyService.isUnitJudge(AgencyId));
    }

    /**
     * 获取单位列表
     */
    @GetMapping("/getDispatchAgencyTree")
    public Result getDispatchAgencyTree() {
        List<Agency> agencyTree = agencyService.getDispatchAgencyTree();
        return Result.success(agencyTree);
    }

    /**
     * 单位管理分页列表
     * @param params
     * @return
     */
    @GetMapping("/queryPage")
    public Result queryPage(@RequestParam Map<String, Object> params){
        Page<Agency> page = agencyService.queryPage(params);
        return PageUtils.getPageResult(page);
    }

    /**
     * 获取单位用户选择列表
     * @param agencyId
     * @return
     */
    @GetMapping("/getAgencyUserSelectList")
    public Result getAgencyUserSelectList(Integer agencyId){
        if (agencyId == null){
            agencyId = LoginUserUtils.getLoginUserInfo().getSupAgencyId();
        }
        List<Map<String, Object>> mapList = agencyService.getAgencyUserSelectList(agencyId);
        return Result.success(mapList);
    }

}
