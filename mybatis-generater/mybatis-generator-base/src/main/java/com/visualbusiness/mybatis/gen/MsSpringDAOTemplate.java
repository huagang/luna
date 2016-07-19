package com.visualbusiness.mybatis.gen;

import com.visualbusiness.mybatis.gen.dao.SpringAnnotationalDAOTemplate;

public class MsSpringDAOTemplate extends SpringAnnotationalDAOTemplate {

	public MsSpringDAOTemplate() {
		// FIXME: 1/4 自定义包的位置
		super("ms.biz.common.MsBaseDAO");
	}
}
