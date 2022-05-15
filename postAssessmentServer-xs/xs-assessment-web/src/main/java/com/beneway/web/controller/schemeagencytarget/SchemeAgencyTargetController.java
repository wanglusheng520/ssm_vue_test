package com.beneway.web.controller.schemeagencytarget;


import com.beneway.common.service.schemeagencytarget.SchemeAgencyTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schemeAgencyTarget")
public class SchemeAgencyTargetController {

    @Autowired
    private SchemeAgencyTargetService schemeAgencyTargetService;



}
