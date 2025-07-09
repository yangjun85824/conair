package com.yyld.conair.controller;


import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import com.yyld.conair.biz.WebSocket;
import com.yyld.conair.commons.data.result.APIResult;
import com.yyld.conair.controller.util.UploadFileTypeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
@RestController
public class IndexController {

    @Resource
    private WebSocket webSocket;

    @GetMapping("/index")
    public String index(){

        webSocket.onMessage("11111");
        return  "sss";
    }

    /**
     * 判断文件类型
     */
    @PostMapping("/fileupload")
    public APIResult jmimemagic(@RequestBody MultipartFile[] files, HttpServletRequest request) {

        //故意把png文件的后缀改成txt,看是否能获取到真实文件格式
//        final String filePath = getFileType("C:\\Users\\13192\\Pictures\\123.png.txt");
        String filePath = "C:\\Users\\杨建俊\\Desktop\\新建文件夹\\477.jpg";
        String name= null;
        try {
//            name = GetFileTypeUtil.getType(filePath).name();
//            System.out.println(name);
//            name = GetFileTypeUtil.getType(filePath).getValue();
//            System.out.println(name);
            String fileName = files[0].getOriginalFilename();
            System.out.println(fileName);
//            name = GetFileTypeUtil.getType(files[0].getInputStream(),fileName).name();
//            System.out.println(name);

//            fileName = FileTypes.getFileType(files[0].getInputStream());
//            System.out.println(fileName);
            System.out.println(UploadFileTypeUtil.getType(files[0].getInputStream(),fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("文件格式是"+name);

        return null;
    }

    private static final String FILE_ZIP="zip";
    private static final String FILE_XLS=".xls";
    private static final String FILE_XLSX=".xlsx";

    public boolean checkFileType(MultipartFile file)  {
        String filename = file.getOriginalFilename();
        String fileType = filename.substring(filename.lastIndexOf("."));
        //先判断后缀名
        if (FILE_XLS.equalsIgnoreCase(fileType) || FILE_XLSX.equalsIgnoreCase(fileType)) {
            String type ;
            try {
                type = FileTypeUtil.getType(file.getInputStream());
                //根据首部字节判断文件类型
                if (FILE_ZIP.contains(type)||FILE_XLS.contains(type)){
                    return true;
                }
            } catch (Exception e) {
//                log.error("上传工作状态excel失败", e);
                e.printStackTrace();
                return false;
            }

        }
        return false;
    }
    /**
     * 根据文件流的头部信息获得文件类型
     *
     * <pre>
     *     1、无法识别类型默认按照扩展名识别
     *     2、xls、doc、msi头信息无法区分，按照扩展名区分
     *     3、zip可能为docx、xlsx、pptx、jar、war头信息无法区分，按照扩展名区分
     * </pre>
     *
     * @param file 文件 {@link File}
     * @return 类型，文件的扩展名，未找到为<code>null</code>
     * @throws IORuntimeException 读取文件引起的异常
     */
    public static String getType(File file) throws IORuntimeException {
        String typeName = null;
//        FileInputStream in = null;
        /*try {
            in = IoUtil.toStream(file);
            typeName = getType(in);
        } finally {
            IoUtil.close(in);
        }*/

        if (null == typeName) {
            // 未成功识别类型，扩展名辅助识别
            typeName = FileUtil.extName(file);
        } else if ("xls".equals(typeName)) {
            // xls、doc、msi的头一样，使用扩展名辅助判断
            final String extName = FileUtil.extName(file);
            if ("doc".equalsIgnoreCase(extName)) {
                typeName = "doc";
            } else if ("msi".equalsIgnoreCase(extName)) {
                typeName = "msi";
            }
        } else if ("zip".equals(typeName)) {
            // zip可能为docx、xlsx、pptx、jar、war等格式，扩展名辅助判断
            final String extName = FileUtil.extName(file);
            if ("docx".equalsIgnoreCase(extName)) {
                typeName = "docx";
            } else if ("xlsx".equalsIgnoreCase(extName)) {
                typeName = "xlsx";
            } else if ("pptx".equalsIgnoreCase(extName)) {
                typeName = "pptx";
            } else if ("jar".equalsIgnoreCase(extName)) {
                typeName = "jar";
            } else if ("war".equalsIgnoreCase(extName)) {
                typeName = "war";
            }
        }
        return typeName;
    }

    public static void main(String[] args) {
        String taskName = "【采购发票报账 测试】 CGBZ24112500062 -" +
                "测试待办".replaceAll("[^\u4e00-\u9fa5]", "").replaceAll("</p>", "") + "申请";
        //cloudPushUrl = oasso + "?appId=" + appid + "&bsiCode=" + bsicode + "&instId=" + inst_id + "&taskId=" + currentTask_id + "&status=" + status + "&notifyType=1";
        //                cloudPushPCUrl = oasso + "?appId=" + appid + "&bsiCode=" + bsicode + "&instId=" + inst_id + "&taskId=" + currentTask_id + "&status=" + status + "&pc_slide=false&notifyType=1";
        taskName = "报销顺丰2025年5月份ABC运费".replaceAll("[^\u4e00-\u9fa5]", "").replaceAll("</p>", "") + "申请";
        System.out.println(taskName);
    }
}
