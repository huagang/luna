package ms.luna.biz.dao.custom;

import ms.luna.biz.dao.MsShowAppDAOBase;
import ms.luna.biz.dao.custom.model.MsShowAppParameter;
import ms.luna.biz.dao.custom.model.MsShowAppResult;

import java.util.List;

public interface MsShowAppDAO extends MsShowAppDAOBase {
	
	public List<MsShowAppResult> selectShowAppWithFilter(MsShowAppParameter parameter);
	
	public int selectIdByName(String name);

	public List<MsShowAppResult> selectShowAppByCtgrId(MsShowAppParameter parameter);

	public Integer countShowAppsByCtgrId(MsShowAppParameter msShowAppParameter);
}