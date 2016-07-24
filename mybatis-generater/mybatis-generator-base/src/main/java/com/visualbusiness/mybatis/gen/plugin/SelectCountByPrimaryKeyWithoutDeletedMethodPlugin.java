package com.visualbusiness.mybatis.gen.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;

import com.visualbusiness.mybatis.gen.dao.SpringAnnotationalDAOTemplate;


/**
 * 无视逻辑删除项目，追加取得件数的方法
 * @author 马建华
 *
 */
public class SelectCountByPrimaryKeyWithoutDeletedMethodPlugin extends SelectByPrimaryKeyWithoutDeletedMethodPlugin{

	@Override
	protected void addQueryLine(Method m, IntrospectedTable introspectedTable) {

		StringBuilder sb = new StringBuilder();
		sb.setLength(0);
		sb.append("Integer count = (Integer) "); //$NON-NLS-1$
		sb.append(new SpringAnnotationalDAOTemplate().getQueryForObjectMethod(introspectedTable
				.getIbatis2SqlMapNamespace(), introspectedTable
				.getCountByExampleStatementId(), "criteria")); //$NON-NLS-1$
		m.addBodyLine(sb.toString());
		m.addBodyLine("return count;"); //$NON-NLS-1$
	}

	@Override
	protected String getMethodName() {
		return "selectCountByPrimaryKeyWithoutDeleted";
	}

	@Override
	protected FullyQualifiedJavaType getReturnType(
			IntrospectedTable introspectedTable) {
		return FullyQualifiedJavaType.getIntInstance();
	}
}
