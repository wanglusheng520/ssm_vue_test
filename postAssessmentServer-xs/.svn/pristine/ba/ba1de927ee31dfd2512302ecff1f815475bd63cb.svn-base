package com.beneway.common.service.system.router;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.system.router.RouterMapper;
import com.beneway.common.entity.system.router.Meta;
import com.beneway.common.entity.system.router.Router;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.role.Role;
import com.beneway.common.entity.system.rolemenu.RoleMenu;
import com.beneway.common.entity.system.userrole.UserRole;
import com.beneway.common.service.system.role.RoleService;
import com.beneway.common.service.system.rolememu.RoleMenuService;
import com.beneway.common.service.system.userrole.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author wangJun
 * @createTime 2021-09-06
 */

@Service
public class RouterServiceImpl extends ServiceImpl<RouterMapper, Router> implements RouterService {

    @Autowired
    private RouterMapper routerMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private RoleService roleService;

    @Override
    public Result router(Integer id) {
        String userId = LoginUserUtils.getLoginUserId();
        if(LoginUserUtils.isAdmin()){
            List<Router> list = list();
            return getResultByModule(list , id);
        }else{
            QueryWrapper<UserRole> rqw = new QueryWrapper<>();
            rqw.select("role_id").eq("user_id" , userId);
            //获取到用户的角色列表
            List<UserRole> list = userRoleService.list(rqw);
            //获取到用户的角色id,并且筛选出开启的角色
            List<Integer> roleIds = list.stream().map(res -> {
                QueryWrapper<Role> rq  = new QueryWrapper<Role>();
                rq.eq("is_work" , 0);
                rq.eq("id" , res.getRoleId());
                Role one = roleService.getOne(rq);
                return null == one ? null : res.getRoleId();
            }).filter(res -> null != res).collect(Collectors.toList());

            if(roleIds.size()>0){
                QueryWrapper<RoleMenu> rmqw = new QueryWrapper<>();
                rmqw.in("role_id" , roleIds);
                //查询出所有的菜单
                List<RoleMenu> list1 = roleMenuService.list(rmqw);
                //获取到菜单id
                List<Integer> RouterIds = list1.stream().map(res -> {return res.getMenuId();}).distinct().collect(Collectors.toList());
                QueryWrapper<Router> mqw = new QueryWrapper<>();
                mqw.in("id" , RouterIds);
                //查询出菜单
                List<Router> routers = routerMapper.selectList(mqw);
                return getResultByModule(routers , id);
            }
        }
        return Result.fail("角色已关闭");
    }

    public Result getResultByModule(List<Router> routers, Integer id) {
        List<Router> collect = routers.stream().filter(r -> Objects.equals(r.getType(), 1)).map(res -> {
            res.setChildren(getChildrenRouter1(routers, res.getId()));
            res.setMeta(Meta.builder().title(res.getTitle()).icon(res.getIcon()).build());
            return res;
        }).collect(Collectors.toList());
        return Result.success(collect);
    }


    public List<Router> getChildrenRouter1(List<Router> list, Integer id) {
        List<Router> collect = list.stream().filter(r -> Objects.equals(r.getPid(), id)).map(res -> {
            res.setChildren(getChildrenRouter(list, res.getId()));
            res.setMeta(Meta.builder().title(res.getTitle()).icon(res.getIcon()).build());
            return res;
        }).collect(Collectors.toList());
        return collect;
    }












    private Result getResult(List<Router> routers) {
        List<Router> collect = routers.stream().filter(r -> Objects.equals(r.getType(), 0)).map(res -> {
            res.setChildren(getChildrenRouter(routers, res.getId()));
            res.setMeta(Meta.builder().title(res.getTitle()).icon(res.getIcon()).build());
            return res;
        }).collect(Collectors.toList());
        return Result.success(collect);
    }

    @Override
    public Result allRouter() {
        QueryWrapper<Router> qw = new QueryWrapper<>();
        qw.orderByAsc("sequence+0");
        List<Router> routers = routerMapper.selectList(qw);
        return getResult(routers);
    }


    public List<Router> getChildrenRouter(List<Router> list, Integer id) {
        List<Router> collect = list.stream().filter(r -> Objects.equals(r.getPid(), id)).map(res -> {
            res.setChildren(getChildrenRouter(list, res.getId()));
            res.setMeta(Meta.builder().title(res.getTitle()).icon(res.getIcon()).build());
            return res;
        }).collect(Collectors.toList());
        return collect;
    }

    @Transactional
    public Result add(Router router){
        routerMapper.insert(router);
        return Result.success();
    }

    public Result delete(Integer id){
        routerMapper.deleteById(id);
        return Result.success();
    }

    public Result getOne(Router router){
        return Result.success(routerMapper.selectById(router.getId()));
    }

    @Transactional
    public Result update(Router router){
        routerMapper.updateById(router);
        return Result.success();
    }
}
