package com.beneway.common.service.system.login;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.utils.base.PageUtils;
import com.beneway.common.common.utils.base.Tools;
import com.beneway.common.common.utils.base.UuidUtil;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.system.login.LoginuserDao;
import com.beneway.common.dao.token.TokenDao;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.agency.Agency;
import com.beneway.common.entity.system.login.Loginuser;
import com.beneway.common.entity.system.role.Role;
import com.beneway.common.entity.system.userrole.UserRole;
import com.beneway.common.entity.userlogin.UserloginLog;
import com.beneway.common.service.files.FilesService;
import com.beneway.common.service.system.agency.AgencyService;
import com.beneway.common.service.system.role.RoleService;
import com.beneway.common.service.system.userrole.UserRoleService;
import com.beneway.common.service.userlogin.UserloginLogService;
import com.beneway.common.service.userposition.UserPositionService;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = "user")
@Service
public class LoginuserServiceImp extends ServiceImpl<LoginuserDao, Loginuser> implements LoginuserService{
    private static final Logger logger = LoggerFactory.getLogger(LoginuserServiceImp.class);
    @Autowired
    private LoginuserDao loginuserDao;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private FilesService filesService;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private AgencyService agencyService;

    @Autowired
    private UserPositionService userPositionService;

    @Autowired
    private UserloginLogService userloginLogService;

    @Autowired
    private RoleService roleService;

    private LoginuserService getCurrThis(){
        LoginuserService currentProxy = (LoginuserService) AopContext.currentProxy();
        return currentProxy;
    }

    private Loginuser getByAccountId(String accountId){
        Loginuser loginuser = this.getOne(new LambdaQueryWrapper<Loginuser>().eq(Loginuser::getAccountId, accountId));
        return loginuser;
    }


    /**
     * 保存登录日志
     */
    private void saveLoginLog(String userId){
        //String ipAddr = IPUtil.getIpAddr();
        UserloginLog userloginLog = new UserloginLog();
        userloginLog.setUserId(userId);
        userloginLog.setLoginTime(new Date());
        //userloginLog.setIpAddress(ipAddr);
        userloginLogService.save(userloginLog);
    }

    /**
     * 更新租户id
     * @param jsonObject
     */
    private void upTenantId(JsonObject jsonObject){
        String accountId = jsonObject.get("accountId").getAsString();
        String tenantId = jsonObject.get("tenantId").getAsString();

        this.update(new LambdaUpdateWrapper<Loginuser>()
                .set(Loginuser::getTenantId, tenantId)
                .eq(Loginuser::getAccountId, accountId)
                .isNull(Loginuser::getTenantId));
    }

    /**
     * 根据id获取用户
     */
    @Cacheable(key = "'user:' + #id")
    @Override
    public Loginuser findById(String id) {
        Loginuser loginuser = this.getById(id);
        if (loginuser != null){
            loginuser.setPositionList(userPositionService.getListByUserId(loginuser.getId()));
            // 获取单位信息
            Agency agency = agencyService.getById(loginuser.getSupAgencyId());
            if (agency != null){
                loginuser.setAgencyName(agency.getAgencyName());
            }
            // 获取科室名称
            agency = agencyService.getById(loginuser.getAgencyId());
            if (agency != null){
                loginuser.setDeptName(agency.getAgencyName());
            }

            List<UserRole> userRoleList = userRoleService.list(new LambdaQueryWrapper<UserRole>()
                    .eq(UserRole::getUserId, id)
                    .orderByAsc(UserRole::getId));
            List<Integer> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            loginuser.setRoleIdList(roleIdList);
        }
        return loginuser;
    }

    @Override
    public Loginuser getSafeById(String id) {
        Loginuser loginuser = getCurrThis().findById(id);
        if (loginuser != null){
            Loginuser l = new Loginuser();
            l.setId(loginuser.getId());
            l.setUsername(loginuser.getUsername());
            l.setAgencyName(loginuser.getAgencyName());
            l.setDeptName(loginuser.getDeptName());
            return l;
        }else{
            return null;
        }
    }

