package com.beneway.common.system.dao.sys_files;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beneway.common.system.entity.sys_files.SysFiles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysFilesDao extends BaseMapper<SysFiles> {

    @Select("SELECT * FROM files WHERE uuid = #{id};")
    SysFiles find(@Param("id") String id);

}
