package com.visualbusiness.mybatis.gen.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import com.visualbusiness.mybatis.gen.dao.SpringAnnotationalDAOTemplate;

/**
 * 忽略逻辑删除项目，取得内容。
 * @author 马建华
 *
 */
public class SelectByPrimaryKeyWithoutDeletedMethodPlugin extends PluginAdapter{
	
	private String deleteFlagColumnName;
	
	private String offValue;

	@Override
	public boolean validate(List<String> warnings) {
		deleteFlagColumnName = properties.getProperty("deleteFlagColumnName");
		offValue = properties.getProperty("offValue");
		return true;
	}

	@Override
	public boolean clientGenerated(Interface interfaze,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		
		IntrospectedColumn column = introspectedTable.getColumn(deleteFlagColumnName);
		if(column == null){
			return true;
		}
		
		Method m = getMethodShell(topLevelClass, introspectedTable);
				
		FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(
				introspectedTable.getExampleType());
		
		
		m.addBodyLine(exampleType.getShortName() + " criteria = new " + exampleType.getShortName() + "();");
		m.addBodyLine("criteria.createCriteria()");

		if (introspectedTable.getRules().generatePrimaryKeyClass()) {
			// no primary key class, but primary key is enabled. Primary
			// key columns must be in the base class.
			FullyQualifiedJavaType keyType = new FullyQualifiedJavaType(
					introspectedTable.getBaseRecordType());
			topLevelClass.addImportedType(keyType);
			
			for (IntrospectedColumn introspectedColumn : introspectedTable
					.getPrimaryKeyColumns()) {
				
				m.addBodyLine(".and" + toUpperFirst(introspectedColumn.getJavaProperty()) + "EqualTo(_key." + JavaBeansUtil.getGetterMethodName(introspectedColumn
						.getJavaProperty(),introspectedColumn.getFullyQualifiedJavaType()) + "())");
				
			}
		}else{
			
			for (IntrospectedColumn introspectedColumn : introspectedTable
					.getPrimaryKeyColumns()) {
				
				m.addBodyLine(".and" + toUpperFirst(introspectedColumn.getJavaProperty()) + "EqualTo(" + introspectedColumn.getJavaProperty() + ")");
				
			}
		}
		m.addBodyLine(".and" + toUpperFirst(column.getJavaProperty()) + "EqualTo(\"" + offValue + "\");");

		addQueryLine(m, introspectedTable);
		
		
		
		
		topLevelClass.addMethod(m);
		interfaze.addMethod(getMethodShell(topLevelClass, introspectedTable));
		
		return true;
	}
	
	protected void addQueryLine(Method m, IntrospectedTable introspectedTable){
		FullyQualifiedJavaType returnType = m.getReturnType();

		StringBuilder sb = new StringBuilder();
		sb.setLength(0);
		sb.append(returnType.getShortName());
		sb.append(" record = ("); //$NON-NLS-1$
		sb.append(returnType.getShortName());
		sb.append(") "); //$NON-NLS-1$
		sb.append(new SpringAnnotationalDAOTemplate().getQueryForObjectMethod(introspectedTable
				.getIbatis2SqlMapNamespace(), introspectedTable
				.getSelectByExampleStatementId(), "criteria")); //$NON-NLS-1$
		m.addBodyLine(sb.toString());
		m.addBodyLine("return record;"); //$NON-NLS-1$
	}
	
	protected String toUpperFirst(String property){
		String first = String.valueOf(property.charAt(0)).toUpperCase();
		return first + property.substring(1);
	}
	
	protected String getMethodName(){
		return "selectByPrimaryKeyWithoutDeleted";
	}
	
	protected FullyQualifiedJavaType getReturnType(IntrospectedTable introspectedTable){
		return introspectedTable.getRules().calculateAllFieldsClass();
	}
	
	private Method getMethodShell(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		Method m = new Method();
		m.setVisibility(JavaVisibility.PUBLIC);
		m.setName(getMethodName());
		m.setReturnType(getReturnType(introspectedTable));
		
		topLevelClass.addImportedType(introspectedTable.getRules()
				.calculateAllFieldsClass());
		
		if (introspectedTable.getRules().generatePrimaryKeyClass()) {
			FullyQualifiedJavaType type = new FullyQualifiedJavaType(
					introspectedTable.getPrimaryKeyType());
			topLevelClass.addImportedType(type);
			m.addParameter(new Parameter(type, "_key")); //$NON-NLS-1$
		} else {
			for (IntrospectedColumn introspectedColumn : introspectedTable
					.getPrimaryKeyColumns()) {
				FullyQualifiedJavaType type = introspectedColumn
						.getFullyQualifiedJavaType();
				topLevelClass.addImportedType(type);
				m.addParameter(new Parameter(type, introspectedColumn
						.getJavaProperty()));
			}
		}
		
		return m;
	}
	
}
