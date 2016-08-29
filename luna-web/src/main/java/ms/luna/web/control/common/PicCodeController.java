package ms.luna.web.control.common;

import com.alibaba.fastjson.JSONObject;
import ms.luna.biz.sc.PicCodeService;
import ms.luna.biz.util.CheckCodeUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by SDLL18 on 16/8/29.
 */
@Controller
@RequestMapping("/common/picCode")
public class PicCodeController {

    private static final String CODE_REGEX = "^[0-9]{13}_[0-9]{5}$";

    @Autowired
    private PicCodeService picCodeService;

    @RequestMapping(method = RequestMethod.GET, value = "/getCode/{key}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPicCode(@PathVariable String key) {
        try {
            Pattern pattern = Pattern.compile(CODE_REGEX);
            Matcher matcher = pattern.matcher(key);
            Boolean result = matcher.matches();
            if (!result) return new byte[1];
            Pair<String, BufferedImage> code = CheckCodeUtil.generateCheckCode();
            result = picCodeService.saveCode(key, code.getLeft());
            if (!result) return new byte[1];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(code.getRight(), "jpg", out);
            return out.toByteArray();
        } catch (Exception e) {
            return new byte[1];
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/checkCode/{key}")
    @ResponseBody
    public JSONObject checkPicCode(@PathVariable String key,
                                   @RequestParam String code) {
        return picCodeService.checkCode(key,code);
    }

}
