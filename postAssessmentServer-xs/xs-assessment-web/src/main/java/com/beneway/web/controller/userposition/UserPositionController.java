package com.beneway.web.controller.userposition;

import com.beneway.common.common.enums.UserPositionEnum;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.service.userposition.UserPositionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhy
 * @email 2434017367@qq.com
 * @date 2021-07-29 09:53:20
 */
@Log4j2
@CrossOrigin
@RestController
@RequestMapping("/userposition")
public class UserPositionController {

    @Autowired
    private UserPositionService userPositionService;

    /**
     * 根据角色 获取本单位 用户名和id 列表
     * 除去自己
     *
     * @param positionType
     * @return
     */
    @GetMapping("/getAgencyUserListByPosition/{positionType}")
    public Result getAgencyUserListByPosition(@PathVariable Integer positionType) {
        List<Loginuser> list = userPositionService.getAgencyUserListByPosition(UserPositionEnum.getByPosition(positionType));
        return Result.success(list);
    }

    /**
     * 根据角色 用户名和id 列表
     *
     * @param positionType
     * @return
     */
    @GetMapping("/getUserListByPosition/{positionType}")
    public Result getUserListByPosition(@PathVariable Integer positionType) {
        List<Loginuser> list = userPositionService.getUserListByPosition(UserPositionEnum.getByPosition(positionType));
        return Result.success(list);
    }

    /**
     * 获取公平竞争审查或合法性审查人员列表
     *
     * @param type F：公平竞争审查 L：合法性审查
     * @return
     */
    @GetMapping("/getFairLegalUserList/{type}")
    public Result getFairLegalUserList(@PathVariable String type) {
        List<Loginuser> userList = userPositionService.getFairLegalUserList(type);
        return Result.success(userList);
    }

    @GetMapping("/getFairUserSup")
    public Result getFairUserSup() {
        int supPosition = userPositionService.getFairUserSup();
        return Result.success(supPosition);
    }

    @GetMapping("/getNodeUserSup")
    public Result getNodeUserSup() {
        int supPosition = userPositionService.getNodeUserSup();
        return Result.success(supPosition);
    }

}
