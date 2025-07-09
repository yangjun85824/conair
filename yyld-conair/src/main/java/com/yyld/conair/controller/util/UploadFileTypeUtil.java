package com.yyld.conair.controller.util;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.util.CollectionUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class UploadFileTypeUtil {

    private final static Map<String, List<String>> FILE_TYPE_MAP = new HashMap();
    static{
        FILE_TYPE_MAP.put("jpg", Lists.newArrayList("ffd8ffe000104a464946")); //JPEG (jpg)
        FILE_TYPE_MAP.put("jpeg", Lists.newArrayList("ffd8ffe000104a464946")); //JPEG (jpg)
        FILE_TYPE_MAP.put("png", Lists.newArrayList("89504e470d0a1a0a0000")); //PNG (png)
        FILE_TYPE_MAP.put("gif", Lists.newArrayList("47494638396126026f01")); //GIF (gif)
        FILE_TYPE_MAP.put("tif", Lists.newArrayList("49492a00227105008037")); //TIFF (tif)
        FILE_TYPE_MAP.put("tiff", Lists.newArrayList("49492a00227105008037")); //TIFF (tif)
        FILE_TYPE_MAP.put("bmp", Lists.newArrayList("424d228c010000000000","424d8240090000000000","424d8e1b030000000000")); //16色位图(bmp)、24位位图(bmp)、256色位图(bmp)
//        FILE_TYPE_MAP.put("bmp", Lists.newArrayList("424d8240090000000000")); //24位位图(bmp)
//        FILE_TYPE_MAP.put("bmp", Lists.newArrayList("424d8e1b030000000000")); //256色位图(bmp)
        FILE_TYPE_MAP.put("dwg", Lists.newArrayList("41433130313500000000")); //CAD (dwg)
        FILE_TYPE_MAP.put("html", Lists.newArrayList("3c21444f435459504520")); //HTML (html)
        FILE_TYPE_MAP.put("htm", Lists.newArrayList("3c21646f637479706520")); //HTM (htm)
        FILE_TYPE_MAP.put("css", Lists.newArrayList("48544d4c207b0d0a0942")); //css
        FILE_TYPE_MAP.put("js" ,Lists.newArrayList("696b2e71623d696b2e71")); //js
        FILE_TYPE_MAP.put("rtf", Lists.newArrayList("7b5c727466315c616e73")); //Rich Text Format (rtf)
        FILE_TYPE_MAP.put("psd", Lists.newArrayList("38425053000100000000")); //Photoshop (psd)
        FILE_TYPE_MAP.put("eml", Lists.newArrayList("46726f6d3a203d3f6762")); //Email [Outlook Express 6] (eml)
        FILE_TYPE_MAP.put("doc", Lists.newArrayList("d0cf11e0a1b11ae10000","3c3f786d6c2076657273","504b0304140006000800")); //MS Excel 注意：word、msi 和 excel的文件头一样
        FILE_TYPE_MAP.put("vsd", Lists.newArrayList("d0cf11e0a1b11ae10000")); //Visio 绘图
        FILE_TYPE_MAP.put("mdb", Lists.newArrayList("5374616E64617264204A")); //MS Access (mdb)
        FILE_TYPE_MAP.put("ps" ,Lists.newArrayList("252150532D41646F6265"));
        FILE_TYPE_MAP.put("pdf", Lists.newArrayList("255044462d312e350d0a")); //Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put("rmvb", Lists.newArrayList("2e524d46000000120001")); //rmvb/rm相同
        FILE_TYPE_MAP.put("flv", Lists.newArrayList("464c5601050000000900")); //flv与f4v相同
        FILE_TYPE_MAP.put("mp4", Lists.newArrayList("00000020667479706d70"));
        FILE_TYPE_MAP.put("mp3", Lists.newArrayList("49443303000000002176"));
        FILE_TYPE_MAP.put("mpg", Lists.newArrayList("000001ba210001000180")); //
        FILE_TYPE_MAP.put("wmv", Lists.newArrayList("3026b2758e66cf11a6d9")); //wmv与asf相同
        FILE_TYPE_MAP.put("wav", Lists.newArrayList("52494646e27807005741")); //Wave (wav)
        FILE_TYPE_MAP.put("avi", Lists.newArrayList("52494646d07d60074156"));
        FILE_TYPE_MAP.put("mid", Lists.newArrayList("4d546864000000060001")); //MIDI (mid)
        FILE_TYPE_MAP.put("zip", Lists.newArrayList("504b0304140000000800","504b0506000000000000"));
        FILE_TYPE_MAP.put("rar", Lists.newArrayList("526172211a0700cf9073","526172211a070100415b1bdf0c01050800070101"));
        FILE_TYPE_MAP.put("ini", Lists.newArrayList("235468697320636f6e66"));
        FILE_TYPE_MAP.put("jar", Lists.newArrayList("504b03040a0000000000"));
        FILE_TYPE_MAP.put("exe", Lists.newArrayList("4d5a9000030000000400"));//可执行文件
        FILE_TYPE_MAP.put("jsp", Lists.newArrayList("3c25402070616765206c"));//jsp文件
        FILE_TYPE_MAP.put("mf" ,Lists.newArrayList("4d616e69666573742d56"));//MF文件
        FILE_TYPE_MAP.put("xml", Lists.newArrayList("3c3f786d6c2076657273"));//xml文件
        FILE_TYPE_MAP.put("sql", Lists.newArrayList("494e5345525420494e54"));//xml文件
        FILE_TYPE_MAP.put("java", Lists.newArrayList("7061636b616765207765"));//java文件
        FILE_TYPE_MAP.put("bat", Lists.newArrayList("406563686f206f66660d"));//bat文件
        FILE_TYPE_MAP.put("gz" ,Lists.newArrayList("1f8b0800000000000000"));//gz文件
        FILE_TYPE_MAP.put("properties", Lists.newArrayList("6c6f67346a2e726f6f74"));//bat文件
        FILE_TYPE_MAP.put("class",Lists.newArrayList("cafebabe0000002e0041"));//bat文件
        FILE_TYPE_MAP.put("chm", Lists.newArrayList("49545346030000006000"));//bat文件
        FILE_TYPE_MAP.put("mxp", Lists.newArrayList("04000000010000001300"));//bat文件
        FILE_TYPE_MAP.put("docx", Lists.newArrayList("504b0304140006000800","504b03040a0000000000"));//docx文件
        FILE_TYPE_MAP.put("wps", Lists.newArrayList("d0cf11e0a1b11ae10000"));//WPS文字wps、表格et、演示dps都是一样的
        FILE_TYPE_MAP.put("torrent", Lists.newArrayList("6431303a637265617465"));
        FILE_TYPE_MAP.put("mov", Lists.newArrayList("6D6F6F76")); //Quicktime (mov)
        FILE_TYPE_MAP.put("wpd", Lists.newArrayList("FF575043")); //WordPerfect (wpd)
        FILE_TYPE_MAP.put("dbx", Lists.newArrayList("CFAD12FEC5FD746F")); //Outlook Express (dbx)
        FILE_TYPE_MAP.put("pst", Lists.newArrayList("2142444E")); //Outlook (pst)
        FILE_TYPE_MAP.put("qdf", Lists.newArrayList("AC9EBD8F")); //Quicken (qdf)
        FILE_TYPE_MAP.put("pwl", Lists.newArrayList("E3828596")); //Windows Password (pwl)
        FILE_TYPE_MAP.put("ram", Lists.newArrayList("2E7261FD")); //Real Audio (ram)
        FILE_TYPE_MAP.put("txt", Lists.newArrayList("6C657420","7b2244617461436f756e74223a31353036392c22")); //txt
        FILE_TYPE_MAP.put("text", Lists.newArrayList("6C657420","7b2244617461436f756e74223a31353036392c22")); //txt
        FILE_TYPE_MAP.put("ofd", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd

//        FILE_TYPE_MAP.put("mkv", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd
//        FILE_TYPE_MAP.put("swf", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd
//        FILE_TYPE_MAP.put("wmy", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd
//        FILE_TYPE_MAP.put("flac", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //flac
//        FILE_TYPE_MAP.put("aac", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd
//        FILE_TYPE_MAP.put("m4a", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd
//        FILE_TYPE_MAP.put("au", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd
//        FILE_TYPE_MAP.put("mmf", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd
//        FILE_TYPE_MAP.put("ai", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd
//        FILE_TYPE_MAP.put("ape", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd
//        FILE_TYPE_MAP.put("wma", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd
        FILE_TYPE_MAP.put("xls", Lists.newArrayList("d0cf11e0a1b11ae10000")); //xls
        FILE_TYPE_MAP.put("xlsx", Lists.newArrayList("504b0304140006000800","504b03040a0000000000")); //xlsx
        FILE_TYPE_MAP.put("ppt", Lists.newArrayList("d0cf11e0a1b11ae1000000000000000000000000")); //ofd
        FILE_TYPE_MAP.put("pptx", Lists.newArrayList("504b030414000600080000002100dfcc18f5ad01")); //ofd
        FILE_TYPE_MAP.put("csv", Lists.newArrayList("efbbbf49642c557365722c486f73742c64622c43")); //ofd
//        FILE_TYPE_MAP.put("7z", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd
//        FILE_TYPE_MAP.put("arj", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd
//        FILE_TYPE_MAP.put("z", Lists.newArrayList("504B03041400000008004771B75AED68FCF26A01")); //ofd

        FILE_TYPE_MAP.put("ffd8ffe000104a464946", Lists.newArrayList("jpg")); //JPEG (jpg)
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", Lists.newArrayList("png")); //PNG (png)
        FILE_TYPE_MAP.put("47494638396126026f01", Lists.newArrayList("gif")); //GIF (gif)
        FILE_TYPE_MAP.put("49492a00227105008037", Lists.newArrayList("tif")); //TIFF (tif)
        FILE_TYPE_MAP.put("424d228c010000000000", Lists.newArrayList("bmp")); //16色位图(bmp)
        FILE_TYPE_MAP.put("424d8240090000000000", Lists.newArrayList("bmp")); //24位位图(bmp)
        FILE_TYPE_MAP.put("424d8e1b030000000000", Lists.newArrayList("bmp")); //256色位图(bmp)
        FILE_TYPE_MAP.put("41433130313500000000", Lists.newArrayList("dwg")); //CAD (dwg)
        FILE_TYPE_MAP.put("3c21444f435459504520", Lists.newArrayList("html")); //HTML (html)
        FILE_TYPE_MAP.put("3c21646f637479706520", Lists.newArrayList("htm")); //HTM (htm)
        FILE_TYPE_MAP.put("48544d4c207b0d0a0942", Lists.newArrayList("css")); //css
        FILE_TYPE_MAP.put("696b2e71623d696b2e71", Lists.newArrayList("js")); //js
        FILE_TYPE_MAP.put("7b5c727466315c616e73", Lists.newArrayList("rtf")); //Rich Text Format (rtf)
        FILE_TYPE_MAP.put("38425053000100000000", Lists.newArrayList("psd")); //Photoshop (psd)
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", Lists.newArrayList("eml")); //Email [Outlook Express 6] (eml)
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", Lists.newArrayList("doc")); //MS Excel 注意：word、msi 和 excel的文件头一样
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", Lists.newArrayList("vsd")); //Visio 绘图
        FILE_TYPE_MAP.put("5374616E64617264204A", Lists.newArrayList("mdb")); //MS Access (mdb)
        FILE_TYPE_MAP.put("252150532D41646F6265", Lists.newArrayList("ps"));
        FILE_TYPE_MAP.put("255044462d312e350d0a", Lists.newArrayList("pdf")); //Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put("2e524d46000000120001", Lists.newArrayList("rmvb")); //rmvb/rm相同
        FILE_TYPE_MAP.put("464c5601050000000900", Lists.newArrayList("flv")); //flv与f4v相同
        FILE_TYPE_MAP.put("00000020667479706d70", Lists.newArrayList("mp4"));
        FILE_TYPE_MAP.put("49443303000000002176", Lists.newArrayList("mp3"));
        FILE_TYPE_MAP.put("000001ba210001000180", Lists.newArrayList("mpg")); //
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", Lists.newArrayList("wmv")); //wmv与asf相同
        FILE_TYPE_MAP.put("52494646e27807005741", Lists.newArrayList("wav")); //Wave (wav)
        FILE_TYPE_MAP.put("52494646d07d60074156", Lists.newArrayList("avi"));
        FILE_TYPE_MAP.put("4d546864000000060001", Lists.newArrayList("mid")); //MIDI (mid)
        FILE_TYPE_MAP.put("504b0304140000000800", Lists.newArrayList("zip"));
        FILE_TYPE_MAP.put("526172211a0700cf9073", Lists.newArrayList("rar"));
        FILE_TYPE_MAP.put("235468697320636f6e66", Lists.newArrayList("ini"));
        FILE_TYPE_MAP.put("504b03040a0000000000", Lists.newArrayList("jar"));
        FILE_TYPE_MAP.put("4d5a9000030000000400", Lists.newArrayList("exe"));//可执行文件
        FILE_TYPE_MAP.put("3c25402070616765206c", Lists.newArrayList("jsp"));//jsp文件
        FILE_TYPE_MAP.put("4d616e69666573742d56", Lists.newArrayList("mf"));//MF文件
        FILE_TYPE_MAP.put("3c3f786d6c2076657273", Lists.newArrayList("xml"));//xml文件
        FILE_TYPE_MAP.put("494e5345525420494e54", Lists.newArrayList("sql"));//xml文件
        FILE_TYPE_MAP.put("7061636b616765207765", Lists.newArrayList("java"));//java文件
        FILE_TYPE_MAP.put("406563686f206f66660d", Lists.newArrayList("bat"));//bat文件
        FILE_TYPE_MAP.put("1f8b0800000000000000", Lists.newArrayList("gz"));//gz文件
        FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", Lists.newArrayList("properties"));//bat文件
        FILE_TYPE_MAP.put("cafebabe0000002e0041", Lists.newArrayList("class"));//bat文件
        FILE_TYPE_MAP.put("49545346030000006000", Lists.newArrayList("chm"));//bat文件
        FILE_TYPE_MAP.put("04000000010000001300", Lists.newArrayList("mxp"));//bat文件
        FILE_TYPE_MAP.put("504b0304140006000800", Lists.newArrayList("docx"));//docx文件
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", Lists.newArrayList("wps"));//WPS文字wps、表格et、演示dps都是一样的
        FILE_TYPE_MAP.put("6431303a637265617465", Lists.newArrayList("torrent"));

        FILE_TYPE_MAP.put("6D6F6F76", Lists.newArrayList("mov")); //Quicktime (mov)
        FILE_TYPE_MAP.put("FF575043", Lists.newArrayList("wpd")); //WordPerfect (wpd)
        FILE_TYPE_MAP.put("CFAD12FEC5FD746F", Lists.newArrayList("dbx")); //Outlook Express (dbx)
        FILE_TYPE_MAP.put("2142444E", Lists.newArrayList("pst")); //Outlook (pst)
        FILE_TYPE_MAP.put("AC9EBD8F", Lists.newArrayList("qdf")); //Quicken (qdf)
        FILE_TYPE_MAP.put("E3828596", Lists.newArrayList("pwl")); //Windows Password (pwl)
        FILE_TYPE_MAP.put("2E7261FD", Lists.newArrayList("ram")); //Real Audio (ram)
    }
    public static void main(String[] args) {
        try {
            String txt = "";//getType(new FileInputStream("C:\\Users\\杨建俊\\Desktop\\附件2：日程安排表.doc"),"doc");
//            txt = getType(new FileInputStream("C:\\Users\\杨建俊\\Desktop\\附件1：任务呈报表.doc"),"doc");
//            txt = getType(new FileInputStream("C:\\Users\\杨建俊\\Desktop\\发票-盛盟.rar"),"rar");
//            txt = getType(new FileInputStream("C:\\Users\\杨建俊\\Desktop\\日程安排表.docx"),"docx");
//            txt = getType(new FileInputStream("C:\\Users\\杨建俊\\Desktop\\山东省因公出国任务呈报表7月.doc"),"docx");
//            txt = getType(new FileInputStream("C:\\Users\\杨建俊\\Desktop\\新建 360压缩 ZIP 文件.zip"),"zip");
            txt = getType(new FileInputStream("C:\\Users\\杨建俊\\Desktop\\新建 Microsoft Excel 工作表.xls"),"zip");
            System.out.println(txt);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getType(InputStream inputStream,String fileName){
        try {
            String fileHead = getFileHeadContent(inputStream);
            if (fileHead == null || fileHead.length() == 0 || StringUtils.isBlank(fileName)) {
                return null;
            }
            fileHead = fileHead.toUpperCase();
            String[] fname = fileName.split("\\.");
            String typeName = fname[fname.length-1];
            List<String> values = FILE_TYPE_MAP.get(typeName.toLowerCase());
            if (CollectionUtils.isEmpty(values)){
                return null;
            }
            for (String value : values) {
                if (fileHead.toLowerCase().startsWith(value.toLowerCase())){
                    return typeName;
                }
                String val = value.length() >= 20 ? value.substring(0,14) : value;
                if (fileHead.toLowerCase().startsWith(val.toLowerCase())){
                    return typeName;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    private static String getFileHeadContent(InputStream inputStream) throws IOException {

        byte[] b = new byte[20];
        try {
            /**
             * int read() 从此输入流中读取一个数据字节。int read(byte[] b) 从此输入流中将最多 b.length
             * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len)
             *从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。
             * 之所以从输入流中读取20个字节数据，是因为不同格式的文件头魔数长度是不一样的，比如 EML("44656C69766572792D646174653A")和GIF("47494638")
             * 为了提高识别精度所以获取的字节数相应地长一点
             */
            inputStream.read(b, 0, 20);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return bytesToHexString(b);
    }
    /**
     * @description 第二步：将文件头转换成16进制字符串
     * @param
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        System.out.println("文件类型16进制字符串是"+stringBuilder.toString());
        return stringBuilder.toString();
    }

}