package com.beneway.web.controller.message;


import com.beneway.common.common.result.Result;
import com.beneway.common.entity.message.Message;
import com.beneway.common.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 获取我的信息 已读和未读
     * @return
     */
    @GetMapping("/")
    public Result myMessage(){
        return messageService.myMessage();
    }

    @PutMapping("/")
    public Result update(@RequestBody Message message){
        return messageService.edit(message);
    }

}
