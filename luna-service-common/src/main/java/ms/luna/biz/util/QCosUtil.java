package ms.luna.biz.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qcloud.cosapi.api.CosCloud;
import ms.luna.biz.cons.QCosConfig;
import ms.luna.biz.cons.ErrorCode;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-07
 */
public class QCosUtil {

    private final static Logger logger = Logger.getLogger(QCosUtil.class);

    public static final String ACCESS_URL = QCosConfig.ACCESS_URL;

    private static QCosUtil instance = new QCosUtil();
    private CosCloud cosCloud;

    private QCosUtil() {
        cosCloud = new CosCloud(QCosConfig.APP_ID, QCosConfig.SECRET_ID,
                QCosConfig.SECRET_KEY, QCosConfig.DEFAULT_TIMEOUT_IN_SECONDS);
    }

    public static synchronized QCosUtil getInstance() {
        return instance;
    }

    public JSONObject uploadFileFromLocalFile(String bucket, String remoteFilePath, String localFilePath, boolean overwrite) {

        JSONObject data = new JSONObject();
        try {
            if(overwrite) {
                cosCloud.deleteFile(bucket, remoteFilePath);
            }
            String ret = cosCloud.uploadFile(bucket, remoteFilePath, localFilePath);
            logger.debug(ret);
            JSONObject retJson = JSON.parseObject(ret);
            if(retJson.getIntValue("code") == 0) {
                data.put(ACCESS_URL, retJson.getJSONObject("data").getString(ACCESS_URL));
            } else {
                return FastJsonUtil.error(retJson.getIntValue("code"), "上传失败");
            }
        } catch (Exception e) {
            logger.error("Failed to upload file: " + remoteFilePath, e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "上传文件失败");
        }
        return FastJsonUtil.sucess("上传成功", data);
    }

    /**
     * code == 0, succeed
     * if file exist on cos, it will fail with error code
     * the caller should ensure that path should not exist
     * @param bucket
     * @param remoteFilePath
     * @param inputStream
     * @return
     */
    public JSONObject uploadFileFromStream(String bucket, String remoteFilePath, InputStream inputStream, boolean overwrite) {

        JSONObject data = new JSONObject();
        try {
            if(overwrite) {
                cosCloud.deleteFile(bucket, remoteFilePath);
            }
            logger.debug("remoteFilePath: " + remoteFilePath);
            String ret = cosCloud.uploadFile(bucket, remoteFilePath, inputStream);
            logger.debug(ret);
            JSONObject retJson = JSON.parseObject(ret);
            if(retJson.getIntValue("code") == 0) {
                data.put(ACCESS_URL, retJson.getJSONObject("data").getString(ACCESS_URL));
            } else {
                return FastJsonUtil.error(retJson.getIntValue("code"), "上传失败");
            }
        } catch (Exception e) {
            logger.error("Failed to upload file: " + remoteFilePath, e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "上传文件失败");
        }
        return FastJsonUtil.sucess("上传成功", data);
    }

    public JSONObject uploadFileFromByte(String bucket, String remoteFilePath, byte[] content, boolean overwrite) {
        ByteArrayInputStream bais = new ByteArrayInputStream(content);
        return this.uploadFileFromStream(bucket, remoteFilePath, bais, overwrite);
    }

