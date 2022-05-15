package com.beneway.common.service.putrule;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.common.utils.page.PageUtils;
import com.beneway.common.dao.putrule.PutRuleDao;
import com.beneway.common.entity.putrule.PutRule;
import com.beneway.common.entity.putrule.fo.PutRuleFo;
import com.beneway.common.entity.putrule.fo.PutRuleItemFo;
import com.beneway.common.entity.putrule.fo.PutRuleQueryFo;
import com.beneway.common.entity.putrule.vo.PutRuleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class PutRuleServiceImpl extends ServiceImpl<PutRuleDao , PutRule> implements PutRuleService{

    @Autowired
    private PutRuleDao putRuleDao;



    @Override
    public Page<PutRuleVo> queryPage(PutRuleQueryFo putRuleQueryFo){
        Page param = PageUtils.getPage(putRuleQueryFo);
        Page<PutRuleVo> page = putRuleDao.queryPage(param,putRuleQueryFo);
        return page;
    }

    @Override
    @Transactional
    public Result add(PutRuleFo putRuleFo){
        putRuleFo.setCreateUser(LoginUserUtils.getLoginUserId())
                .setPid("0")
                .setCreateTime(new Date());
        this.save(putRuleFo);

        //添加具体指标项
        for(PutRuleItemFo p:putRuleFo.getPutRuleItemFoList()){
            p.setPutRuleId(putRuleFo.getId());
            log.error("{}",p.getFieldName());
        }
        return Result.ok();
    }



//    @Override
//    @Transactional
//    public Result add(PutRule putRule) {
//        List<PutRule> putRules = putRule.getPutRules();
//        putRule.setCreateUser(LoginUserUtils.getUserId()).setCreateTime(new Date()).setPid("0").setIsWork(true);
//        //添加父级
//        save(putRule);
//        //添加子级
//        List<PutRule> child = new ArrayList<>();
//        for (PutRule rule : putRules) {
//            rule.setPid(putRule.getId());
//            //不是多选的情况
//            if(StringUtils.isNotBlank(rule.getK()) && StringUtils.isNotBlank(rule.getV())){
//                if(StringUtils.isNotBlank(rule.getSymbol())){
//                    rule.setContent(rule.getK() + rule.getSymbol() + "'" + rule.getV() + "'");
//                }else{
//                    rule.setContent(rule.getK() + "=" + "'" + rule.getV() + "'");
//                }
//                child.add(rule);
//            }
//            //是多选的情况
//            if(StringUtils.isNotBlank(rule.getK()) && !CollectionUtils.isEmpty(rule.getVs())){
//                List<String> val = rule.getVs();
//                StringBuilder sb = new StringBuilder();
//                sb.append(rule.getK() + "  " +"in" +  "  "  + "(");
//                for (int i = 0 ; i < val.size() ; i++) {
//                    sb.append("\'"+ val.get(i) + "\'");
//                    if (i < val.size() - 1){
//                        sb.append(",");
//                    }
//                }
//                sb.append(")");
////                String join = String.join(",", val);
//                rule.setContent(sb.toString());
//                rule.setVal(val.toString());
//                child.add(rule);
//            }
//
//        }
//        saveBatch(child);
//        return Result.success();
//    }
//
//    @Override
//    public Result getOneDetail(PutRule putRule) {
//        putRule = getById(putRule.getId());
//        List<PutRule> list = list(new QueryWrapper<PutRule>().eq("pid", putRule.getId()));
//        if(!CollectionUtils.isEmpty(list)){
//            for (PutRule rule : list) {
//                String val = rule.getVal();
//                if(null == val) continue;
//                if(val.startsWith("[") && val.endsWith("]")){
//                    String s  = val.substring(1, val.length());
//                    String v = s.substring(0 , val.length()-2);
//                    ArrayList<String> strings = new ArrayList<>();
//                    for (String i : v.split(",")) {
//                        strings.add(i.trim());
//                    }
//                    rule.setVs(strings);
//                }
//            }
//            putRule.setPutRules(list);
//        }
//        return Result.success(putRule);
//    }
//
//    @Override
//    @Transactional
//    public Result del(PutRule putRule) {
//        putRuleDao.deleteById(putRule.getId());
//        //删除子规则
//        putRuleDao.delete(new QueryWrapper<PutRule>().eq("pid" , putRule.getId()));
//        return Result.success();
//    }
//
//    @Override
//    @Transactional
//    public Result edit(PutRule putRule) {
//        List<PutRule> putRules = putRule.getPutRules();
//        updateById(putRule);
//        //删除子规则
//        putRuleDao.delete(new QueryWrapper<PutRule>().eq("pid" , putRule.getId()));
//        if(CollectionUtils.isEmpty(putRules)){
//            return Result.success();
//        }
//        List<PutRule> child = new ArrayList<>();
//        for (PutRule rule : putRules) {
//            rule.setPid(putRule.getId());
//            //不是多选的情况
//            if(StringUtils.isNotBlank(rule.getK()) && StringUtils.isNotBlank(rule.getV())){
//                if(StringUtils.isNotBlank(rule.getSymbol())){
//                    rule.setContent(rule.getK() + rule.getSymbol() + "'" + rule.getV() + "'");
//                }else{
//                    rule.setContent(rule.getK() + "=" + "'" + rule.getV() + "'");
//                }
//                child.add(rule);
//            }
//            //是多选的情况
//            if(StringUtils.isNotBlank(rule.getK()) && !CollectionUtils.isEmpty(rule.getVs())){
//                List<String> val = rule.getVs();
//                StringBuilder sb = new StringBuilder();
//                sb.append(rule.getK() + "  " +"in" +  "  "  + "(");
//                for (int i = 0 ; i < val.size() ; i++) {
//                    sb.append("\'"+ val.get(i) + "\'");
//                    if (i < val.size() - 1){
//                        sb.append(",");
//                    }
//                }
//                sb.append(")");
////                String join = String.join(",", val);
//                rule.setContent(sb.toString());
//                rule.setVal(val.toString());
//                child.add(rule);
//            }
//        }
//        saveBatch(child);
//        return Result.success();
//    }
//
//    @Override
//    public Result isWork(PutRule putRule) {
//        updateById(putRule);
//        return Result.success();
//    }
}
