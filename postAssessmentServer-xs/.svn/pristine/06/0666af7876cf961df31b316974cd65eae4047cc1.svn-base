package com.beneway.common.dao.userlogin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beneway.common.entity.userlogin.UserloginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-06-05 17:32:55
 */
@Mapper
@Repository
public interface UserloginLogDao extends BaseMapper<UserloginLog> {

    IPage<UserloginLog> queryPage(Page page, @Param("param") Map<String, Object> params);
    UserloginLog selectEnd();

}
