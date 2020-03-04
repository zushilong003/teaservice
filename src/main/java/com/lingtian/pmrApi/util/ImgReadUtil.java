package com.lingtian.pmrApi.util;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImgReadUtil {
    /**
     * 裁剪图片：去掉黑边
     */
    public static BufferedImage clipImage(BufferedImage srcImage) {
        return srcImage.getSubimage(1, 1, srcImage.getWidth() - 1, srcImage.getHeight() - 1);
    }

    /**
     * 灰度化
     */
    public static BufferedImage grayImage(BufferedImage srcImage) {
        return copyImage(srcImage, new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY));
    }

    /**
     * 二值化
     */
    public static BufferedImage binaryImage(BufferedImage srcImage) {
        return copyImage(srcImage, new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY));
    }

    public static BufferedImage copyImage(BufferedImage srcImage, BufferedImage destImage) {
        for (int y = 0; y < srcImage.getHeight(); y++) {
            for (int x = 0; x < srcImage.getWidth(); x++) {
                destImage.setRGB(x, y, srcImage.getRGB(x, y));
            }
        }
        return destImage;
    }

    /**
     * 除去图片的背景
     *
     * @param imgUrl
     * @param resUrl
     */
    public static void removeBackground(String imgUrl, String resUrl) {
        //定义一个临界阈值
        int threshold = 300;
        try {
            BufferedImage img = ImageIO.read(new File(imgUrl));
            int width = img.getWidth();
            int height = img.getHeight();
            for (int i = 1; i < width; i++) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        Color color = new Color(img.getRGB(x, y));
                        System.out.println("red:" + color.getRed() + " | green:" + color.getGreen() + " | blue:" + color.getBlue());
                        int num = color.getRed() + color.getGreen() + color.getBlue();
                        if (num >= threshold) {
                            img.setRGB(x, y, Color.WHITE.getRGB());
                        }
                    }
                }
            }
            for (int i = 1; i < width; i++) {
                Color color1 = new Color(img.getRGB(i, 1));
                int num1 = color1.getRed() + color1.getGreen() + color1.getBlue();
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        Color color = new Color(img.getRGB(x, y));

                        int num = color.getRed() + color.getGreen() + color.getBlue();
                        if (num == num1) {
                            img.setRGB(x, y, Color.BLACK.getRGB());
                        } else {
                            img.setRGB(x, y, Color.WHITE.getRGB());
                        }
                    }
                }
            }
            File file = new File(resUrl);
            if (!file.exists()) {
                File dir = file.getParentFile();
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ImageIO.write(img, "jpg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public static String readImgNum(String path) {
        String result = "";
        HttpURLConnection httpUrl = null;
        try {
            // path 识别图片的路径（修改为自己的图片路径）
            URL url = new URL(path);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            // 语言库位置（修改为跟自己语言库文件夹的路径）
            final String lagnguagePath = "src/main/resources/testdata";

            ITesseract instance = new Tesseract();

            //设置训练库的位置
            instance.setDatapath(lagnguagePath);
            //chi_sim ：简体中文， eng    根据需求选择语言库
            instance.setLanguage("eng");

            long startTime = System.currentTimeMillis();

            BufferedImage image = ImageIO.read(httpUrl.getInputStream());
            BufferedImage processedImage = grayImage(image);
            ImageIO.write(processedImage, "png", new File("F://output.png"));
            result = instance.doOCR(processedImage);
            long endTime = System.currentTimeMillis();
            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
            System.out.println("result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpUrl != null) {
                httpUrl.disconnect();
            }
        }
        return result;
    }


    public static void main(String args[]) {
        //  easyOrc("F:/img/ihD8BmCFsZHQ3kV.png");
        //https://www.5169168.com/captcha/3KcpEdYY5I0xcLs.png
        readImgNum("https://www.5169168.com/captcha/NWkx2K46lbx0z66.png");
        //removeBackground("F:\\img\\ihD8BmCFsZHQ3kV.png","F://dist.png");

    }
}
