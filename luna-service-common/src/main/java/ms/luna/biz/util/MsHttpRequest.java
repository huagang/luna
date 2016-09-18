package ms.luna.biz.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MsHttpRequest {

	private static final int BUFFER_SIZE = 1024;

	/** 
     * 使用post方式获取数据 
     *  
     * @param uri 
     *            URL包括参数，http://HOST/XX?XX=XX&XXX=XXX 
     * @param charset 
     * @return 
     */  
    public static URLConnection sendPost(String url) {  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection connection = realUrl.openConnection();  
            // 设置通用的请求属性  
            connection.setRequestProperty("accept", "*/*");  
            connection.setRequestProperty("connection", "Keep-Alive");  
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 建立实际的连接  
            connection.connect();
            return connection;
        } catch (Exception e) {  
            e.printStackTrace();
            throw new RuntimeException("请求异常：url=" + url);
        }  
    }

    /** 
     * 使用Get方式获取数据 
     *  
     * @param url 
     *            URL包括参数，http://HOST/XX?XX=XX&XXX=XXX 
     * @param charset 
     * @return 
     */  
    public static URLConnection sendPost(String url, String userAgent) {  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection connection = realUrl.openConnection();  
            // 设置通用的请求属性  
            connection.setRequestProperty("accept", "*/*");  
            connection.setRequestProperty("connection", "Keep-Alive");  
            connection.setRequestProperty("user-agent", userAgent);
            System.out.println("userAgent=" + userAgent);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 建立实际的连接  
            connection.connect();  
            return connection;
        } catch (Exception e) {  
            e.printStackTrace();
            throw new RuntimeException("请求异常：url=" + url);
        }  
    }

    public static String conver2JsonString(InputStream inStream) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[BUFFER_SIZE];
		int len = 0;
		while((len = inStream.read(buffer, 0, BUFFER_SIZE)) != -1){
			outputStream.write(buffer, 0, len);
		}
		inStream.close();
		return new String(outputStream.toByteArray(), "UTF-8");
	}

}
     

