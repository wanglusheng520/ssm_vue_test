package com.beneway.common.service.detailtarget;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.ClassUtil;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.common.utils.page.PageUtils;
import com.beneway.common.dao.choiceindicator.ChoiceIndicatorDao;
import com.beneway.common.dao.detailtarget.DetailTargetDao;
import com.beneway.common.entity.choiceindicator.ChoiceIndicator;
import com.beneway.common.entity.choosable.Choosable;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.detailtarget.enums.TypeEnum;
import com.beneway.common.entity.detailtarget.fo.DetailTargetFo;
import com.beneway.common.entity.detailtarget.fo.DetailTargetQueryFo;
import com.beneway.common.entity.detailtarget.vo.DetailTargetVo;
import com.beneway.common.entity.detailtargetitem.DetailTargetItem;
import com.beneway.common.entity.detailtargetitem.fo.DetailTargetItemFo;
import com.beneway.common.entity.detailtargetitem.vo.DetailTargetItemVo;
import com.beneway.common.entity.detailtargetitemoperator.DetailTargetItemOperator;
import com.beneway.common.entity.majorindicators.fo.MajorIndicatorsQueryFo;
import com.beneway.common.entity.majorindicators.vo.MajorIndicatorsVo;
import com.beneway.common.entity.schemeagencytarget.vo.SchemeAgencyTargetVo;
import com.beneway.common.service.choiceindicator.ChoiceIndicatorService;
import com.beneway.common.service.choosable.ChoosableService;
import com.beneway.common.service.detailtargetitem.DetailTargetItemService;
import com.beneway.common.service.detailtargetitemoperator.DetailTargetItemOperatorService;
import com.beneway.common.service.schemeagency.SchemeAgencyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DetailTargetServiceImpl extends ServiceImpl<DetailTargetDao , DetailTarget> implements DetailTargetService {

    @Autowired
    private DetailTargetDao detailTargetDao;

    @Autowired
    private ChoiceIndicatorService choiceIndicatorService;

    @Autowired
    private DetailTargetItemOperatorService detailTargetItemOperatorService;

    @Autowired
    private DetailTargetItemService detailTargetItemService;

    private DetailTargetService getCurrThis(){
        DetailTargetService currentProxy = (DetailTargetService) AopContext.currentProxy();
        return currentProxy;
    }


    @Override
    @Transactional
    public Result del(DetailTarget detailTarget) {
        this.removeById(detailTarget.getId());
        return Result.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result add(DetailTargetFo detailTargetFo) {
        detailTargetFo.setCreateUser(LoginUserUtils.getLoginUserId())
                .setCreateTime(new Date());
        this.save(detailTargetFo);
        //保存detailTargetItem
        detailTargetItemService.saveList(detailTargetFo.getDetailTargetItemFoList(),detailTargetFo);
        return Result.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addDetail(DetailTargetFo detailTargetFo) {
        List<DetailTargetItemFo> detailTargetItemFoList = detailTargetFo.getDetailTargetItemFoList();
        save(detailTargetFo);
        if(!detailTargetItemFoList.isEmpty() && detailTargetFo.getType().equals("5")){
            List<DetailTargetItem> detailTargetItems = new ArrayList<>();
            DetailTargetItemOperator dtio = new DetailTargetItemOperator();
            List<DetailTargetItemOperator> operators = new ArrayList<>();
            DetailTargetItem d = new DetailTargetItem();
            int tag = 0;
            //for目的，先添加属性，然后运算符可以获取到属性的上一个和下一个id
            for (DetailTargetItemFo targetItem : detailTargetItemFoList) {
                if(targetItem.getFlag()){
                    d = targetItem.setDetailTargetId(detailTargetFo.getId());
                    detailTargetItemService.save(d);
                    if(StringUtils.isBlank(dtio.getLastItemId())){
                        dtio.setLastItemId(d.getId());
                        continue;
                    }
                    if(StringUtils.isBlank(dtio.getNextItemId())){
                        dtio.setNextItemId(d.getId());
                    }
                    if(StringUtils.isNotBlank(dtio.getNextItemId()) && StringUtils.isNotBlank(dtio.getLastItemId())){
                        dtio.setDetailTargetId(detailTargetFo.getId());
                        dtio.setTag(tag);
                        operators.add(dtio);
                        dtio = new DetailTargetItemOperator();
                    }
                }else{
                    tag++;
                    if(StringUtils.isBlank(dtio.getLastItemId())){
                        dtio.setLastItemId(d.getId());
                    }
                    dtio.setOperator(targetItem.getOperator());
                }
            }
            detailTargetItemOperatorService.saveBatch(operators);
        }
        List<ChoiceIndicator> choiceIndicator = detailTargetFo.getChoiceIndicator();
        if(CollectionUtils.isEmpty(choiceIndicator)){
            return Result.ok();
        }
        choiceIndicator = choiceIndicator.stream().map(res -> {
            return res.setDetailTargetId(detailTargetFo.getId());
        }).collect(Collectors.toList());
        choiceIndicatorService.saveBatch(choiceIndicator);
        return Result.ok();
    }

    @Override
    public Result selectDetailTargetForIds(DetailTargetFo detailTargetFo) {
        if(StringUtils.isBlank(detailTargetFo.getIds())){
            return Result.ok();
        }
        String[] split = detailTargetFo.getIds().split(",");
        List<String> ids = Arrays.asList(split);
        List<DetailTarget> res = list(new QueryWrapper<DetailTarget>()
                .in("id", ids));
        return Result.ok(res);
    }

    @Override
    public Result allTarget() {
        List<DetailTarget> list = list(new QueryWrapper<DetailTarget>().select("id", "detail_target_name"));
        return Result.ok(list);
    }


    @Override
    public Page<DetailTargetVo> queryPage(DetailTargetQueryFo detailTargetQueryFo) {
        Page page = PageUtils.getPage(detailTargetQueryFo);
        Page<DetailTargetVo> iPage = detailTargetDao.queryPage(page, detailTargetQueryFo);
        DetailTargetItem detailTargetItem = null;
        for(DetailTargetVo detailTargetVo:iPage.getRecords()){
            getDetailTargetAss(detailTargetVo);
        }
        return iPage;
    }

    @Override
    public List<DetailTargetVo> getTargetTB(List<SchemeAgencyTargetVo> schemeAgencyTargetVoList) {
        Map<String, Object> map = new HashMap<>();
        DetailTargetVo detailTargetVo = null;

        for(SchemeAgencyTargetVo schemeAgencyTargetVo:schemeAgencyTargetVoList){
            if (!map.containsKey(schemeAgencyTargetVo.getTargetId())) {
                detailTargetVo = ClassUtil.toClass(this.getById(schemeAgencyTargetVo.getTargetId()), DetailTargetVo.class);
            } else {
                detailTargetVo = ClassUtil.toClass(map.get(schemeAgencyTargetVo.getTargetId()), DetailTargetVo.class);
            }
            detailTargetVo.setSchemeAgencyTargetVo(schemeAgencyTargetVo);
            getCurrThis().getDetailTargetAss(detailTargetVo);
            map.put(schemeAgencyTargetVo.getTargetId(), detailTargetVo);
        }

        List<DetailTargetVo> detailTargetVoList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            detailTargetVoList.add(ClassUtil.toClass(entry.getValue(), DetailTargetVo.class));
        }
        detailTargetVoList = detailTargetVoList.stream().sorted(Comparator.comparing(DetailTargetVo::getCreateTime)).collect(Collectors.toList());
        return detailTargetVoList;
    }

    /**
     * 获取指标相关下级属性
     */
    @Override
    public void getDetailTargetAss(DetailTargetVo detailTargetVo){
        List<DetailTargetItemVo> detailTargetItemVoList = new ArrayList<>();
        switch (detailTargetVo.getType()) {
            case "2":
                List<ChoiceIndicator> list = choiceIndicatorService.list(
                        new QueryWrapper<ChoiceIndicator>()
                                .lambda()
                                .eq(ChoiceIndicator::getDetailTargetId, detailTargetVo.getId())
                );
                detailTargetVo.setChoiceIndicator(list);
                break;
            case "5":
                if(null != detailTargetVo.getSchemeAgencyTargetVo()){
                    DetailTargetItem detailTargetItem = detailTargetItemService.getById(detailTargetVo.getSchemeAgencyTargetVo().getItemId());
                    detailTargetItemVoList = detailTargetVo.getDetailTargetItemVoList();
                    if (null == detailTargetItemVoList) {
                        detailTargetItemVoList = new ArrayList<>();
                    }
                    detailTargetItemVoList.add(ClassUtil.toClass(detailTargetItem,DetailTargetItemVo.class));
                }else{
                    detailTargetItemVoList = ClassUtil.toClassList(detailTargetItemService.list(
                            new QueryWrapper<DetailTargetItem>()
                                    .lambda()
                                    .eq(DetailTargetItem::getDetailTargetId,detailTargetVo.getId())
                    ),DetailTargetItemVo.class);
                }
                detailTargetVo.setDetailTargetItemVoList(detailTargetItemVoList);
                break;
        }
    }
}
