package com.beneway.common.common.utils.fileUpload;

import java.io.*;

public class FileUtil{

    //文件上传工具类服务方法

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception{

        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static void flow(InputStream inputStream, OutputStream outputStream) throws IOException {

        if (inputStream != null && outputStream != null){
            byte[] bytes = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, length);
            }

            inputStream.close();
            outputStream.flush();
            outputStream.close();

        }
    }

    public static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }


    public static String getFilesSuffix(String fileName){
        String[] split = fileName.split("\\.");
        String suffix = "."+split[split.length - 1].toLowerCase();
        return suffix;
    }

    public static String getFilesName(String fileName){
        String[] split = fileName.split("\\.");
        String realName = "";
        if(split.length > 2){
            for (int i = 0 ; i < split.length - 1 ; i++) {
                realName = realName + split[i] + ".";
            }
            realName.substring(0 , realName.length()-1);
        }else{
            realName = split[0];
        }
        return realName;
    }

    public static String checkFileType(String suffix) {
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

}