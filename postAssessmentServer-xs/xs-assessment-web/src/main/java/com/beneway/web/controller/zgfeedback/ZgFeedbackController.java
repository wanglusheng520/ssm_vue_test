package com.beneway.web.controller.zgfeedback;


import com.beneway.common.common.enums.StageEnum;
import com.beneway.common.common.result.Result;
import com.beneway.common.entity.zgfeedback.ZgFeedback;
import com.beneway.common.service.zgfeedback.ZgFeedbackService;
import com.beneway.web.common.annotation.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zgFeedback")
public class ZgFeedbackController {

    @Autowired
    private ZgFeedbackService zgFeedbackService;

    @Log(stage = StageEnum.F_ALREADY_RECTIFICATION)
    @PostMapping("/")
    public Result feedback(@RequestBody ZgFeedback zgFeedback){
        return zgFeedbackService.feedback(zgFeedback);
    }
}
