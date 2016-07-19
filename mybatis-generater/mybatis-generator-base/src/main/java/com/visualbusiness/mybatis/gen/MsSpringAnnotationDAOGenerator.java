package com.visualbusiness.mybatis.gen;

import com.visualbusiness.mybatis.gen.dao.SpringAnnotationalDAOGenerator;

/**
 * @author Ma Jian Hua
 */
public class MsSpringAnnotationDAOGenerator extends
		SpringAnnotationalDAOGenerator {

	public MsSpringAnnotationDAOGenerator() {
		super(new MsSpringDAOTemplate());
	}
}
