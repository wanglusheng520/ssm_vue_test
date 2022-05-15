package com.beneway.common.dao.files;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.entity.files.Files;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FilesDao extends BaseMapper<Files> {
    @Select("SELECT * FROM files WHERE id = #{uuid};")
    Files find(@Param("uuid") String uuid);

    @Select("SELECT url FROM server;")
    String getServerUrl();

    List<Files> getFileByIdList(@Param("idList") List<String> idList);
}