    public JSONObject readAccessUrl(String bucket, String remoteFilePath) {
        JSONObject data = new JSONObject();
        try {
            String ret = cosCloud.getFileStat(bucket, remoteFilePath);
            JSONObject retJson = JSON.parseObject(ret);
            if(retJson.getIntValue("code") == 0) {
                data.put(ACCESS_URL, retJson.getJSONObject("data").getString(ACCESS_URL));
                return FastJsonUtil.sucess("", data);
            }
        } catch (Exception e) {
            logger.error("Failed to read access url", e);
        }
        return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "获取访问地址失败");
    }

    public JSONObject existFile(String bucket, String remoteFilePath) {
        JSONObject data = new JSONObject();
        try {
            String ret = cosCloud.getFileStat(bucket, remoteFilePath);
            JSONObject retJson = JSON.parseObject(ret);
            if(retJson.getIntValue("code") == 0) {
                data.put("exist", true);
            } else {
                data.put("exist", false);
            }
        } catch (Exception e) {
            logger.error("Failed to get file stat", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "内部错误,请重试");
        }
        return FastJsonUtil.sucess("", data);
    }

    public JSONObject deleteFile(String bucket, String remoteFilePath) {
        try {
            String ret = cosCloud.deleteFile(bucket, remoteFilePath);
            logger.debug(ret);
        } catch (Exception ex) {
            logger.error("Failed to delete file");
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "删除文件失败");
        }
        return FastJsonUtil.sucess("删除成功");
    }

    public JSONObject createFolder(String bucket, String remotePath) {
        try {
            String ret = cosCloud.createFolder(bucket, remotePath);
            logger.debug(ret);
        } catch (Exception e) {
            logger.error("Failed to create folder", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "删除文件夹失败");
        }
        return FastJsonUtil.sucess("创建文件夹成功");
    }

    public JSONObject deleteFolder(String bucket, String remotePath) {

        try {
            // remotePath should end with "/", otherwise will fail
            if(! remotePath.endsWith("/")) {
                remotePath += "/";
            }
            String ret = cosCloud.deleteFolder(bucket, remotePath);
            logger.debug(ret);
        } catch (Exception e) {
            logger.error("Failed to delete folder: " + remotePath, e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "删除文件夹失败");
        }
        return FastJsonUtil.sucess("删除文件夹成功");
    }



    /**
     * cos does not provide a delete Recursive api
     *
     * @param bucket
     * @param remotePath
     * @return
     */
    public JSONObject deleteFolderRecursive(String bucket, String remotePath) {
        String context = "";
        Boolean hasMore = true;
        if(! remotePath.endsWith("/")) {
            remotePath += "/";
        }
        try {
            while(hasMore) {
                String folderList = cosCloud.getFolderList(bucket, remotePath, 10, context, 0, CosCloud.FolderPattern.Both);
                JSONObject folderListJson = JSON.parseObject(folderList);
                if (folderListJson.getIntValue("code") != 0) {
                    logger.error("Failed to get folder list, err: " + folderList);
                    return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "删除文件夹失败");
                }
                logger.debug("folderList: " + folderList);
                JSONObject dataJson = folderListJson.getJSONObject("data");
                context = dataJson.getString("context");
                hasMore = dataJson.getBoolean("has_more");
                if (dataJson.getIntValue("dircount") > 0 || dataJson.getIntValue("filecount") > 0) {
                    JSONArray infos = dataJson.getJSONArray("infos");
                    for (int i = 0; i < infos.size(); i++) {
                        JSONObject info = infos.getJSONObject(i);
                        String name = info.getString("name");
                        if (info.containsKey("filesize")) {
                            // it's a file
                            String filePath = remotePath + name;
                            logger.debug("Delete file: " + filePath);
                            cosCloud.deleteFile(bucket, filePath);
                        } else {
                            // it's a directory
                            String newRemotePath = remotePath + name;
                            deleteFolderRecursive(bucket, newRemotePath);
                        }
                    }
                } else {
                    deleteFolder(bucket, remotePath);
                }
            }
            deleteFolder(bucket, remotePath);
        } catch (Exception e) {
            logger.error("Failed to delete folder", e);
            return FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "删除文件夹失败");
        }

        return FastJsonUtil.sucess("删除文件夹成功");
    }

    public static void main(String[] args) {
        String remoteDirectory = "/luna/dev";
        JSONObject jsonObject = QCosUtil.getInstance().deleteFolderRecursive(QCosConfig.LUNA_BUCKET, remoteDirectory);
    }

}
