package com.beneway.common.entity.normativedoc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.beneway.common.base.entity.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NormativeDoc extends BaseBean {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String uuid;
    /**
     * 文号
     */
    private String officialDocumentName;
    /**
     * 文种
     */
    private String officialDocumentId;
    /**
     * 拟稿日期
     */
    private String draftDate;
    /**
     * 拟稿人
     */
    private String draftAuthor;
    /**
     * 拟稿人id
     */
    private String draftAuthorId;
    /**
     * 图片数量
     */
    private int jpgNum;
    /**
     * 标题
     */
    private String title;
    /**
     * 状态
     */
    private String normativeStatus;

    /**
     * 研判等级
     */
    private String normativeGrade;

    /**
     * 删除标记
     */
    private int isDelete;

    /**
     *
     */
    private String normativeStage;

    /**
     * zip文件id
     */
    private String normativeFilesZip;
    /**
     * 解压后文件ids
     */
    private String normativeFiles;


}
