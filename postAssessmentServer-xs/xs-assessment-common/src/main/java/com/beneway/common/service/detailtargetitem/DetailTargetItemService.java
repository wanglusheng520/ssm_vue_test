package com.beneway.common.service.detailtargetitem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.detailtargetitem.DetailTargetItem;
import com.beneway.common.entity.detailtargetitem.fo.DetailTargetItemFo;

import java.util.List;

public interface DetailTargetItemService extends IService<DetailTargetItem> {

    void saveList(List<DetailTargetItemFo> detailTargetItemFoList, DetailTargetFo detailTargetFo);
}
