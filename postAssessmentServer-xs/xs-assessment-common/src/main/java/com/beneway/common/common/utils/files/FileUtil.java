package com.beneway.common.common.utils.files;

import java.io.*;

public class FileUtil {

    /**
     * 输入文件到inputStream流中
     * @param inputStream
     * @param filePath
     */
    public static void inputFile(InputStream inputStream, String filePath){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            flow(inputStream, fileOutputStream);
        } catch (FileNotFoundException e) {
            throw new FileRuntimeException(e);
        }
    }

    /**
     * 输出文件到outputStream流中
     * @param outputStream
     * @param filePath
     */
    public static void outputFile(OutputStream outputStream, String filePath){
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            flow(fileInputStream, outputStream);
        } catch (FileNotFoundException e) {
            throw new FileRuntimeException(e);
        }
    }

    /**
     * copy文件
     * @param sourceFilePath
     * @param targetFilePath
     */
    public static void copyFile(String sourceFilePath, String targetFilePath){
        try {
            FileInputStream fileInputStream = new FileInputStream(sourceFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(targetFilePath);
            flow(fileInputStream, fileOutputStream);
        } catch (FileNotFoundException e) {
            throw new FileRuntimeException(e);
        }
    }

    public static void flow(InputStream inputStream, OutputStream outputStream){
        int count;
        byte[] buffer = new byte[4096];
        try {
            while ((count = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }
        } catch (IOException e) {
            throw new FileRuntimeException(e);
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

}
