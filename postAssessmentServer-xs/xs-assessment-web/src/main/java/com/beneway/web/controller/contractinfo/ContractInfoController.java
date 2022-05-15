package com.beneway.web.controller.contractinfo;

import com.beneway.common.common.utils.log.MyLog;
import com.beneway.common.entity.contractinfo.ContractInfo;
import com.beneway.common.entity.system.Result;
import com.beneway.common.service.contractinfo.ContractInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/contractInfo")
public class ContractInfoController {

    @Autowired
    private ContractInfoService contractInfoService;


    /**
     * 合同信息数据分页
     * @param params
     * @return
     */
    @MyLog(operation = "合同查询")
    @GetMapping("/queryPage")
    public Result queryPage(@RequestParam Map<String, Object> params){
        return contractInfoService.queryPage(params);
    }

    /**
     * 删除合同（逻辑删除）
     */
    @DeleteMapping("/")
    public Result delete(ContractInfo contractInfo){
        return contractInfoService.delete(contractInfo);
    }

}
