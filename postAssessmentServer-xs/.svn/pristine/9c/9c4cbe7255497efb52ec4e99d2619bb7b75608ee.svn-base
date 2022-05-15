package com.beneway.common.common.utils.files;


import com.aspose.words.*;
import com.beneway.common.entity.files.Files;
import com.beneway.common.entity.system.Result;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 1.此处代码是把office文档转换成pdf的工具类，在BS架构的产品中，我们可以使用基于JS的pdf插件显示pdf文档，但是前提IE需要按照adobe的pdf软件，对于非IE不用安装。
 *2.可以基于flexPager插件显示文档，但是也需要把office转换成swf的文件才可以，实现需要完成转换。
 * */
public class OfficeToPdf {
    private static final Logger logger = LoggerFactory.getLogger(OfficeToPdf.class);


    private static final int wdFormatPDF = 17;
    private static final int xlsFormatPDF = 0;
    private static final int pptFormatPDF = 32;
    private static final int msoTrue = -1;
    private static final int msofalse = 0;


    public static boolean convert2PDF(String inputFile, String inputFileSuffix, String pdfFile) throws Exception {

        File file = new File(inputFile);
        if (!file.exists()) {
            throw new RuntimeException("文件不存在！");
        }
        if (inputFileSuffix.equals(".pdf")) {
            throw new RuntimeException("PDF不需要转换！");
        }
        if (inputFileSuffix.equals(".doc") || inputFileSuffix.equals(".docx")) {
            try {
                //WordUtils.docToPdf(inputFile, pdfFile);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else if (inputFileSuffix.equals(".ppt") || inputFileSuffix.equals(".pptx")) {
            return ppt2PDF(inputFile, pdfFile);
        } else if (inputFileSuffix.equals(".xls") || inputFileSuffix.equals(".xlsx")) {
            return excel2PDF(inputFile, pdfFile);
        } else {
            throw new RuntimeException("文件格式不支持转换!");
        }
    }

    /**
     * 获取license
     *
     * @return
     */
    private static boolean getLicense() {
        boolean result = false;
        try {
            // 凭证
            String licenseStr =
                    "<License>\n" +
                            "  <Data>\n" +
                            "    <Products>\n" +
                            "      <Product>Aspose.Total for Java</Product>\n" +
                            "      <Product>Aspose.Words for Java</Product>\n" +
                            "    </Products>\n" +
                            "    <EditionType>Enterprise</EditionType>\n" +
                            "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                            "    <LicenseExpiry>20991231</LicenseExpiry>\n" +
                            "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                            "  </Data>\n" +
                            "  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
                            "</License>";
            InputStream license = new ByteArrayInputStream(licenseStr.getBytes("UTF-8"));
            License asposeLic = new License();
            asposeLic.setLicense(license);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Word 2 pdf.
     *
     */
    public static boolean word2Pdf(String inputFile, String pdfFile) throws Exception {
        FileOutputStream fileOS = null;
        // 验证License
        if (!getLicense()) {
            throw new RuntimeException("验证License失败！");
        }
        File inputWord = new File(inputFile);
        String os = System.getProperty("os.name");
        if (!os.toLowerCase().startsWith("win")) {

            String[] arr = new String[]{"/usr/share/fonts", "/usr/share/fonts/chinese"};
            if (os.toLowerCase().indexOf("mac") > -1){
                arr = new String[]{"/System/Library/Fonts/", "/Users/lgoodbook/Fonts/", "/usr/share/fonts/Fonts/"};
            }

            //此处处理乱码和小方块
            //如果在本地运行,此处报错,请注释这个这是字体,主要是为了解决linux环境下面运行jar时找不到中文字体的问题

            FontSettings.getDefaultInstance().setFontsFolders(arr, true);
        }

        Document doc = new Document(new FileInputStream(inputWord));
        fileOS = new FileOutputStream(new File(pdfFile));
        // 保存转换的pdf文件
        doc.save(fileOS, SaveFormat.PDF);
        if(fileOS != null){
            fileOS.close();
        }
        return true;
    }


    private static boolean excel2PDF(String inputFile, String pdfFile) {
        try {
            ActiveXComponent app = new ActiveXComponent("Excel.Application");
            app.setProperty("Visible", false);
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.call(excels, "Open", inputFile, false,
                    true).toDispatch();
            File tofile = new File(pdfFile);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.call(excel, "ExportAsFixedFormat", xlsFormatPDF, pdfFile);
            Dispatch.call(excel, "Close", false);
            app.invoke("Quit");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private static boolean ppt2PDF(String inputFile, String pdfFile) {
        try {
            ActiveXComponent app = new ActiveXComponent(
                    "PowerPoint.Application");
            Dispatch ppts = app.getProperty("Presentations").toDispatch();
            Dispatch ppt = Dispatch.call(ppts, "Open", inputFile, true,// ReadOnly
                    true,// Untitled指定文件是否有标题
                    false// WithWindow指定文件是否可见
            ).toDispatch();
            File tofile = new File(pdfFile);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.call(ppt, "SaveAs", pdfFile, pptFormatPDF);
            Dispatch.call(ppt, "Close");
            app.invoke("Quit");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void copyFile(File sourceFile, File destinationFile) throws IOException {

        InputStream input = null;
        OutputStream output = null;

        try {

            input = new FileInputStream(sourceFile);  //从sourceFile文件中获得输入字节
            output = new FileOutputStream(destinationFile);

            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            input.close();
            output.close();
        }

    }

    public static Result yulan(HttpServletResponse response , Files f, String filePath , String filename) throws Exception {
        if (f.getSuffix().equals(".pdf")){
            File sourceFile = new File(filePath  + filename);
            File destinationFile = new File(filePath + "/pdf/" + filename+ ".pdf");
            copyFile(sourceFile,destinationFile);
        }
        ServletOutputStream os = null;
        FileOutputStream fileOS = null;
        FileInputStream fis = null;
        // 验证License
        if (!getLicense()) {
            throw new RuntimeException("验证License失败！");
        }
        String str = System.getProperty("os.name");
        if (!str.toLowerCase().startsWith("win")) {
            String[] arr = new String[]{"/usr/share/fonts", "/usr/share/fonts/chinese"};
            if (str.toLowerCase().indexOf("mac") > -1){
                arr = new String[]{"/System/Library/Fonts/", "/Users/lgoodbook/Fonts/", "/usr/share/fonts/Fonts/"};
            }
            //此处处理乱码和小方块
            //如果在本地运行,此处报错,请注释这个这是字体,主要是为了解决linux环境下面运行jar时找不到中文字体的问题

            FontSettings.getDefaultInstance().setFontsFolders(arr, true);
        }
        //路径
        File dir = new File(filePath + "/pdf");
        //判断是否有文件路径，没有就创建
        if (!dir.exists()) dir.mkdir();

        //判断文件里面是否有此文件转换过的文件，没有则创建
        File pdf = new File(filePath + "/pdf/" + filename + ".pdf");
        if(!pdf.exists()){
            //获取到doc文件
            Document doc = new Document(new FileInputStream(filePath + "/" + filename));
            //创建输出流，pdf文件保存的位置，并命名
            fileOS = new FileOutputStream(dir+ "/"+ filename + ".pdf");
            //转换，保存到对应的文件夹下
            doc.save(fileOS, SaveFormat.PDF);
        }
        fis = new FileInputStream(pdf);
        //设置相关参数
        response.setHeader("Accept-Ranges", "bytes");
        response.setContentLengthLong(pdf.length());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;filename=\"" +
                new String(f.getFileName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)
                + ".pdf" + "\"");
        os = response.getOutputStream();
        int count;
        byte[] buffer = new byte[4096];
        while ((count = fis.read(buffer)) != -1) {
            os.write(buffer, 0, count);
        }
        os.flush();

        if(fileOS != null){
            fileOS.close();
        }
        if (null != fis) {
            fis.close();
        }
        if (null != os) {
            os.close();
        }
        return Result.success();
    }

    
    public static void main(String[] args) {
        
    }
}