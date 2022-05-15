package com.beneway.tasks.common.job;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.dfa.WordTree;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beneway.common.common.utils.base.Tools;
import com.beneway.common.common.utils.base.UuidUtil;
import com.beneway.common.common.utils.files.FileUtil;
import com.beneway.common.common.utils.sys_files.FilePathEnum;
import com.beneway.common.common.utils.sys_files.SysFilesUtils;
import com.beneway.common.entity.files.Files;
import com.beneway.common.entity.normativedoc.NormativeDoc;
import com.beneway.common.service.files.FilesService;
import com.beneway.common.service.normativedoc.NormativeDocService;
import com.beneway.common.system.service.sys_files.SysFilesService;
import com.beneway.tasks.common.config.ScheduledOfTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 获取规范性文件数据-定时器
 *
 */

@Slf4j
@Component
public class NormativeDocJob implements ScheduledOfTask {

    @Autowired
    private NormativeDocService normativeDocService;
    @Autowired
    private SysFilesService sysFilesService;
    @Autowired
    private SysFilesUtils sysFilesUtils;

    @Transactional
    @Override
    public void execute(){
        //链式构建请求
        String url = "http://10.19.179.51/fileoutUnit/getFileoutUnitList?userSysName=xfws";
        String result = HttpRequest.post(url)
                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
                .timeout(20000)//超时，毫秒
                .execute().body();
        JSONArray object = JSONArray.parseArray(result);
        String a[] = {"通报","批复","表扬","表彰","下达","公告","指示","征收","公布","调查","普查","分工","预算"};
        String b[] = {"成立","公布","工作安排","提案","结果","任务","责任分解","创建","工作要点","批复","启用","通报","职责","分工","考核评价","奖励评价","印章","名单","领导小组","管理员","预算"};
        WordTree tree = new WordTree();
        for(int n=0;n<a.length;n++){
            tree.addWord(a[n]);
        }
        for(int m=0;m<b.length;m++){
            tree.addWord(b[m]);
        }
        int j = 0;
        String today= DateUtil.today();
        for(Object o : object){
            JSONObject obj = (JSONObject) o;
            String dateStr = obj.getString("draftDate");
            if(dateStr.equals(today)){
                String officialDocumentId = obj.getString("officialDocumentId");
                if("象政办发".equals(officialDocumentId) || "象政发".equals(officialDocumentId)){
                    List<String> matchAll = tree.matchAll(obj.getString("title"), -1, true, true);
                    if(matchAll.isEmpty()){
                        String urlDetail = "http://10.19.179.51/fileoutUnit/getFileoutUnitDetail?uuid="+obj.getString("uuid");
                        String detail = HttpRequest.get(urlDetail)
                                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
                                .timeout(20000)//超时，毫秒
                                .execute().body();
                        NormativeDoc normativeDoc = JSONObject.parseObject(detail,NormativeDoc.class);
                        String normativeFilesZip = getFilesZip(normativeDoc.getUuid());
                        normativeDoc.setNormativeFilesZip(normativeFilesZip);
                        String normativeFiles = getFiles(normativeFilesZip);
                        normativeDoc.setNormativeFiles(normativeFiles);
                        normativeDocService.save(normativeDoc);
                    }
                }
            }
        }
    }

    public String getFilesZip(String uuid){
        //uuid = "96a7dc1d-8139-4ae0-b402-a4adefdb3c83";
        String url = "http://10.19.179.51/fileoutUnit/getFileoutUnitFile?uuid="+uuid;
        String result = HttpRequest.get(url)
                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
                .timeout(20000)//超时，毫秒
                .execute().body();
        File file = null;
        String id = "";
        try {
            id = UuidUtil.get32UUID();
            String res = URLDecoder.decode(result, CharsetUtil.CHARSET_GBK);
            byte[] bytes = Base64.decode(res);
            String path = System.getProperty("user.dir")+"/files/zip/"+ id+".zip";
            file = new File(path);
            if (!file.exists()){
                file.createNewFile();
            }
            OutputStream out = new FileOutputStream(path);
            out.write(bytes);
            out.flush();
            out.close();
            return id;
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public String getFiles(String normativeFilesZip){
        //normativeFilesZip = "b4272e04b3694165a6d85d1ac0b7f170";
        StringBuffer normativeFiles = new StringBuffer();
        try{
            String zipFilePath = System.getProperty("user.dir")+"/files/zip/"+normativeFilesZip+".zip";
            File zipFile = new File(zipFilePath);
            String unzipFilePath = System.getProperty("user.dir")+"/files/zip/"+normativeFilesZip+"/";
            File unzipFileDir = new File(unzipFilePath);
            if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
                unzipFileDir.mkdirs();
            }
            ZipEntry entry = null;
            String entryFilePath = null, entryDirPath = null;
            File entryFile = null, entryDir = null;
            int index = 0, count = 0;
            byte[] buffer = new byte[1024];
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            ZipFile zip = new ZipFile(zipFile);
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zip.entries();
            while(entries.hasMoreElements()) {
                entry = entries.nextElement();
                entryFilePath = unzipFileDir + File.separator + entry.getName();
                index = entryFilePath.lastIndexOf(File.separator);
                if (index != -1) {
                    entryDirPath = entryFilePath.substring(0, index);
                }
                else {
                    entryDirPath = "";
                }
                entryDir = new File(entryDirPath);
                if (!entryDir.exists() || !entryDir.isDirectory()) {
                    entryDir.mkdirs();
                }
                entryFile = new File(entryFilePath);
                //写入文件
                bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                bis = new BufferedInputStream(zip.getInputStream(entry));
                try{
                    String uuid = UuidUtil.get32UUID();
                    String path  = FilePathEnum.FILES.getPath();
                    String filePath = sysFilesUtils.packStorPath(path, uuid);
                    FileUtil.inputFile(bis, filePath);
                    if(".pdf".equals(FileUtil.getFilesSuffix(entry.getName()))){
                        sysFilesService.saveFile(uuid, path, FileUtil.getFilesName(entry.getName()), FileUtil.getFilesSuffix(entry.getName()));
                        normativeFiles.append(uuid).append(",");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                bos.flush();
                bos.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return normativeFiles.toString();
    }


}
