package com.beneway.tasks.common.job;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beneway.common.entity.normativedoc.NormativeDoc;
import com.beneway.common.entity.tzprojectearliermes.TzProjectEarlierMes;
import com.beneway.common.service.tzprojectearliermes.TzProjectEarlierMesService;
import com.beneway.tasks.common.config.ScheduledOfTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.*;

public class UpdateDate implements ScheduledOfTask {

    @Autowired
    private TzProjectEarlierMesService tzProjectEarlierMesService;

    @Override
    public void execute() {
        try {
        ExecutorService executorService = new ThreadPoolExecutor(4, 10, 1L,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        executorService.submit(() -> {
            proBeforeMes();
        });
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void proBeforeMes(){
        Integer limit = 10;
        Integer page = 1;
        JSONObject jsonObject = new JSONObject();
        while (true){
            jsonObject.put("limit" , String.valueOf(limit));
            jsonObject.put("page" , String.valueOf(page));
            String url = "https://meshproxy.xiangshan.gov.cn/bgmesh/fiddler/3999/service/f22ac58a-c30f-43c4-9154-decb21d7c592/0/";
            String res = HttpRequest.post(url)
                    .header("token", "XSXXZJCHPGGLXT399979143e70285b-f087-425e-bf4a-24bd64dbfac0")
                    .timeout(20000)//超时，毫秒
                    .body(jsonObject.toJSONString()).execute().body();
            if(StringUtils.isBlank(res)){
                return;
            }
            JSONObject object = JSONObject.parseObject(res);
            if(object == null){
                return;
            }
            JSONArray o = (JSONArray)object.get("data");
            for(Object i : o){
                TzProjectEarlierMes t = (TzProjectEarlierMes)i;
                tzProjectEarlierMesService.save(t);
            }
            ++page;
        }
    }

    public void tzProMes(){
        Integer limit = 10;
        Integer page = 1;
        JSONObject jsonObject = new JSONObject();
        while (true){
            jsonObject.put("limit" , String.valueOf(limit));
            jsonObject.put("page" , String.valueOf(page));
            String url = "https://meshproxy.xiangshan.gov.cn/bgmesh/fiddler/4000/service/2856a506-9ec2-4d13-b6e1-2437b2b963bd/0/";
            String res = HttpRequest.post(url)
                    .header("token", "XSXXZJCHPGGLXT4000791535da3b97-9fe5-4a2e-b9d0-e01e80a98194")
                    .timeout(20000)//超时，毫秒
                    .body(jsonObject.toJSONString()).execute().body();
            if(StringUtils.isBlank(res)){
                return;
            }
            JSONObject object = JSONObject.parseObject(res);
            if(object == null){
                return;
            }
            JSONArray o = (JSONArray)object.get("data");
            for(Object i : o){
                TzProjectEarlierMes t = (TzProjectEarlierMes)i;
                tzProjectEarlierMesService.save(t);
            }
            ++page;
        }
    }


}
