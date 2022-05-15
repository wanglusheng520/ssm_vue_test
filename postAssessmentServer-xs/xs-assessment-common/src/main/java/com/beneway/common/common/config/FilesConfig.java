package com.beneway.common.common.config;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @email 2434017367@qq.com
 * @author: zhy
 * @date: 2021/1/19
 * @time: 18:03
 */
@Data
@Component
@ConfigurationProperties(prefix = "customize.file")
public class FilesConfig {

    /**
     * 文件存放地址
     */
    private String filePath;

    private String files = "files";

    /**
     * 临时文件存放地址
     */
    private String interim = "interim";

    /**
     * 模板文件存放地址
     */
    private String template = "template";

    @PostConstruct
    public void init(){
        File file = createMkdirs(filePath);
        this.filePath = file.getAbsolutePath() + "/";
        // 初始化其它目录
        createMkdirs(packStorPath(this.files));
        createMkdirs(packStorPath(this.interim));
        createMkdirs(packStorPath(this.template));
    }

    private File createMkdirs(String path){
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public String packStorPath(String... paths){
        String p = this.filePath;
        for (String s : paths) {
            if (StrUtil.isEmpty(s)){
                continue;
            }
            p += (s + "/");
        }
        p = p.replaceAll("//", "/");
        if (p.lastIndexOf("/") == p.length() - 1){
            p = p.substring(0, p.length() - 1);
        }
        return p;
    }

    public String packPath(String... paths){
        String p = "";
        for (String s : paths) {
            if (StrUtil.isEmpty(s)){
                continue;
            }
            p += (s + "/");
        }
        p = p.replaceAll("//", "/");
        if (p.lastIndexOf("/") == p.length() - 1){
            p = p.substring(0, p.length() - 1);
        }
        return p;
    }

    public String getInterimFilePath(){
        return this.packStorPath(this.getInterim(), IdUtil.simpleUUID());
    }

    public boolean isWord(String suffix){
        return ".doc".equalsIgnoreCase(suffix) || ".docx".equalsIgnoreCase(suffix);
    }

}
