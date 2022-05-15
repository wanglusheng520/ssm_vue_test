package com.beneway.web.controller.tool;

import cn.hutool.core.util.IdUtil;
import com.beneway.common.common.config.FilesConfig;
import com.beneway.common.common.utils.files.OfficeToPdf;
import com.beneway.common.common.utils.base.Tools;
import com.beneway.common.common.utils.base.UuidUtil;
import com.beneway.common.common.utils.exception.RRException;
import com.beneway.common.common.utils.fileUpload.FileUtil;
import com.beneway.common.entity.files.Files;
import com.beneway.common.entity.system.Result;
import com.beneway.common.service.files.FilesService;
import com.beneway.web.common.utils.jwt.PassToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/files")
public class FilesController {

    @Autowired
    private FilesService filesService;

    @Autowired
    private FilesConfig filesConfig;


    private static List<String> whiteFile = new ArrayList<>();

    static {
        whiteFile.add(".pdf");
        whiteFile.add(".docx");
        whiteFile.add(".doc");
        whiteFile.add(".png");
        whiteFile.add(".jpeg");
        whiteFile.add(".jpg");
        whiteFile.add(".xlsx");
    }

    /**
     * 上传文件
     */
    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam(value = "file") MultipartFile file, String path){


        if (Tools.isEmpty(path)){
            path = "files";
        }

        //本地文件存储位置
        String filePath = filesConfig.packStorPath(path) + "/";
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        //没文件就返回错误1
        if (file.isEmpty()) {
            return Result.fail("文件不存在");
        }else {
            try {
                //截取文件名
                String[] split = file.getOriginalFilename().split("\\.");
                //获取文件名以及后缀
                String fileRealName = "";
                if(split.length > 2){
                    for (int i = 0 ; i < split.length - 1 ; i++) {
                        fileRealName = fileRealName + split[i] + ".";
                    }
                    fileRealName.substring(0 , fileRealName.length()-1);
                }else{
                    fileRealName = split[0];
                }

                String suffix = "."+split[split.length - 1].toLowerCase();

                // 判断文件后缀是否为限制文件格式
                if (!whiteFile.contains(suffix)){
                    log.error(String.format("文件为：%s 的文件格式不能被上传", fileRealName + suffix));
                    return Result.fail("文件格式错误!");
                }

                //生成uuid,使用uuid代替名字
                String uuid = UuidUtil.get32UUID();
                //设置路径

                //上传文件
                FileUtil.uploadFile(file.getBytes(),path+"/"+Tools.nowDate().toString()+"/",uuid);
                //文件上传后，添加进数据库
                Files f = Files.builder()
                        .id(uuid)
                        .suffix(suffix)
                        .fileType(checkFileType(suffix))
                        .fileName(fileRealName)
                        .realPath(Tools.nowDate().toString())
                        .createDate(new Date())
                        .build();
                filesService.insert(f);
                return Result.success(f);
            }catch (Exception e){
                e.printStackTrace();
                return Result.fail("文件上传失败");
            }
        }
    }

    @GetMapping("/getFilesByIds")
    public Result getFilesByIds(String ids){
        return Result.success(filesService.getFileByIds(ids));
    }

    private String checkFileType(String suffix) {
        String fileType = "0";
        Boolean flag = true;
        String[] allowImgTypes = new String[]{".jpg", ".png", ".gif", ".jpeg", ".webp"};
        String[] allowDocTypes = new String[]{".doc", ".docx", ".pdf",".xlsx"};
        String[] allowVideoTypes = new String[]{".mp4", ".mov", ".pdf"};
        for (String type : allowImgTypes) {
            if (suffix.equals(type)) {
                fileType = "1";
                flag = false;
                break;
            }
        }
        if (flag) {
            for (String type : allowDocTypes) {
                if (suffix.equals(type)) {
                    fileType = "2";
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            for (String type : allowVideoTypes) {
                if (suffix.equals(type)) {
                    fileType = "3";
                    break;
                }
            }
        }
        return fileType;
    }

    /**
     * 下载文件，图片的话直接显示
     */
    @GetMapping("/{filename}")
    public Result createFolw(@PathVariable("filename")String filename,
                             HttpServletResponse response) {
        return createFolwImpl(filename, response);
    }

    /**
     * 下载文件，图片的话直接显示
     */
    @PassToken
    @GetMapping("/showFile/{id}")
    public Result showFile(@PathVariable("id") String id,
                           HttpServletResponse response) {
        FileInputStream fis = null;
        ServletOutputStream os = null;
        try {
            Files file = filesService.getById(id);
            if(null != file){
                String Path = "files/" + file.getRealPath();
                File f = new File( Path + "/" + id);
                try {
                    fis = new FileInputStream(f);
                }catch (Exception e){
                    return Result.success();
                }
                //查询数据库中的文件后缀
                String suffix = file.getSuffix();
                String name = file.getFileName();

                //如果是doc，转pdf给前端
                if(suffix.equals(".doc") || suffix.equals(".docx")){
                    return OfficeToPdf.yulan(response ,file, Path ,id);
                }

                String[] images = {".jpg", ".png", ".gif", ".jpeg", ".webp"};
                boolean flag = true;
                for (String image : images) {
                    if (suffix.toLowerCase().equals(image)) {
                        flag = false;
                        break;
                    }
                }
                //设置相关参数
                response.setHeader("Accept-Ranges", "bytes");
                response.setContentLengthLong(f.length());
                //如果不是图片后缀，则使用附件的方式下载文件
                if (flag) {
                    response.setHeader("Content-Disposition", "attachment;filename=\"" +
                            new String(name.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)
                            + suffix + "\"");
                }

                os = response.getOutputStream();
                int count;
                byte[] buffer = new byte[4096];
                while ((count = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                }
                os.flush();
            }else{
                return Result.fail().setMsg("文件不存在！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail().setMsg("文件不存在！");
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
                if (null != os) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Result.success();
    }

    /**
     * 下载文件，图片的话直接显示开放
     */
    @GetMapping("/{filename}/{key}")
    public Result createFolwOpen(@PathVariable("filename") String filename,
                                 @PathVariable("key") String key,
                                 HttpServletResponse response){
        String id = filesService.openFileUrlDecode(filename, key);
        return createFolwImpl(id, response);
    }

    private Result createFolwImpl(String filename, HttpServletResponse response){
        FileInputStream fis = null;
        ServletOutputStream os = null;
        try {
            Files file = filesService.find(filename);
            File f = filesService.getFile(file);
            fis = new FileInputStream(f);
            //查询数据库中的文件后缀
            String suffix = file.getSuffix();
            String name = file.getFileName();
            String[] images = {".jpg",".png",".gif",".jpeg",".webp"};
            boolean flag = true;
            for (String image : images) {
                if (suffix.toLowerCase().equals(image)){
                    flag=false;
                    break;
                }
            }
            //设置相关参数
            response.setHeader("Accept-Ranges","bytes");
            response.setContentLengthLong(f.length());
            //如果不是图片后缀，则使用附件的方式下载文件
            if (flag){
                response.setHeader("Content-Disposition","attachment;filename=\""+
                        new String(name.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)
                        +suffix+ "\"");
            }

            os = response.getOutputStream();
            int count;
            byte[] buffer = new byte[4096];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail().setMsg("文件不存在！");
        }finally {
            try {
                if (null!=fis){
                    fis.close();
                }
                if (null!=os){
                    os.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return Result.success();
    }

    @GetMapping("/delFile/{uuid}")
    public Result delFile(@PathVariable("uuid") String uuid,
                          @RequestParam(value = "isDel", defaultValue = "true") boolean isDel){
        if (isDel){
            return filesService.delete(uuid);
        }else{
            return Result.success();
        }
    }

    @PostMapping("/getFileName")
    public ArrayList<String> getFileName(@RequestParam(value = "ids[]") String[] ids){
        return filesService.getFileName(ids);
    }

    @GetMapping("/getServerUrl")
    public String getServerUrl(){
        return filesService.getServerUrl();
    }

    @PassToken
    @GetMapping("/showPdf/{id}")
    public void showPdf(@PathVariable("id") String id, HttpServletResponse response){
        Files files = filesService.find(id);
        if (files == null){
            throw new RRException("文件不存在！");
        }

        File file = filesService.getFile(files);

        boolean isPdf = true;
        if (!".pdf".equalsIgnoreCase(files.getSuffix())){
            // 文件格式不为pdf
            isPdf = false;
            try {
                // 创建一个临时文件
                String lsFilePath = filesConfig.packStorPath(filesConfig.getInterim(), IdUtil.simpleUUID());
//                OfficeToPdf.convert2PDF(file.getAbsolutePath(), files.getSuffix(), lsFilePath);
                //WordUtils.docToPdf(file.getAbsolutePath(), lsFilePath, files.getSuffix());
                file = new File(lsFilePath);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RRException("生成pdf文件失败！");
            }
        }

        try {
            response.setContentType("application/pdf");
            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            FileUtil.flow(in, out);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RRException("pdf传输失败！");
        } finally {
            // 删除临时文件
            if (!isPdf){
                file.delete();
            }
        }

    }

    @GetMapping("/getList")
    public Result getList(@RequestParam("ids") String ids){
        List<Files> filesList = filesService.getFileByIds(ids);
        return Result.success(filesList);
    }

}
