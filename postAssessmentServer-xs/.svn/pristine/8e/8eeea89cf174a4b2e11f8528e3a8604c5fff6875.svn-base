package com.beneway.common.entity.normativedoc.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.beneway.common.entity.files.Files;
import com.beneway.common.entity.normativedoc.NormativeDoc;
import lombok.Data;

import java.util.List;

@Data
public class NormativeDocVo extends NormativeDoc {

    @TableField(exist = false)
    private String earlyWarning;

    @TableField(exist = false)
    private String isExpect;

    @TableField(exist = false)
    private List<Files> normativeFilesList;

    private String overdue;
}
