package com.beneway.common.common.utils.page;

import lombok.Data;

import java.io.Serializable;


@Data
public class PageQuery implements Serializable {

    private int page = 1;

    private int size = 10;

}
