package com.beneway.common.entity.system.agency;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/12/3
 * @time: 10:48
 */
@Data
public class AgencyGroupVo implements Serializable {

    private String agencyName;

    private List<Agency> children;

}
