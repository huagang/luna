package ms.luna.biz.dao.custom;

import java.sql.ResultSet;
import java.util.List;

import ms.luna.biz.dao.MsShowAppDAOBase;
import ms.luna.biz.dao.custom.model.MsShowAppParameter;
import ms.luna.biz.dao.custom.model.MsShowAppResult;

public interface MsShowAppDAO extends MsShowAppDAOBase {
	
	public List<MsShowAppResult> selectShowAppWithFilter(MsShowAppParameter parameter);
	
	public int selectIdByName(String name);
	
}