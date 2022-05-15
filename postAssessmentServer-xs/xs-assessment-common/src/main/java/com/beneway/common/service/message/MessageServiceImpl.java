package com.beneway.common.service.message;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.jwt.LoginUserUtils;
import com.beneway.common.dao.message.MessageDao;
import com.beneway.common.entity.majorproject.MajorProject;
import com.beneway.common.entity.message.Message;
import com.beneway.common.entity.normativedoc.NormativeDoc;
import com.beneway.common.service.majorproject.MajorProjectService;
import com.beneway.common.service.normativedoc.NormativeDocService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao , Message> implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MajorProjectService majorProjectService;

    @Autowired
    private NormativeDocService normativeDocService;

    @Override
    public Result myMessage() {
        Integer myAgency = LoginUserUtils.getSupUnitId();
        //获取和我相关的信息
        List<Message> list = lambdaQuery().eq(Message::getUnitId, myAgency).list();
        if(list.isEmpty()){
            return Result.ok();
        }
        //获取项目名称
        for (Message message : list) {
            switch (message.getAssType()){
                case "P":
                    MajorProject majorProject = majorProjectService.getOne(new QueryWrapper<MajorProject>()
                            .eq("id" , message.getAssId()).select("project_name"));
                    message.setAssName(majorProject.getProjectName());
                    break;
                case "N":
                    NormativeDoc normativeDoc = normativeDocService.getOne(new QueryWrapper<NormativeDoc>()
                            .eq("id" , message.getAssId()).select("official_document_name"));
                    message.setAssName(normativeDoc.getOfficialDocumentName());
                    break;
            }
        }
        List<Message> mess1 = list.stream().filter(res -> res.getIsRead() == 0).sorted(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return Long.compare(o2.getCreateTime().getTime() , o1.getCreateTime().getTime());
            }
        }).collect(Collectors.toList());
        List<Message> mess2 = list.stream().filter(res -> res.getIsRead() == 1).sorted(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return Long.compare(o2.getCreateTime().getTime(),o1.getCreateTime().getTime());
            }
        }).collect(Collectors.toList());
        return Result.ok().put("mess1" , mess1).put("mess2" , mess2);
    }

    @Override
    public Result edit(Message message) {
        updateById(message);
        return Result.ok();
    }
}
