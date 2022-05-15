package com.beneway.common.service.majorindicators;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.enums.DelMarkEnum;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.common.utils.page.PageUtils;
import com.beneway.common.dao.majorindicators.MajorIndicatorsDao;
import com.beneway.common.entity.choiceindicator.ChoiceIndicator;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.detailtarget.vo.DetailTargetVo;
import com.beneway.common.entity.detailtargetitem.DetailTargetItem;
import com.beneway.common.entity.detailtargetitem.fo.DetailTargetItemFo;
import com.beneway.common.entity.detailtargetitemoperator.DetailTargetItemOperator;
import com.beneway.common.entity.majorindicators.MajorIndicators;
import com.beneway.common.entity.majorindicators.fo.MajorIndicatorsFo;
import com.beneway.common.entity.majorindicators.fo.MajorIndicatorsQueryFo;
import com.beneway.common.entity.majorindicators.vo.MajorIndicatorsVo;
import com.beneway.common.service.choiceindicator.ChoiceIndicatorService;
import com.beneway.common.service.detailtarget.DetailTargetService;
import com.beneway.common.service.detailtargetitem.DetailTargetItemService;
import com.beneway.common.service.detailtargetitemoperator.DetailTargetItemOperatorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MajorIndicatorsServiceImpl extends ServiceImpl<MajorIndicatorsDao , MajorIndicators> implements MajorIndicatorsService {

    @Autowired
    private MajorIndicatorsDao majorIndicatorsDao;

    @Autowired
    private ChoiceIndicatorService choiceIndicatorService;

    @Autowired
    private DetailTargetService detailTargetService;

    @Autowired
    private DetailTargetItemOperatorService detailTargetItemOperatorService;

    @Autowired
    private DetailTargetItemService detailTargetItemService;


    @Override
    public Page<MajorIndicatorsVo> queryPage(MajorIndicatorsQueryFo majorIndicatorsFo) {
        Page page = PageUtils.getPage(majorIndicatorsFo);
        Page<MajorIndicatorsVo> iPage = majorIndicatorsDao.queryPage(page, majorIndicatorsFo);
        List<MajorIndicatorsVo> M = iPage.getRecords();
        for (MajorIndicatorsVo majorIndicatorsVo : M) {
            List<DetailTarget> detailTargetList = detailTargetService.list(
                    new QueryWrapper<DetailTarget>()
                            .lambda()
                            .eq(DetailTarget::getMajorIndicatorsId,majorIndicatorsVo.getId())
                            .eq(DetailTarget::getIsDel, DelMarkEnum.NO)
            );
            List<DetailTargetVo> detailTargetVoList = ClassUtil.toClassList(detailTargetList,DetailTargetVo.class);
            majorIndicatorsVo.setDetailTargetVoList(detailTargetVoList);
//            QueryWrapper<DetailTarget> qq = new QueryWrapper<DetailTarget>()
//                    .eq("major_indicators_id", majorIndicatorsVo.getId())
//                    .and(wrapper->wrapper.isNull("is_del").or().ne("is_del" , "1"));
//            List<DetailTarget> dtList = detailTargetService.list(qq);
//            List<DetailTargetVo> d = new ArrayList<>();
//            for (DetailTarget detailTarget : dtList) {
//                DetailTargetVo detailTargetVo = ClassUtil.toClass(detailTarget,DetailTargetVo.class);
//                d.add(detailTargetVo);
//            }
//            for (DetailTargetVo detailTargetVo : d) {
//                if(detailTargetVo.getType().equals("2")){
//                    List<ChoiceIndicator> chList = choiceIndicatorService.list(new QueryWrapper<ChoiceIndicator>().eq("detail_target_id", detailTargetVo.getId()));
//                    detailTargetVo.setChoiceIndicator(chList);
//                }
//                if(detailTargetVo.getType().equals("5")){
//                    List<DetailTargetItem> detailTargetItemList = detailTargetItemService.list(
//                            new QueryWrapper<DetailTargetItem>().lambda()
//                                    .eq(DetailTargetItem::getDetailTargetId,detailTargetVo.getId())
//                    );
//                    detailTargetVo.setDetailTargetItem(detailTargetItemList);
//                }
//            }
//            majorIndicatorsVo.setDetailTarget(d);
        }
        return iPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result add(MajorIndicatorsFo majorIndicatorsFo) {
        if(StringUtils.isNotEmpty(majorIndicatorsFo.getId())){
            updateById(majorIndicatorsFo);
        }else{
            majorIndicatorsFo.setCreateTime(new Date());
            majorIndicatorsFo.setCreateUser(LoginUserUtils.getLoginUserId());
            save(majorIndicatorsFo);
        }
        return Result.ok();
    }

    @Override
    @Transactional
    public Result addTarget(MajorIndicatorsFo majorIndicatorsFo) {
        MajorIndicators majorIndicators = ClassUtil.toClass(majorIndicatorsFo,MajorIndicators.class);
        //添加大指标
        majorIndicators.setCreateTime(new Date())
                .setCreateUser(LoginUserUtils.getLoginUserId());
        this.save(majorIndicators);

        //获取详细指标
        DetailTargetFo detailTargets = majorIndicatorsFo.getDetailTarget();
        //获取到公式
        List<DetailTargetItemFo> detailTargetItem = detailTargets.getDetailTargetItemFoList();
        //给详细指标赋相关值
        detailTargets.setCreateTime(new Date()).setCreateUser(LoginUserUtils.getLoginUserId()).setMajorIndicatorsId(majorIndicatorsFo.getId());
        //添加到详细指标
        detailTargetService.save(detailTargets);
        //如果公式不为空 指标类型是5
        if(!detailTargetItem.isEmpty() && detailTargets.getType().equals("5")){
            List<DetailTargetItem> detailTargetItems = new ArrayList<>();
            DetailTargetItemOperator dtio = new DetailTargetItemOperator();
            List<DetailTargetItemOperator> operators = new ArrayList<>();
            DetailTargetItem d = new DetailTargetItem();
            //for目的，先添加属性，然后运算符可以获取到属性的上一个和下一个id
            for (DetailTargetItemFo targetItem : detailTargetItem) {
                if(targetItem.getFlag()){
                     d = targetItem.setDetailTargetId(detailTargets.getId());
                    detailTargetItemService.save(d);
                    if(StringUtils.isBlank(dtio.getLastItemId())){
                        dtio.setLastItemId(d.getId());
                        continue;
                    }
                    if(StringUtils.isBlank(dtio.getNextItemId())){
                        dtio.setNextItemId(d.getId());
                    }
                    if(StringUtils.isNotBlank(dtio.getNextItemId()) && StringUtils.isNotBlank(dtio.getLastItemId())){
                        dtio.setDetailTargetId(detailTargets.getId());
                        operators.add(dtio);
                        dtio = new DetailTargetItemOperator();
                    }
                }else{
                    if(StringUtils.isBlank(dtio.getLastItemId())){
                        dtio.setLastItemId(d.getId());
                    }
                    dtio.setOperator(targetItem.getOperator());
                }
            }
            detailTargetItemOperatorService.saveBatch(operators);
        }
        //获取选项
        List<ChoiceIndicator> choiceIndicator = detailTargets.getChoiceIndicator();
        if(CollectionUtils.isEmpty(choiceIndicator)){
            return Result.ok();
        }
        //给选项赋值关联到详细指标
        choiceIndicator = choiceIndicator.stream().map(res -> {
            return res.setDetailTargetId(detailTargets.getId());
        }).collect(Collectors.toList());
        for (ChoiceIndicator indicator : choiceIndicator) {
            choiceIndicatorService.save(indicator);
        }
        return Result.ok();
    }

    @Override
    @Transactional
    public Result del(MajorIndicators majorIndicators) {
        this.removeById(majorIndicators.getId());
        return Result.ok();
    }

    @Override
    public Result getOneDetail(MajorIndicators majorIndicators) {
        majorIndicators = getById(majorIndicators.getId());
        MajorIndicatorsVo majorIndicatorsVo = ClassUtil.toClass(majorIndicators,MajorIndicatorsVo.class);
        return Result.ok(majorIndicatorsVo);
    }

    @Override
    public Result edit(MajorIndicators majorIndicators) {
        updateById(majorIndicators);
        return Result.ok();
    }


}
