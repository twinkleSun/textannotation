package com.annotation.util;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {

    //检查文件的格式
    public static int checkfiletype(String filename){
        //-1文件格式不正确
        try {
            if (filename.endsWith(".doc") ||filename.endsWith(".docx") ||filename.endsWith(".txt")) {
                return 0;
            }else{
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    //解析文件的内容
    public static String parsefilecontent(MultipartFile multipartFile)throws IOException {
        String filename =multipartFile.getOriginalFilename();//文件名称
        InputStream inputStream = multipartFile.getInputStream();
        String docContent="";//文件内容
        try {
            if (filename.endsWith(".doc")) {
                WordExtractor ex = new WordExtractor(inputStream);
                docContent = ex.getText();
            } else if (filename.endsWith("docx")) {
                XWPFDocument xdoc = new XWPFDocument(inputStream);
                XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
                docContent= extractor.getText();
                inputStream.close();
            } else if(filename.endsWith(".txt")) {
                InputStreamReader reader = new InputStreamReader(inputStream, "GBK");
                BufferedReader br = new BufferedReader(reader);
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                docContent = sb.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return docContent;
    }

    //信息抽取和分类的条件判断
    public static int checkfilecontent(MultipartFile file)throws IOException {
        String docContent = parsefilecontent(file);
        long filesize = file.getSize();
        if(filesize>=1048576){
             return -4;
        }
        //读取的文件内容由#分隔
        //检查每段内容大小
        String[] contentArr = docContent.split("#");
        //-2文件中有的item为空
        //-3文件中有的文本超过字数限制
        for (int i = 0; i < contentArr.length; i++) {
            if (contentArr[i].length() <= 0) {
                return -2;
            }
            if (contentArr[i].length() > 20000) {
                return -3;
            }
        }
        return 0;
    }

    //两个文本的条件判断
    public static int checktwoitem(MultipartFile multipartFile)throws IOException {
        String docContent = parsefilecontent(multipartFile);
        //读取的文件内容由#分隔
        //检查每段内容大小
        String[] contentArr = docContent.split("#");
        for (int i = 0; i < contentArr.length; i++) {
            String[] itemArr = contentArr[i].split("-------");
            //-2每个instance中的item的个数不正确
            //-3文件中有的item为空
            //-4文件中有的文本超过字数限制
            if(itemArr.length!=2){
                return -2;
            }else{
                for(int j=0;j<itemArr.length;j++){
                    if (itemArr[i].length() <= 0) {
                        return -3;
                    }
                    if (itemArr[i].length() > 20000) {
                        return -4;
                    }
                }
            }
        }
        return 0;
    }

    //多个文本的条件判断
    public static int checkmatchcategory(MultipartFile multipartFile)throws IOException {
        String docContent = parsefilecontent(multipartFile);
        //读取的文件内容由#分隔
        //检查每段内容大小
        String[] contentArr = docContent.split("#");
        for (int i = 0; i < contentArr.length; i++) {
            String[] listArr = contentArr[i].split("-------");
            //-2每个instance中的list的个数不正确
            //-3文件中有的item为空
            //-4文件中有的文本超过字数限制
            if(listArr.length!=2){
                return -2;
            }else{
                for(int j=0;j<listArr.length;j++){
                    String[] itemArr = listArr[i].split("&&&&&&&");
                    for(int k=0;k<itemArr.length;k++) {
                        if (itemArr[i].length() <= 0) {
                            return -3;
                        }
                        if (itemArr[i].length() > 20000) {
                            return -4;
                        }
                    }
                }
            }
        }
        return 0;
    }
}
