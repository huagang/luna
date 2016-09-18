package ms.biz.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * MybatisDAO
 * @author Mark(VB Inc.)
 *
 */
public abstract class MsBaseDAO extends SqlMapClientDaoSupport{
	
	/**
	 * SqlMapClient
	 * @param sqlMapClient sqlMapClient
	 */
	@Autowired
	public final void setClient(SqlMapClient sqlMapClient){
		setSqlMapClient(sqlMapClient);
	}
}
