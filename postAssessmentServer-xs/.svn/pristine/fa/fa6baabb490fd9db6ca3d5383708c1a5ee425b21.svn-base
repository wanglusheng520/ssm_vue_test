package com.beneway.common.service.tzpromes;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.result.Result;
import com.beneway.common.dao.tzpromes.TzProMesDao;
import com.beneway.common.dao.tzsjmes.TzSjMesDao;
import com.beneway.common.entity.tzpromes.TzProMes;
import com.beneway.common.entity.tzpromes.TzProMesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TzProMesServiceImpl extends ServiceImpl<TzProMesDao , TzProMes> implements TzProMesService {

    @Autowired
    private TzSjMesDao tzSjMesDao;
    @Autowired
    private TzProMesDao tzProMesDao;

    @Override
    public Result detail(TzProMes tzProMes) {
        //TzProMes tz = getOne(new QueryWrapper<TzProMes>().eq("proj_id", tzProMes.getProjId()));
        TzProMesVo tzProMesVo = tzProMesDao.getOne(tzProMes.getProjId());
        return Result.ok(tzProMesVo);
    }
}
