package ms.luna.biz.util;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.VbConstant;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-02
 */
public class VODUtilTest {

    private String vodPath = "/Users/chenshangan/Downloads/vod_resource";
    private String videoFileName = "sp.mp4";
    private String videoFileFullName = vodPath + "/" + videoFileName;

    @Test
    public void testCaptureFrame() throws IOException, FrameGrabber.Exception, InterruptedException {
        FileInputStream fis = new FileInputStream(videoFileFullName);
        System.out.println("size: " + fis.available());
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(videoFileFullName);
        Java2DFrameConverter java2DFrameConverter = new Java2DFrameConverter();

//        frameGrabber.setAudioChannels(0);
        frameGrabber.start();
        for(int i = 0; i < 10; i ++) {
            Frame frame = frameGrabber.grab();
            BufferedImage bufferedImage = java2DFrameConverter.convert(frame);
            if(bufferedImage != null) {
                ImageIO.write(bufferedImage, "png", new File(String.format("%s/out%d.png", vodPath, i)));
            } else {
                System.out.println("empty " + i);
            }
            Thread.sleep(100);
        }

        frameGrabber.stop();
    }

    @Test
    public void testUploadVod() throws IOException {
        VODUtil vodUtil = VODUtil.getInstance();
        JSONObject jsonObject = vodUtil.upload2Cloud(videoFileFullName, "luna/dev/poi/test", videoFileName);
        System.out.println(jsonObject);
        if(jsonObject.getIntValue("code") == 0) {
            JSONObject data = jsonObject.getJSONObject("data");
            String fileId = data.getString("vod_file_id");
            JSONObject playUrlJson = vodUtil.getVodPlayUrls(fileId);
            if(playUrlJson.getIntValue("code") == 0) {
                JSONObject playUrlData = playUrlJson.getJSONObject("data");
                String url = playUrlData.getString(VbConstant.VOD_DEFINITION.NAME.VOD_ORIGINAL_FILE_URL);
                System.out.println(url);
            }

        }
    }

}
