package com.beneway.common.common.utils.sys_files;

import cn.hutool.core.util.StrUtil;
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
@Component
@ConfigurationProperties(prefix = "customize")
public class SysFilesUtils {

    /**
     * 文件存放地址
     */
    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @PostConstruct
    public void init(){
        File file = createMkdirs(filePath);
        this.filePath = file.getAbsolutePath() + "/";
        // 初始化其它目录
        FilePathEnum[] filePathEnums = FilePathEnum.values();
        for (FilePathEnum filePathEnum : filePathEnums) {
            createPath(filePathEnum.getPath());
        }
    }

    private File createMkdirs(String path){
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 创建文件目录
     * @param path
     */
    public void createPath(String path){
        createMkdirs(packStorPath(path));
    }

    /**
     * 获取路径
     * @param paths
     * @return
     */
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

    public String packStorPath(FilePathEnum filePathEnum, String... paths) {
        String[] strings = new String[1 + paths.length];
        strings[0] = filePathEnum.getPath();
        for (int i = 1; i <= paths.length; i++) {
            strings[i] = paths[i - 1];
        }
        return packStorPath(strings);
    }

}
