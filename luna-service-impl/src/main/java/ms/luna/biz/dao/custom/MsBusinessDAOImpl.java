package ms.luna.biz.dao.custom;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javassist.compiler.ast.Pair;
import ms.luna.biz.dao.MsBusinessDAOBaseImpl;
import ms.luna.biz.dao.custom.model.MerchantsParameter;
import ms.luna.biz.dao.custom.model.MerchantsResult;
import ms.luna.biz.dao.custom.model.MsBusinessParameter;
import ms.luna.biz.dao.custom.model.MsBusinessResult;


@Repository("msBusinessDAO")
public class MsBusinessDAOImpl extends MsBusinessDAOBaseImpl implements MsBusinessDAO {
	
	private final static Logger logger = Logger.getLogger(MsBusinessDAOImpl.class);

	@Override
	public List<MsBusinessResult> selectBusinessWithFilter(MsBusinessParameter parameter) {
		// TODO Auto-generated method stub
		List<MsBusinessResult> results = getSqlMapClientTemplate().queryForList("ms_business.selectBusinessWithFilter", parameter);
		return results;
	}

	@Override
	public int readTotalBusinessCount() {
		// TODO Auto-generated method stub
		int count = 0;
		try{
			String selectSql = String.format("select count(*) from %s", TABLE_NAME);
			Connection connection = getDataSource().getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(selectSql);
			if(resultSet.next()) {
				count = resultSet.getInt(1);
			}
			statement.close();
		} catch (Exception ex) {
			logger.error("Failed to read total business count", ex);
		}
		
		return count;
	}
	
}