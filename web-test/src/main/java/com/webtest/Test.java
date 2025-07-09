package com.webtest;

//import org.apache.catalina.mapper.Mapper;

import org.docx4j.Docx4J;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
public class Test {

    public static void main(String[] args) {
//        System.out.println("11111111111");
//        //备份原有输出流
//        PrintStream old = System.out;
//        ConsoleStream newStream = new ConsoleStream(old);
        //设置新的输出流
//        System.setOut(new PrintStream(newStream));
        try {
            changeSystemConsoleCharset(StandardCharsets.UTF_8);
            // 打印当前系统控制台编码
            System.out.println("Current System Console Charset: " + Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public static void docTopdf(String docPath, String docxPath) throws Exception {
        try {
            WordprocessingMLPackage pkg = Docx4J.load(new File(docPath));

            Mapper fontMapper = new IdentityPlusMapper();
            fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
            fontMapper.put("宋体", PhysicalFonts.get("SimSun"));
            fontMapper.put("微软雅黑", PhysicalFonts.get("Microsoft Yahei"));
            fontMapper.put("黑体", PhysicalFonts.get("SimHei"));
            fontMapper.put("楷体", PhysicalFonts.get("KaiTi"));
            fontMapper.put("新宋体", PhysicalFonts.get("NSimSun"));
            fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
            fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
            fontMapper.put("仿宋", PhysicalFonts.get("FangSong"));
            fontMapper.put("幼圆", PhysicalFonts.get("YouYuan"));
            fontMapper.put("华文宋体", PhysicalFonts.get("STSong"));
            fontMapper.put("华文中宋", PhysicalFonts.get("STZhongsong"));
            fontMapper.put("等线", PhysicalFonts.get("SimSun"));
            fontMapper.put("等线 Light", PhysicalFonts.get("SimSun"));
            fontMapper.put("华文琥珀", PhysicalFonts.get("STHupo"));
            fontMapper.put("华文隶书", PhysicalFonts.get("STLiti"));
            fontMapper.put("华文新魏", PhysicalFonts.get("STXinwei"));
            fontMapper.put("华文彩云", PhysicalFonts.get("STCaiyun"));
            fontMapper.put("方正姚体", PhysicalFonts.get("FZYaoti"));
            fontMapper.put("方正舒体", PhysicalFonts.get("FZShuTi"));
            fontMapper.put("华文细黑", PhysicalFonts.get("STXihei"));
            fontMapper.put("宋体扩展", PhysicalFonts.get("simsun-extB"));
            fontMapper.put("仿宋_GB2312", PhysicalFonts.get("FangSong_GB2312"));
            fontMapper.put("新細明體", PhysicalFonts.get("SimSun"));
            pkg.setFontMapper(fontMapper);

            Docx4J.toPDF(pkg, new FileOutputStream(docxPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Docx4JException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void changeSystemConsoleCharset(Charset charset) throws Exception {
        Properties props = System.getProperties();
        props.put("sun.stdout.encoding", charset.name());
        Field field = props.getClass().getDeclaredField("sun.stdout.encoding");
        AccessibleObject.setAccessible(new Field[]{field}, true);
        field.set(props, charset.name());
    }
}
