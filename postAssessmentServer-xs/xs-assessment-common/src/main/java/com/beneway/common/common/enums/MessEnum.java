package com.beneway.common.common.enums;

public enum MessEnum {

    ZDFA("A", "请填报");

    private String stage;

    private String content;

    MessEnum(String stage, String content) {
        this.content = content;
        this.stage = stage;
    }

    public String getStage() {
        return stage;
    }

    public String getContent() {
        return content;
    }
}
