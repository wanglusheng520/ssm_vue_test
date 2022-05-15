package com.beneway.common.service.tzsjmes;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.dao.tzsjmes.TzSjMesDao;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.tzsjmes.TzSjMes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wj
 * @since 2022-03-19
 */
@Service
public class TzSjMesServiceImpl extends ServiceImpl<TzSjMesDao, TzSjMes> implements TzSjMesService {

    @Autowired
    private TzSjMesDao tzSjMesDao;

    @Override
    public Result sjMesByProjId(TzSjMes tzSjMes) {
        List<TzSjMes> list = list(new QueryWrapper<TzSjMes>().eq("proj_id", tzSjMes.getProjId()));
        return Result.success(list);
    }
}
