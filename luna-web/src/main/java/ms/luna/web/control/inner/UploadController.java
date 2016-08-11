package ms.luna.web.control.inner;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.cons.ErrorCode;
import ms.luna.biz.cons.QCosConfig;
import ms.luna.biz.cons.VbConstant;
import ms.luna.biz.sc.VodPlayService;
import ms.luna.biz.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-08-08
 */

@Controller
@RequestMapping("/inner")
public class UploadController {

    private final static Logger logger = Logger.getLogger(UploadController.class);

    private Random random = new Random();
    private Set<String> validFileExtention;
    private QCosUtil qCosUtil;

    @Autowired
    private VodPlayService vodPlayService;

    @PostConstruct
    public void init() {
        validFileExtention = new HashSet<>();
        validFileExtention.add("jpg");
        validFileExtention.add("jpeg");
        validFileExtention.add("png");
        qCosUtil = qCosUtil.getInstance();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadPic")
    @ResponseBody
    public JSONObject uploadPic(@RequestParam(required = true, value ="pic") MultipartFile file,
            @RequestParam(required = true, value="app_id") String appId,
            HttpServletRequest request) throws IOException {

        if (file == null || file.isEmpty()) {
            return FastJsonUtil.error("-1", "文件不能为空");
        }
        // 文件太大超过预定的10M
        if (file.getSize() > COSUtil.图片上传大小分界线1M) {
            return FastJsonUtil.error("-1", "文件大小不能超过1M");
        }

        String fileName = file.getOriginalFilename();
        int point = fileName.lastIndexOf(".");
        if (point == -1 || point == fileName.length()-1) {
            return FastJsonUtil.error("-1", "文件类型不正确");
        }
        String extName = fileName.substring(point+1);
        extName = extName.toLowerCase();
        if (extName.length() == 0 || (! validFileExtention.contains(extName))) {
            return FastJsonUtil.error("-1", "文件类型必须是" + validFileExtention.toString());
        }

        try {
            JSONObject result = COSUtil.getInstance().upload2CloudDirect(file, COSUtil.getLunaImgRootPath() + "/" + appId + "/");
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if(result.containsKey("data")) {
                JSONObject data = result.getJSONObject("data");
                data.put("height", bufferedImage.getHeight());
                data.put("width", bufferedImage.getWidth());
            }
            return result;
        } catch (Exception e) {
            logger.error("Failed to upload pic", e);
            return FastJsonUtil.error(ErrorCode.INVALID_PARAM, "内部错误");
        }
    }


    /**
     *
     * cos path rule:
     * 		bucket/{env}/{type}/{resource_type}/{resource_id}/{filename}
     * vod path rule(path is limited to at most 4 level by vod):
     * 		bucket/{env}/{resource_type}/{resource_id}/{filename}
     * @param file
     * @param type file type: pic, video, audio
     * @param resourceType resource owner, i.e : app, poi, etc
     * @param resourceId resource unique id, i.e : appId, poiId, etc
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    @ResponseBody
    public JSONObject uploadFile2Cloud(@RequestParam(required = true, value = "file") MultipartFile file,
                                 @RequestParam(required = true, value = "type") String type,
                                 @RequestParam(required = true, value = "resource_type") String resourceType,
                                 @RequestParam(required = false, value = "resource_id") String resourceId,
                                 HttpServletRequest request) throws IOException {
        try{
            // 类型检查
            if(! VbConstant.UploadFileRule.isValidFileType(type)) {
                return FastJsonUtil.error(ErrorCode.INVALID_PARAM,
                        "文件类型不合法,合法的文件类型:" + VbConstant.UploadFileRule.getValidFileTypes());
            }
            // 后缀名获取和检查
            String ext = VbConstant.UploadFileRule.getFileExtention(file.getOriginalFilename());
            if (! VbConstant.UploadFileRule.isValidFormat(type, ext)) {
                return FastJsonUtil.error("-1", "文件格式不支持");
            }
            // 文件大小检查
            if(! VbConstant.UploadFileRule.isValidSize(type, file.getSize())){
                return FastJsonUtil.error("-1", "file size is larger");
            }
            // 目录
            // TODO: should check UploadFileRule.isValidPath(resourceType) ?
            if(StringUtils.isBlank(resourceType)){
                resourceType = getDefaultPath(type);// 获取默认路径
            }
            String fileName = generateFileName(ext);
            JSONObject result = uploadFile2Cloud(file, type, QCosConfig.LUNA_BUCKET, resourceType, resourceId, fileName);
            logger.debug("method:uploadFile2Cloud, result from server: " + result.toString());

            return result;

        } catch(Exception e){
            logger.error("Failed to upload file", e);
            return FastJsonUtil.error("-1", "上传文件失败");
        }

    }

    private JSONObject uploadFile2Cloud(MultipartFile file,
                                        String type, String bucket, String resourceType, String resourceId, String filename) throws Exception {
        JSONObject result = new JSONObject();
        if(type.equals(VbConstant.UploadFileRule.TYPE_PIC) || type.equals(VbConstant.UploadFileRule.TYPE_AUDIO)) {
            String realPath = String.format("%s/%s/%s", QCosConfig.ENV, type, resourceType);
            if(StringUtils.isNotBlank(resourceId)) {
                realPath += "/" + resourceId;
            }
            realPath += "/" + filename;
            // current naming rule can ensure that file will not exist on cos
            result = qCosUtil.uploadFileFromStream(bucket, realPath, file.getInputStream(), false);
            if(result.getString("code").equals("0")) {
                if (type.equals(VbConstant.UploadFileRule.TYPE_PIC)) {
                    BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
                    if (result.containsKey("data")) {
                        JSONObject data = result.getJSONObject("data");
                        data.put("height", bufferedImage.getHeight());
                        data.put("width", bufferedImage.getWidth());
                    }
                }
                // replace access url
                LunaQCosUtil.replaceAccessUrl(result);
            }

        } else if(type.equals(VbConstant.UploadFileRule.TYPE_VIDEO)){
            String realPath = String.format("/%s%s/%s", bucket, QCosConfig.ENV, resourceType);
            if(StringUtils.isNotBlank(resourceId)) {
                realPath += "/" + resourceId;
            }
            realPath += "/" + filename;
            JSONObject vodResult = VODUtil.getInstance().upload2Cloud(file, realPath, filename, "", 0);

            JSONObject retData = new JSONObject();
            retData.put("original", file.getOriginalFilename());
            retData.put("name", file.getOriginalFilename());
            retData.put("size", file.getSize());
            retData.put("type", VbConstant.UploadFileRule.getFileExtention(file.getOriginalFilename()));
            boolean success = false;

            if(vodResult.getString("code").equals("0")) {
                JSONObject data = vodResult.getJSONObject("data");
                String vodFileId = data.getString("vod_file_id");
                JSONObject urlResult = VODUtil.getInstance().getVodPlayUrls(vodFileId);
                if("0".equals(urlResult.getString("code"))) {
                    String originFileUrl = urlResult.getJSONObject("data").getString("vod_original_file_url");
                    JSONObject param = new JSONObject();
                    param.put("vod_file_id", vodFileId);
                    param.put("vod_original_file_url", originFileUrl);
                    vodPlayService.createVodRecord(param.toString());
                    retData.put(QCosConfig.ACCESS_URL, originFileUrl);
                    retData.put("status", "SUCCESS");
                    result = FastJsonUtil.sucess("", retData);
                    success = true;
                }
            }
            if(! success) {
                retData.put("status", "FAIL");
                result = FastJsonUtil.error(ErrorCode.INTERNAL_ERROR, "");
                result.put("data", retData);
            }

        } else if(type.equals(VbConstant.UploadFileRule.TYPE_ZIP)){
            //TODO: do not process zip now
        }
        return result;
    }

    /**
     * 获得默认文件名(带后缀)
     *
     * @param ext 文件后缀
     * @return
     */
    private String generateFileName(String ext) {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileNameInCloud = date + "_" + StringUtils.leftPad(String.valueOf(random.nextInt()), 10, '0') + ext;
        return fileNameInCloud;
    }

    /**
     * 获得默认的文件路径
     *
     * @param type 文件类型
     * @return
     */
    private String getDefaultPath(String type) {
        return COSUtil.DEFAULT_PATH;
    }

    /**
     * 返回文件的后缀
     *
     * @param type 文件类型
     * @param originalFilename 文件名（带后缀）
     * @return
     */
    private String getExt(String type, String originalFilename) {
        if(type.equals(VbConstant.UploadFileRule.TYPE_PIC)){
            return VbUtility.getExtensionOfPicFileName(originalFilename);
        }
        if(type.equals(VbConstant.UploadFileRule.TYPE_AUDIO)){
            return VbUtility.getExtensionOfAudioFileName(originalFilename);
        }
        if(type.equals(VbConstant.UploadFileRule.TYPE_VIDEO)){
            return VbUtility.getExtensionOfVideoFileName(originalFilename);
        }
        if(type.equals(VbConstant.UploadFileRule.TYPE_ZIP)){
            return VbUtility.getExtensionOfZipFileName(originalFilename);
        }
        return null;
    }
}
