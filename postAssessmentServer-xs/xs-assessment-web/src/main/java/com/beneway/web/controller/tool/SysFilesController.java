package com.beneway.web.controller.tool;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.beneway.common.common.result.Result;
import com.beneway.common.common.utils.files.FileRuntimeException;
import com.beneway.common.common.utils.files.FileUtil;
import com.beneway.common.common.utils.files.WordUtils;
import com.beneway.common.common.utils.sys_files.FilePathEnum;
import com.beneway.common.common.utils.sys_files.FileTypeEnum;
import com.beneway.common.common.utils.sys_files.SysFilesUtils;
import com.beneway.common.system.entity.sys_files.SysFiles;
import com.beneway.common.system.service.sys_files.SysFilesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/sys_files")
public class SysFilesController {

    @Autowired
    private SysFilesUtils sysFilesUtils;

    @Autowired
    private SysFilesService sysFilesService;

    /**
     * 上传文件
     */
    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam(value = "file") MultipartFile file, String path){
        // 判断是否有目录
        if (StrUtil.isEmpty(path)){
            // 为空就设置默认路径
            path = FilePathEnum.FILES.getPath();
        }else{
            // 创建路径
            sysFilesUtils.createPath(path);
        }

        // 保存文件
        if (file != null) {
            //截取文件名
            String[] split = file.getOriginalFilename().split("\\.");
            //获取文件名以及后缀
            String filename = split[0];
            String suffix = "."+split[split.length - 1].toLowerCase();
            // 判断文件类型是否在白名单中
            if (FileTypeEnum.isNotExistFileType(suffix)){
                return Result.error("该文件类型不允许上传");
            }

            // 封装文件地址
            String fileId = IdUtil.simpleUUID();
            String filePath = sysFilesUtils.packStorPath(path, fileId);
            // 保存文件
            try {
                FileUtil.inputFile(file.getInputStream(), filePath);
            } catch (IOException e) {
                throw new FileRuntimeException(e);
            }

            // 保存文件信息到数据库中
            sysFilesService.saveFile(fileId, path, filename, suffix);

            return Result.ok(fileId);
        }else{
            return Result.error("文件为空上传失败");
        }
    }

    /**
     * 下载文件
     * @param fileId
     * @param response
     * @return
     */
    @GetMapping("/downloadFile/{fileId}")
    public Result downloadFile(@PathVariable("fileId") String fileId, HttpServletResponse response){
        SysFiles sysFiles = sysFilesService.getById(fileId);
        if (sysFiles == null){
            return Result.error("文件不存在");
        }

        File file = sysFilesService.getFile(sysFiles);
        String filename = sysFiles.getFilename();
        String suffix = sysFiles.getSuffix();

        //设置相关参数
        response.setHeader("Accept-Ranges","bytes");
        response.setContentLengthLong(file.length());
        response.setHeader("Content-Disposition","attachment;filename=\""+
                new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)
                +suffix+ "\"");

        // 传输文件
        try {
            FileUtil.flow(new FileInputStream(file), response.getOutputStream());
        } catch (IOException e) {
            throw new FileRuntimeException(e);
        }

        return Result.ok();
    }

    /**
     * 预览文件
     * @param fileId
     * @param response
     * @return
     */
    @GetMapping("/previewFile/{fileId}")
    public Result previewFile(@PathVariable("fileId") String fileId, HttpServletResponse response){
        SysFiles sysFiles = sysFilesService.getById(fileId);
        if (sysFiles == null){
            return Result.error("文件不存在");
        }

        File file = sysFilesService.getFile(sysFiles);
        String filename = sysFiles.getFilename();
        String suffix = sysFiles.getSuffix();

        String pdfFilePath = null;

        // 根据不同的文件类型设置响应头
        FileTypeEnum fileTypeEnum = FileTypeEnum.getTypeBySuffix(suffix);
        if (FileTypeEnum.PDF.equals(fileTypeEnum)) {
            response.setContentType("application/pdf");
        } else if (FileTypeEnum.WORD.equals(fileTypeEnum)) {
            response.setContentType("application/pdf");

            // 将文件转为pdf
            pdfFilePath = sysFilesUtils.packStorPath(FilePathEnum.INTERIM, IdUtil.simpleUUID());
            try {
                WordUtils.docToPdf(file.getAbsolutePath(), pdfFilePath, suffix);
                file = new File(pdfFilePath);
            } catch (Exception e) {
                throw new FileRuntimeException("pdf转换失败", e);
            }
        }

        // 传输文件
        try {
            FileUtil.flow(new FileInputStream(file), response.getOutputStream());

            // 删除临时pdf文件
            if (StrUtil.isNotEmpty(pdfFilePath)) {
                file.delete();
            }
        } catch (IOException e) {
            throw new FileRuntimeException(e);
        }

        return Result.ok();
    }

    /**
     * 文件删除
     * @param fileId 文件id
     * @param isDel  是否真正删除文件
     * @return
     */
    @PutMapping("/delFile")
    public Result delFile(@RequestParam("fileId") String fileId,
                          @RequestParam(value = "isDel", defaultValue = "true") boolean isDel){
        if (isDel){
            sysFilesService.delFile(fileId);
        }
        return Result.ok();
    }

    /**
     * 根据文件id串获取文件信息列表
     * @param ids
     * @return
     */
    @GetMapping("/getListByIds")
    public Result getListByIds(@RequestParam("ids") String ids){
        List<SysFiles> sysFilesList = sysFilesService.getListByIds(ids);
        return Result.ok(sysFilesList);
    }

}
