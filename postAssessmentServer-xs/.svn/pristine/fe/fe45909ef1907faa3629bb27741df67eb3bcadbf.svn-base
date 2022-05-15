package com.beneway.common.service.detailtargetitem;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.dao.detailtargetitem.DetailTargetItemDao;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.detailtargetitem.DetailTargetItem;
import com.beneway.common.entity.detailtargetitem.fo.DetailTargetItemFo;
import com.beneway.common.entity.detailtargetitemoperator.DetailTargetItemOperator;
import com.beneway.common.service.detailtarget.DetailTargetService;
import com.beneway.common.service.detailtargetitemoperator.DetailTargetItemOperatorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DetailTargetItemServiceImpl extends ServiceImpl<DetailTargetItemDao , DetailTargetItem> implements DetailTargetItemService{

    @Autowired
    DetailTargetItemOperatorService detailTargetItemOperatorService;

    private DetailTargetItemService getCurrThis(){
        DetailTargetItemService currentProxy = (DetailTargetItemService) AopContext.currentProxy();
        return currentProxy;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveList(List<DetailTargetItemFo> detailTargetItemFoList, DetailTargetFo detailTargetFo) {
        DetailTargetItemOperator detailTargetItemOperator = new DetailTargetItemOperator();
        List<DetailTargetItemOperator> operators = new ArrayList<>();
        int tag = 0;
        for(DetailTargetItemFo detailTargetItemFo:detailTargetItemFoList){
            if(detailTargetItemFo.getFlag()){
                detailTargetItemFo.setDetailTargetId(detailTargetFo.getId());
                getCurrThis().save(detailTargetItemFo);

                if(StringUtils.isBlank(detailTargetItemOperator.getLastItemId())){
                    detailTargetItemOperator.setLastItemId(detailTargetItemFo.getId());
                    continue;
                }
                if(StringUtils.isBlank(detailTargetItemOperator.getNextItemId())){
                    detailTargetItemOperator.setNextItemId(detailTargetItemFo.getId());
                }
                if(StringUtils.isNotBlank(detailTargetItemOperator.getNextItemId()) && StringUtils.isNotBlank(detailTargetItemOperator.getLastItemId())){
                    detailTargetItemOperator.setDetailTargetId(detailTargetFo.getId());
                    detailTargetItemOperator.setTag(tag);
                    operators.add(detailTargetItemOperator);
                    detailTargetItemOperator = new DetailTargetItemOperator();
                }
            }else{
                tag++;
                if(StringUtils.isBlank(detailTargetItemOperator.getLastItemId())){
                    detailTargetItemOperator.setLastItemId(detailTargetItemFo.getId());
                }
                detailTargetItemOperator.setOperator(detailTargetItemFo.getOperator());
            }
        }
        detailTargetItemOperatorService.saveBatch(operators);
    }
}
