package com.beneway.common.entity.detailtargetitemoperator;


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
public class DetailTargetItemOperator {

    private String id;

    private String lastItemId;

    private String nextItemId;

    private String detailTargetId;

    private String operator;

    private int tag;
}