    /**
     * 更新用户
     */
    @CacheEvict(key = "'user:' + #loginuser.id")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result update(Loginuser loginuser) {
        if (null == this.getById(loginuser)){
            return Result.fail().setMsg("用户不存在");
        }
        if (checkAccount(loginuser)){
            return Result.fail().setMsg("该账号名已被注册");
        }

        loginuser.setUpdateMan(LoginUserUtils.getLoginUserId());
        loginuser.setUpdateTime(new Date());

        if(Tools.notEmpty(loginuser.getPassword())){
            loginuser.setPassword(DigestUtils.md5DigestAsHex(loginuser.getPassword().getBytes()));
        }

        // 获取supAgencyId
        Agency agency = agencyService.getAgencyById(loginuser.getAgencyId());
        Integer supAgencyId = agency.getId();
        loginuser.setSupAgencyId(supAgencyId);

        this.updateById(loginuser);

        // 更新用户权限角色
        String loginuserId = loginuser.getId();
        userRoleService.delete(loginuserId);
        List<Integer> roleIdList = loginuser.getRoleIdList();
        userRoleService.insert(loginuserId, roleIdList);

        userPositionService.setUserPosition(loginuser.getId(), loginuser.getPositionList());

        return Result.success();
    }

    /**
     * 插入用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insert(Loginuser loginuser) {
        if (checkAccount(loginuser)){
            return Result.fail().setMsg("该账号名已被注册");
        }

        // 获取supAgencyId
        Agency agency = agencyService.getAgencyById(loginuser.getAgencyId());
        Integer supAgencyId = agency.getId();

        loginuser.setId(UuidUtil.get32UUID())
                .setPassword(DigestUtils.md5DigestAsHex(loginuser.getPassword().getBytes()))
                .setCreateMan(LoginUserUtils.getLoginUserId())
                .setCreateTime(new Date())
                .setSupAgencyId(supAgencyId)
                .setDel(false);
        this.save(loginuser);

        userRoleService.insert(loginuser.getId(), loginuser.getRoleIdList());

        userPositionService.setUserPosition(loginuser.getId(), loginuser.getPositionList());

        return Result.success();
    }

    /**
     * 检测账号名冲突
     * 查出除自己以外的，账号为这个值的用户，如果不为空，则账号重复
     */
    @Override
    public boolean checkAccount(Loginuser loginuser) {
        try {
            LambdaQueryWrapper<Loginuser> wrapper = new LambdaQueryWrapper<>();
            wrapper.and(q -> q.eq(Loginuser::getAccount, loginuser.getAccount()).or().eq(Loginuser::getAccountId, loginuser.getAccountId()));
            if (loginuser.getId()!=null){
                wrapper.ne(Loginuser::getId, loginuser.getId());
            }
            Loginuser one = this.getOne(wrapper);
            if (null != one) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    /**
     * 删除用户
     */
    @CacheEvict(key = "'user:' + #loginuser.id")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(String id) {
        this.update(new LambdaUpdateWrapper<Loginuser>().set(Loginuser::getDel, true).eq(Loginuser::getId, id));
        return Result.success();
    }

    @Override
    public Result del(String id) {
        this.update(new LambdaUpdateWrapper<Loginuser>().set(Loginuser::getDel, true).eq(Loginuser::getId, id));
        return Result.success();
    }

    /**
     * 重置密码
     */
    @CacheEvict(key = "'user:' + #id")
    @Override
    public Result resetPWD(String id, String oldPWD, String newPWD, String checkPWD) {
        if(newPWD.equals(checkPWD)){
            Loginuser loginuser = Loginuser.builder()
                    .id(id)
                    .build();
            loginuser = loginuserDao.selectById(loginuser);
            if (null==loginuser){
                return Result.fail().setMsg("用户不存在！");
            }
            if(loginuser.getPassword().equals(DigestUtils.md5DigestAsHex(oldPWD.getBytes()))){
                loginuser.setPassword(DigestUtils.md5DigestAsHex(newPWD.getBytes()));
                loginuserDao.updateById(loginuser);
                return Result.success();
            }else{
                //密码不对
                return Result.fail().setMsg("旧密码错误！");
            }
        }else{
            return Result.fail().setMsg("两次密码不一致！");
        }
    }

    /*
        新的方法
     */

    /**
     * 用户管理分页查询
     * @param params
     * @return
     */
    @Override
    public IPage<Loginuser> queryPage(Map<String, Object> params) {
        boolean b = userPositionService.judgeCurrUserAdminOrSupAdmin();
        if (!b){
            return new Page<>();
        }

        String agencyIdStr = (String)params.get("agencyId");
        if (StrUtil.isEmpty(agencyIdStr)){
            return new Page<>();
        }
        Integer agencyId = Integer.parseInt(agencyIdStr);
        List<Integer> agencyIdList = agencyService.getChildById(agencyId);
        params.put("agencyIdList", agencyIdList);

        Page page = PageUtils.getPage(params);
//        IPage<Loginuser> iPage = this.page(page, new LambdaQueryWrapper<Loginuser>()
//                .in(Loginuser::getAgencyId, agencyIdList)
//                .and(StringUtils.isNotEmpty(keyword), q -> {
//                    q.like(Loginuser::getUsername, keyword)
//                            .or()
//                            .like(Loginuser::getAccount, keyword);
//                })
//                .eq(Loginuser::getDel, false)
//                .orderByAsc(Loginuser::getCreateTime));
        IPage<Loginuser> iPage = loginuserDao.queryPage(page, params);

        List<Loginuser> records = iPage.getRecords();
        for (Loginuser loginuser : records) {
            Integer aId = loginuser.getAgencyId();

            String agencyName = agencyService.getWholeAgencyNameById(aId);
            loginuser.setAgencyName(agencyName);

            Agency agency = agencyService.getAgencyById(aId);
            loginuser.setAgencyPosition(agency.getPosition());

            loginuser.setPositionList(userPositionService.getListByUserId(loginuser.getId()));

            List<UserRole> userRoleList = userRoleService.list(new LambdaQueryWrapper<UserRole>()
                    .eq(UserRole::getUserId, loginuser.getId())
                    .orderByAsc(UserRole::getId));
            List<Integer> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(roleIdList)){
                List<Role> roleList = roleService.list(new LambdaQueryWrapper<Role>().in(Role::getId, roleIdList).orderByAsc(Role::getId));
                loginuser.setRoleList(roleList);
            }
        }
        return iPage;
    }

    @Override
    public IPage<Loginuser> queryPages(Map<String, Object> params) {
        boolean b = userPositionService.judgeCurrUserAdminOrSupAdmin();
        if (!b){
            return new Page<>();
        }

        String agencyIdStr = (String)params.get("agencyId");
        if (StrUtil.isEmpty(agencyIdStr)){
            return new Page<>();
        }
        Integer agencyId = Integer.parseInt(agencyIdStr);
        List<Integer> agencyIdList = agencyService.getChildById(agencyId);
        params.put("agencyIdList", agencyIdList);
        Page page = PageUtils.getPage(params);
        IPage<Loginuser> iPage = loginuserDao.queryPages(page, params);
        List<Loginuser> records = iPage.getRecords();
        for (Loginuser loginuser : records) {
            Integer aId = loginuser.getAgencyId();

            String agencyName = agencyService.getWholeAgencyNameById(aId);
            loginuser.setAgencyName(agencyName);
            List<UserRole> userRoleList = userRoleService.list(new LambdaQueryWrapper<UserRole>()
                    .eq(UserRole::getUserId, loginuser.getId())
                    .orderByAsc(UserRole::getId));
            List<Integer> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(roleIdList)){
                List<Role> roleList = roleService.list(new LambdaQueryWrapper<Role>().in(Role::getId, roleIdList).orderByAsc(Role::getId));
                loginuser.setRoleList(roleList);
            }
        }
        return iPage;
    }



}
