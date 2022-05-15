package com.beneway.common.entity.satanswer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.beneway.common.entity.detailtarget.DetailTarget;
import com.beneway.common.entity.detailtargetitem.DetailTargetItem;
import com.beneway.common.entity.files.Files;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SatAnswer {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String answerRecordId;

    private String files;

    private String choice;

    private String content;

    private String satId;


    /**
     * 佐证材料
     */
    private String supportingMaterials;

    /**
     * 用户填写的数据
     */
    private String useMes;
    private String proMes;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
