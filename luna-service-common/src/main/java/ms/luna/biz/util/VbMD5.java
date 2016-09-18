package ms.luna.biz.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * VbMD5
 *
 * @author Mark
 *
 */
public final class VbMD5 {
	private static Random random = new Random(Long.MAX_VALUE);
	// 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5","6", "7", "8", "9",
    		"a", "b", "c", "d", "e", "f","g","h","i", "j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","_",
    		"A", "B", "C", "D", "E", "F","G","H","I", "J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","-"};
	 // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 64;
        int iD2 = iRet % 64;
        return strDigits[iD1] + strDigits[iD2];
    }
	 // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }
    
	public static String convertRandomMD5Code(String strObj) {
        String resultString = null;
        try {
        	StringBuilder sb = new StringBuilder(generateToken());
        	strObj = new String(sb.toString() + strObj + System.currentTimeMillis());
        	System.out.println(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

	public static String convertFixMD5Code(String strObj) {
        String resultString = null;
        try {
        	strObj = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

	public static String generateToken() {
        String resultString = null;
        try {
        	StringBuilder sb = new StringBuilder();
        	for (int i = 0; i < 5; i++) {
        		sb.append(random.nextLong());
        	}
        	sb.append(System.currentTimeMillis());
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(sb.toString().getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
	
	 /** 
     * 通用 MD5 加密 
     */  
    public static String getCommonMD5Str(String str) {  
        MessageDigest messageDigest = null;  
  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
            messageDigest.reset();  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("NoSuchAlgorithmException caught!");  
            System.exit(-1);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
  
        byte[] byteArray = messageDigest.digest();  
  
        StringBuffer md5StrBuff = new StringBuffer();  
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
  
        return md5StrBuff.toString();  
    } 

	private VbMD5() {
	}

    public static void main(String[] args) {
        System.out.println(VbMD5.convertFixMD5Code("shawn123"));
    }
}

