package com.beneway.common.entity.choosable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Choosable {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String field;

    private String name;

    private String type;

}
