package com.beneway.common.service.system.dict;


import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.system.Result;
import com.beneway.common.entity.system.dict.Dict;

import java.util.Map;

public interface DictService extends IService<Dict> {
     Result getDictByKey(Dict dict);
     Result getParent(Dict dict);
     Result getSon(Integer page, Integer limit, Dict dict);
     Result find(Dict dict);
     Result insert(Dict dict);
     Result update(Dict dict);
     Result delete(Dict dict);
     Result restore(Dict dict);
     Result getAllDelete(Integer page, Integer limit, Dict dict);
     Result realDel(Dict dict);

    Result getSon1(Map<String, Object> params);
}
