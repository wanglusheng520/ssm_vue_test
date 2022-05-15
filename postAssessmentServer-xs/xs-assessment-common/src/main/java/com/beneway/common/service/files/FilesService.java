package com.beneway.common.service.files;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beneway.common.entity.files.Files;
import com.beneway.common.entity.system.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public interface FilesService extends IService<Files> {

    Files find(String uuid);

    void insert(Files files);

    void insertInterim(String id, String fileName, String suffix);

    String getServerUrl();

    ArrayList<String> getFileName(String[] ids);

    Result delete(String fileId);

    List<Files> getFileByIdList(List<String> idList);

    List<Files> getFileByIds(String ids);

    String getFileRealAddress(String uuid);

    String getFileRealAddress(Files files);

    List<String> getFileRealAddressList(List<Files> filesList);

    File getFileById(String uuid);

    File getFile(Files files);

    String spliceFileName(List<Files> list, String delimiter);

    /**
     * 将文件列表转为pdf
     * @param list
     * @return
     */
    List<String> toPdfList(List<Files> list) throws Exception;

    /**
     * 获取开放加密后的文件链接
     * @param id
     * @return
     */
    String getOpenFileUrl(String id);

    /**
     * 对开放加密的文件链接进行解码获取真正的文件id
     * @param id
     * @param key
     * @return
     */
    String openFileUrlDecode(String id, String key);

}
