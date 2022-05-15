package com.beneway.web.controller.schemeagency;

import com.beneway.common.common.result.Result;
import com.beneway.common.entity.schemeagency.SchemeAgency;
import com.beneway.common.service.schemeagency.SchemeAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schemeAgency")
public class SchemeAgencyController{

    @Autowired
    private SchemeAgencyService schemeAgencyService;


}
