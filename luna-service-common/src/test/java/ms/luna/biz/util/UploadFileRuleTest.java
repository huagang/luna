package ms.luna.biz.util;

import ms.luna.biz.cons.VbConstant;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-11
 */
public class UploadFileRuleTest {


    @Test
    public void testGetFileExtension() {
        String ext = VbConstant.UploadFileRule.getFileExtention("hello.mp4");
        assertEquals("get file extension error", ".mp4", ext);
    }

    @Test
    public void testFileFormat() {
        String fileName = "hello.mp4";
        String type = "video";
        String ext = VbConstant.UploadFileRule.getFileExtention(fileName);
        assertEquals("failed to judge file format", VbConstant.UploadFileRule.isValidFormat(type, ext), true);
    }
}
