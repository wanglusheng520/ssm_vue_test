package com.beneway.common.service.files;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.AES;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beneway.common.common.config.FilesConfig;
import com.beneway.common.common.utils.exception.RRException;
import com.beneway.common.dao.files.FilesDao;
import com.beneway.common.entity.files.Files;
import com.beneway.common.entity.system.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service("filesService")
public class FilesServiceImpl extends ServiceImpl<FilesDao, Files> implements FilesService {

    @Autowired
    private FilesDao filesDao;

    @Autowired
    private FilesConfig filesConfig;

    @Override
    public Files find(String uuid) {
        return filesDao.find(uuid);
    }

    @Override
    public void insert(Files files) {
        files.setCreateDate(new Date());
        filesDao.insert(files);
    }

    @Override
    public void insertInterim(String id, String fileName, String suffix) {
        Files files = new Files();
        files.setId(id);
        files.setFileName(fileName);
        files.setSuffix(suffix);
        files.setPath(filesConfig.getInterim());
        insert(files);
    }

    @Override
    public String getServerUrl() {
        return filesDao.getServerUrl();
    }

    @Override
    public ArrayList<String> getFileName(String[] ids) {
        ArrayList<String> names = new ArrayList<>();
        for (String id : ids) {
            if(!id.equals("")){
                Files files = filesDao.find(id);
                String fileName = files.getFileName() + files.getSuffix();
                names.add(fileName);
            }
        }
        return names;
    }

    @Override
    public Result delete(String fileId) {
        try {
            File file = this.getFileById(fileId);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.removeById(fileId);
        return Result.success("删除成功");
    }


    @Override
    public List<Files> getFileByIdList(List<String> idList){
        if (idList != null && idList.size() > 0){
            List<Files> list = filesDao.getFileByIdList(idList);
            return list;
        }else{
            return null;
        }
    }


    @Override
    public List<Files> getFileByIds(String ids){
        if (StringUtils.isNotEmpty(ids)){
            if(ids.endsWith(",")){
                ids = ids.substring(0, ids.length() - 1);
            }
            List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
            return getFileByIdList(idList);
        }else{
            return null;
        }
    }

    /**
     * 获取文件真实地址
     * @param uuid
     * @return
     */
    @Override
    public String getFileRealAddress(String uuid) {
        Files files = this.find(uuid);
        return getFileRealAddress(files);
    }

    @Override
    public String getFileRealAddress(Files files) {
        return filesConfig.packStorPath(files.getPath(), files.getId());
    }

    @Override
    public List<String> getFileRealAddressList(List<Files> filesList) {
        List<String> list = new LinkedList<>();
        for (Files files : filesList) {
            list.add(getFileRealAddress(files));
        }
        return list;
    }

    /**
     * 获取文件
     * @param uuid
     * @return
     */
    @Override
    public File getFileById(String uuid) {
        String fileRealAddress = this.getFileRealAddress(uuid);
        return getFile(fileRealAddress);
    }

    @Override
    public File getFile(Files files) {
        String fileRealAddress = filesConfig.packStorPath(files.getPath(), files.getId());
        return getFile(fileRealAddress);
    }

    private File getFile(String path){
        File file = new File(path);
        if (!file.exists()){
            throw new RRException("文件资源不存在！");
        }
        return file;
    }

    @Override
    public String spliceFileName(List<Files> list, String delimiter) {
        if (StrUtil.isEmpty(delimiter)){
            delimiter = ",";
        }
        if (CollUtil.isNotEmpty(list)){
            StringJoiner sj = new StringJoiner(delimiter);
            for (Files files : list) {
                sj.add(files.getFileName() + files.getSuffix());
            }
            return sj.toString();
        }
        return "";
    }

    /**
     * 将文件列表转为pdf
     * @param list
     * @return
     */
    @Override
    public List<String> toPdfList(List<Files> list) throws Exception {
        if (CollUtil.isNotEmpty(list)){
            List<String> l = new ArrayList<>(list.size());
            for (Files files : list) {
                String suffix = files.getSuffix();
                String address = getFileRealAddress(files);
                if (".doc".equalsIgnoreCase(suffix) || ".docx".equalsIgnoreCase(suffix)){
                    String interimFilePath = filesConfig.getInterimFilePath();
                    //WordUtils.docToPdf(address, interimFilePath, suffix);
                    address = interimFilePath;
                } else if (!".pdf".equalsIgnoreCase(suffix)){
                    // 图片格式
                    String interimFilePath = filesConfig.getInterimFilePath();
                    //WordUtils.imgToPdf(address, interimFilePath);
                    address = interimFilePath;
                }
                l.add(address);
            }
            return l;
        }else{
            return null;
        }
    }

    /**
     * 获取开放加密后的文件链接
     * @param id
     * @return
     */
    @Override
    public String getOpenFileUrl(String id){
        // 生成key
        String uuid = IdUtil.simpleUUID();
        String key = StrUtil.sub(uuid, 0, 16);
        // 加密
        AES aes = createAES(key);
        String encryptHex = aes.encryptHex(id);
        // 拼接url
        String url = filesDao.getServerUrl();
        url += encryptHex;
        url += "/";
        url += key;

        return url;
    }

    /**
     * 对开放加密的文件链接进行解码获取真正的文件id
     * @param id
     * @param key
     * @return
     */
    @Override
    public String openFileUrlDecode(String id, String key){
        AES aes = createAES(key);
        // 解密
        String decryptStr = aes.decryptStr(id);
        return decryptStr;
    }

    private AES createAES(String key){
        AES aes = new AES("CBC", "PKCS7Padding",
                // 密钥，可以自定义
                key.getBytes(),
                // iv加盐，按照实际需求添加
                "DYgjCEIMVrj2W9xN".getBytes());
        return aes;
    }

}
