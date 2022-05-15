package com.beneway.common.system.service.sys_user.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.common.utils.page.PageUtils;
import com.beneway.common.system.dao.sys_user.SysUserDao;
import com.beneway.common.system.entity.sys_role.SysRole;
import com.beneway.common.system.entity.sys_tag.SysTag;
import com.beneway.common.system.entity.sys_tag.enums.SysTagTypeEnum;
import com.beneway.common.system.entity.sys_unit.SysUnit;
import com.beneway.common.system.entity.sys_unit.enums.SysUnitTypeEnum;
import com.beneway.common.system.entity.sys_user.SysUser;
import com.beneway.common.system.entity.sys_user.fo.SysUserFo;
import com.beneway.common.system.entity.sys_user.fo.SysUserQueryFo;
import com.beneway.common.system.entity.sys_user.vo.LoginUserInfo;
import com.beneway.common.system.entity.sys_user.vo.SysUserComVo;
import com.beneway.common.system.entity.sys_user.vo.SysUserVo;
import com.beneway.common.system.service.sys_menu.SysMenuPermissionService;
import com.beneway.common.system.service.sys_role.SysRoleService;
import com.beneway.common.system.service.sys_tag.SysTagMapService;
import com.beneway.common.system.service.sys_unit.SysUnitService;
import com.beneway.common.system.service.sys_user.SysUserRoleService;
import com.beneway.common.system.service.sys_user.SysUserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;


@EnableAspectJAutoProxy( proxyTargetClass = true , exposeProxy = true )
@CacheConfig(cacheNames = "sysUser")
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUnitService sysUnitService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysTagMapService sysTagMapService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysMenuPermissionService sysMenuPermissionService;

    private SysUserService getCurrThis(){
        SysUserService currentProxy = (SysUserService) AopContext.currentProxy();
        return currentProxy;
    }

    @Cacheable(key = "'list'")
    @Override
    public List<SysUser> list(){
        List<SysUser> list = getCurrThis().list(new LambdaQueryWrapper<SysUser>()
                .ne(SysUser::getUnitId, 0)
                .orderByAsc(SysUser::getCreateTime));
        return list;
    }

    /**
     * 密码加密
     * @param password
     * @return
     */
    @Override
    public String passEncr(String password) {
        if (StrUtil.isNotEmpty(password)){
            return DigestUtil.md5Hex(password);
        }else{
            return null;
        }
    }

    /**
     * 获取上级单位
     * @param
     * @return
     */

    private Integer getSupUnitId(int unitId){
        SysUnit sysUnit = sysUnitService.getById(unitId);
        if(sysUnit.getType().equals(SysUnitTypeEnum.UNIT)){
            return sysUnit.getId();
        }
        if(sysUnit.getType().equals(SysUnitTypeEnum.AREA)){
            return sysUnit.getId();
        }
        return sysUnit.getPid();
    }

    @CacheEvict(key = "'list'", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result add(SysUserFo sysUserFo) {
        sysUserFo.setSupUnitId(getSupUnitId(sysUserFo.getUnitId()));
        sysUserFo.setPassword(passEncr(sysUserFo.getPassword()));
        sysUserFo.setCreateTime(new Date());
        this.save(sysUserFo);

        List<Integer> roleList = sysUserFo.getRoleList();
        sysUserRoleService.addUserRole(sysUserFo.getId(), roleList);

        List<Integer> tagList = sysUserFo.getTagList();
        sysTagMapService.addTagMap(sysUserFo.getId(), tagList, SysTagTypeEnum.USER);

        return Result.ok();
    }

    @CacheEvict(key = "'list'", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result edit(SysUserFo sysUserFo) {
        sysUserFo.setSupUnitId(getSupUnitId(sysUserFo.getUnitId()));
        sysUserFo.setPassword(passEncr(sysUserFo.getPassword()));
        this.updateById(sysUserFo);

        List<Integer> roleList = sysUserFo.getRoleList();
        sysUserRoleService.updateUserRole(sysUserFo.getId(), roleList);

        List<Integer> tagList = sysUserFo.getTagList();
        sysTagMapService.updateTagMap(sysUserFo.getId(), tagList, SysTagTypeEnum.USER);


        return Result.ok();
    }

    @CacheEvict(key = "'list'", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result del(String userId) {
        this.removeById(userId);
        sysUserRoleService.removeUserRole(userId);
        sysTagMapService.removeTagMap(userId, SysTagTypeEnum.USER);
        return Result.ok();
    }

    @Override
    public LoginUserInfo getLoginUserInfo(String userId) {
        SysUser sysUser = this.getById(userId);
        LoginUserInfo loginUserInfo = ClassUtil.toClass(sysUser, LoginUserInfo.class);
        List<SysUnit> unitList = sysUnitService.getInList(loginUserInfo.getUnitId());
        loginUserInfo.setUnitList(unitList);
        return loginUserInfo;
    }

    @Override
    public boolean isPermission(String userId, String permission) {
        List<Integer> menuIdList = sysRoleService.getMenuIdListByUserId(userId);
        List<String> permissionList = sysMenuPermissionService.getPermissionByMenuIdList(menuIdList);
        return permissionList.contains(permission);
    }

    @Override
    public Page<SysUserVo> queryPage(SysUserQueryFo sysUserPageQueryFo) {
        Page page = PageUtils.getPage(sysUserPageQueryFo);

        Integer unitId = sysUserPageQueryFo.getUnitId();
        Set<Integer> unitIdList = sysUnitService.getIdListByPid(unitId);
        sysUserPageQueryFo.setUnitIdSet(unitIdList);

        Page<SysUserVo> voPage = sysUserDao.queryPage(page, sysUserPageQueryFo);
        List<SysUserVo> records = voPage.getRecords();
        for (SysUserVo record : records) {
            packVo(record);
        }

        return voPage;
    }

    @Override
    public SysUserVo getUserInfo(String userId) {
        SysUser sysUser = this.getById(userId);
        return packVo(sysUser);
    }

    private SysUserVo packVo(SysUser sysUser){
        SysUserVo sysUserVo = ClassUtil.toClass(sysUser, SysUserVo.class);
        return packVo(sysUserVo);
    }

    private SysUserVo packVo(SysUserVo sysUserVo) {
        List<SysUnit> unitList = sysUnitService.getInList(sysUserVo.getUnitId());
        sysUserVo.setUnitList(unitList);
        List<SysRole> roleList = sysRoleService.getListByUserId(sysUserVo.getId());
        sysUserVo.setRoleList(roleList);
        List<SysTag> tagList = sysTagMapService.getByAssId(sysUserVo.getId(), SysTagTypeEnum.USER);
        sysUserVo.setTagList(tagList);

        return sysUserVo;
    }

    /**
     * 获取前端统一组件的用户信息
     * @return
     */
    @Override
    public SysUserComVo getComUserInfo(String userId) {
        SysUser sysUser = this.getOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getUsername)
                .eq(SysUser::getId, userId));
        SysUserComVo sysUserComVo = ClassUtil.toClass(sysUser, SysUserComVo.class);
        return sysUserComVo;
    }

}
