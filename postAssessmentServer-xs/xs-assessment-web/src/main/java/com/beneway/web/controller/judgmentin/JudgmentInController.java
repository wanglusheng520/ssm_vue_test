package com.beneway.web.controller.judgmentin;

import com.beneway.common.common.result.Result;
import com.beneway.common.entity.judgmentin.fo.JudgmentInFo;
import com.beneway.common.service.judgmentin.JudgmentInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 入库研判
 *
 * @author zxc
 * @date 2022/2/22 14:44
 */
@RestController
@RequestMapping("/judgmentIn")
public class JudgmentInController {

    @Autowired
    JudgmentInService judgmentInService;

    /**
     * 研判
     * @param
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody JudgmentInFo judgmentInFo){
        return judgmentInService.add(judgmentInFo);
    }

    /**
     * 自动入库
     */
    @PostMapping("/auto")
    public Result auto(@RequestBody JudgmentInFo judgmentInFo){
        return Result.ok();
    }


}
