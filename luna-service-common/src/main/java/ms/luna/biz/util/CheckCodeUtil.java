package ms.luna.biz.util;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-29
 */
public class CheckCodeUtil {

    private final static Logger logger = Logger.getLogger(CheckCodeUtil.class);

    private final static int CODE_LENGTH = 4;
    private final static String BASE_CODE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final static int BASE_CODE_SIZE = BASE_CODE.length();

    public static Pair<String, BufferedImage> generateCheckCode(){
        int width = 70;
        int height = 30;
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        Random random = new Random();
//		Color background = getRandColor(200, 250);
        //边框色
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);
        Font font = new Font("Times New Roman", Font.HANGING_BASELINE, 26);
        g.setFont(font);
        //背景色
        g.setColor(Color.WHITE);
        g.fillRect(1, 1, width - 2, height - 2);
        Color fontColor = new Color(0xFF, 0x34, 0xB3);
        g.setColor(fontColor);
        for(int i = 0; i < 3; i++){
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(20);
            int y2 = random.nextInt(10);
            g.drawLine(x1, y1, x2, y2);
        }

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < CODE_LENGTH; i++){
            int idx = random.nextInt(BASE_CODE_SIZE);
            String crtCode = BASE_CODE.substring(idx, idx + 1);
            g.drawString(crtCode, i * 15 + 5, 24);
            sb.append(crtCode);
        }

        g.dispose();

        return Pair.of(sb.toString(), buffImg);

    }


    public static Color getRandColor(int downLimit, int upLimit){
        Random random = new Random();
        if(downLimit < 0 || downLimit > 255){
            downLimit = 255;
        }
        if(upLimit < 0 || upLimit > 255){
            upLimit = 255;
        }
        int d = upLimit - downLimit;
        int r = downLimit + random.nextInt(d);
        int g = downLimit + random.nextInt(d);
        int b = downLimit + random.nextInt(d);
        return new Color(r, g, b);
    }

    public static ByteArrayInputStream convertImageToStream(BufferedImage image){

        ByteArrayInputStream inputStream = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpeg", bos);
//			FileOutputStream fos = new FileOutputStream("e:/a.jpg");
//			fos.write(bos.toByteArray());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error("convertImageToStream() failed", e);
        }

        byte[] bts = bos.toByteArray();
        inputStream = new ByteArrayInputStream(bts);

        return inputStream;
    }

    public static void main(String[] args){
        Pair<String, BufferedImage> checkCode = CheckCodeUtil.generateCheckCode();
        CheckCodeUtil.convertImageToStream(checkCode.getRight());
    }
}
