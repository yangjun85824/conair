package com.yyld.conair.druid.utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * @ClassName CutPictureUtil
 * @Description 图片截取帮助类
 * @Author yxl
 * @Date 2022/7/25 14:17
 * @Vresion 1.0
 **/
public final class CutPictureUtil {

    private String srcpath;

    private String subpath;

    private String imageType;

    private int x;

    private int y;

    private int width;

    private int height;

    public CutPictureUtil() {
    }

    public CutPictureUtil(String srcpath, int x, int y, int width, int height) {
        this.srcpath = srcpath;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSrcpath() {
        return srcpath;
    }

    public void setSrcpath(String srcpath) {
        this.srcpath = srcpath;
        if (srcpath != null) {
            this.imageType = srcpath.substring(srcpath.indexOf(".") + 1, srcpath.length());
        }
    }

    public String getSubpath() {
        return subpath;
    }

    public void setSubpath(String subpath) {
        this.subpath = subpath;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public InputStream cut() throws IOException {
        FileInputStream is = null;
        ImageInputStream iis = null;
        try {
            is = new FileInputStream(srcpath);
            this.setSrcpath(srcpath);
            Iterator it = ImageIO.getImageReadersByFormatName(this.imageType);
            ImageReader reader = (ImageReader) it.next();

            iis = ImageIO.createImageInputStream(is);

            reader.setInput(iis, true);

            ImageReadParam param = reader.getDefaultReadParam();

            Rectangle rect = new Rectangle(x, y, width, height);

            param.setSourceRegion(rect);

            BufferedImage bi = reader.read(0, param);

            return bufferedImageToInputStream(bi);
//            ImageIO.write(bi, this.imageType, new File(subpath));


        } finally {
            if (is != null) {
                is.close();
            }
            if (iis != null) {
                iis.close();
            }
        }
    }

    /**
     * 将BufferedImage转换为InputStream
     *
     * @param image
     * @return
     */
    public InputStream bufferedImageToInputStream(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        CutPictureUtil o = new CutPictureUtil("D:\\9.jpg", 100, 200, 1000, 1000);
        o.setSubpath("D:\\1.png");
        try {
            System.out.println(o.cut());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}