package com.beneway.web.controller.userlogin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.beneway.common.common.utils.base.PageUtils;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.userlogin.UserloginLog;
import com.beneway.common.service.userlogin.UserloginLogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-06-05 17:32:55
 */
@Log4j2
@RestController
@RequestMapping("/userloginlog")
public class UserloginLogController {

    @Autowired
    private UserloginLogService userloginLogService;

    /**
     * 分页
     * @param params
     * @return
     */
    @GetMapping("/queryPage")
    public Result queryPage(@RequestParam Map<String, Object> params){
        IPage<UserloginLog> iPage = userloginLogService.queryPage(params);
        return PageUtils.getPageResult(iPage);
    }

}
