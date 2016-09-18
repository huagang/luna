package ms.luna.biz.util;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.QCosConfig;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-08
 */
public class QCosUtilTest {

    private QCosUtil QCosUtil;
    private String bucket;

    private String localDirectory = "/data1/luna/test";
    private String localFileName = "index.html";
    private String localFilePath = localDirectory + "/" + localFileName;

    private String remoteDirectory = "/dev/h5/test";
    private String remoteFileName = "index.html";
    private String remoteFilePath = remoteDirectory + "/" + remoteFileName;

    @Before
    public void setUp() throws IOException {
        QCosUtil = QCosUtil.getInstance();
        bucket = QCosConfig.LUNA_BUCKET;
        File directory = new File(localDirectory);
        if(! directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(localDirectory, localFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write("This is a test".getBytes());
    }


    @Test
    public void testUploadFileFromStream() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(localFilePath);
        JSONObject jsonObject = QCosUtil.uploadFileFromStream(bucket, remoteFilePath, fis, true);
        assertEquals("Upload file from stream failed", 0, jsonObject.getIntValue("code"));
        QCosUtil.deleteFile(bucket, remoteFilePath);
    }

    @Test
    public void testUploadAndDeleteFile() {
        String remoteFilePath = remoteDirectory + "/" + remoteFileName;
        String localFilePath = localDirectory + "/" + localFileName;
        JSONObject jsonObject = QCosUtil.uploadFileFromLocalFile(bucket, remoteFilePath, localFilePath, true);
        assertEquals("Upload file from local file failed", 0, jsonObject.getIntValue("code"));
        jsonObject = QCosUtil.deleteFile(bucket, remoteFilePath);
        assertEquals("Delete file failed", 0, jsonObject.getIntValue("code"));
    }

    @Test
    public void testCreateAndDeleteFolder() {
        JSONObject jsonObject = QCosUtil.createFolder(bucket, remoteDirectory);
        assertEquals("Create folder failed", 0, jsonObject.getIntValue("code"));
        jsonObject = QCosUtil.deleteFolder(bucket, remoteDirectory);
        assertEquals("Delete folder failed", 0, jsonObject.getIntValue("code"));
    }

    @Test
    public void testDeleteFolderRecursive() {
        String remoteDirectory = "/dev/h5/test";
        for(int i = 0; i < 15; i++) {
            String childDirectory = "subdir" + i;
            String remotePath = remoteDirectory + "/" + childDirectory;
            QCosUtil.createFolder(bucket, remotePath);
        }
        for(int i = 0; i < 15; i++) {
            String childFile = "file" + i;
            String remoteFilePath = remoteDirectory + "/" + childFile;
            QCosUtil.uploadFileFromLocalFile(bucket, remoteFilePath, localFilePath, true);
        }
        JSONObject jsonObject = QCosUtil.deleteFolderRecursive(bucket, remoteDirectory);
        assertEquals("Delete folder recursively failed", 0, jsonObject.getIntValue("code"));
    }

}
