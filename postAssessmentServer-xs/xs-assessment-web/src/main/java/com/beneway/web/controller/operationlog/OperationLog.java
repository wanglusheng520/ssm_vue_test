package com.beneway.web.controller.operationlog;


import com.beneway.common.common.result.Result;
import com.beneway.common.service.operationlog.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operationLog")
public class OperationLog {

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 获取最新评估事件（首页）
     * @return
     */
    @GetMapping("/")
    public Result newAssesEvent(){
        return operationLogService.newAssesEvent();
    }

    /**
     * 获取总评估数  以评估数  总整改数
     * @return
     */
    @GetMapping("/assessAmount")
    public Result assessAmount(){
        return operationLogService.assessAmount();
    }

}
