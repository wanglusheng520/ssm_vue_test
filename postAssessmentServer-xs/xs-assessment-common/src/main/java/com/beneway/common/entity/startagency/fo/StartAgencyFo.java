package com.beneway.common.entity.startagency.fo;

import com.beneway.common.entity.startagency.StartAgency;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class StartAgencyFo extends StartAgency {

    private List<String> investTypeList;

    private List<String> projectTypeList;

    public StartAgency setInvestType(String investType){
        super.setInvestType(investType);
        if(StringUtils.isNotEmpty(investType)){
            this.investTypeList = Arrays.asList(investType.split(","));
        }else{
            this.investTypeList = new ArrayList<>();
        }
        return null;
    }

    public StartAgency setProjectType(String projectType){
        super.setProjectType(projectType);
        if(StringUtils.isNotEmpty(projectType)){
            this.projectTypeList = Arrays.asList(projectType.split(","));
        }else{
            this.projectTypeList = new ArrayList<>();
        }
        return null;
    }

}
