package com.beneway.common.service.contractinfo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.utils.base.PageUtils;
import com.beneway.common.dao.contractinfo.ContractInfoDao;
import com.beneway.common.entity.contractinfo.ContractInfo;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.agency.Agency;
import com.beneway.common.entity.userlogin.UserloginLog;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class ContractInfoServiceImpl extends ServiceImpl<ContractInfoDao, ContractInfo> implements ContractInfoService {

    @Autowired
    ContractInfoDao contractInfoDao;

    @Override
    public Result queryPage(Map<String, Object> params) {
        Page page = PageUtils.getPage(params);
        IPage<ContractInfo> iPage = contractInfoDao.queryPage(page, params);
        return PageUtils.getPageResult(iPage);
    }

    @Transactional
    public Result delete(ContractInfo contractInfo){
        contractInfo.setIsDelete(1);
        contractInfoDao.updateById(contractInfo);
        return Result.success();
    }
}
