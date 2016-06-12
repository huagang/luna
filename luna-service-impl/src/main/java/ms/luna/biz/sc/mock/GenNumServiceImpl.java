package ms.luna.biz.sc.mock;


import java.io.UnsupportedEncodingException;


import com.visualbusiness.gennum.service.GenNumService;

import ms.luna.biz.util.VbMD5;

public class GenNumServiceImpl implements GenNumService {

	public String currentNum(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean existKey(String arg0) {
		// TODO Auto-generated method stub
		return Boolean.TRUE;
	}

	public String generateNum(String arg0) {
		return VbMD5.convertRandomMD5Code(arg0);
	}

	
	public String generateNum(String arg0, Long arg1) {
		return VbMD5.convertRandomMD5Code(arg0);
	}

	
	public String generateNum(String arg0, Long arg1, Integer arg2) {
		return VbMD5.convertRandomMD5Code(arg0);
	}

	
	public String getExistKeys() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Boolean registKey(String arg0) {
		// TODO Auto-generated method stub
	  return Boolean.TRUE;
	}

	
	public Boolean removeKey(String arg0) {
		// TODO Auto-generated method stub
	  return Boolean.TRUE;
	}

	
	public String resetNum(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
