package ms.luna.biz.sc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import ms.luna.biz.bl.ShowPageComponentBL;
import ms.luna.biz.sc.ShowPageComponentService;

@Service("showPageComponentService")
public class ShowPageComponentServiceImpl implements ShowPageComponentService {

	@Autowired
	private ShowPageComponentBL showPageComponentBL;
	
	@Override
	public JSONObject getAllComponent() {
		// TODO Auto-generated method stub
		return showPageComponentBL.getAllComponent();
	}

	@Override
	public JSONObject updateComponent(String json) {
		// TODO Auto-generated method stub
		return showPageComponentBL.updateComponent(json);
	}

	@Override
	public JSONObject createComponent(String json) {
		// TODO Auto-generated method stub
		return showPageComponentBL.createComponent(json);
	}

	@Override
	public JSONObject deleteComponent(String name) {
		// TODO Auto-generated method stub
		return showPageComponentBL.deleteComponent(name);
	}

}
