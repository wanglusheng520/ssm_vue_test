package com.beneway.common.dao.token;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 获取项目token
 */
@Mapper
@Repository
public interface TokenDao {

    String getToken(@Param("type") String type);

    void update(@Param("token") String token, @Param("type") String type);

    void insert(@Param("token") String token, @Param("type") String type);
}
