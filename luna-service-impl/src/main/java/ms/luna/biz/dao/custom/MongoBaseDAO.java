package ms.luna.biz.dao.custom;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;

import edu.emory.mathcs.backport.java.util.Arrays;
import ms.biz.common.MongoConnector;

public class MongoBaseDAO {
	
	@Autowired
	MongoConnector mongoConnector;	
	
	@PostConstruct
	public void init() {

	}
	
	@PreDestroy
	public void destroy() {
		
	}

}
