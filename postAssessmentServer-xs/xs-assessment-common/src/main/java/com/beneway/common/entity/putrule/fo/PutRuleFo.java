package com.beneway.common.entity.putrule.fo;

import com.beneway.common.entity.putrule.PutRule;
import lombok.Data;

import java.util.List;

@Data
public class PutRuleFo extends PutRule {


    List<PutRuleItemFo> putRuleItemFoList;
}
