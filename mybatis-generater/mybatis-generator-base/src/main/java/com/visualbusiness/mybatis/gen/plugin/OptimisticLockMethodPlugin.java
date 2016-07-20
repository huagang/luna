package com.visualbusiness.mybatis.gen.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.JavaBeansUtil;

/**
 * 追加乐观锁检查方法的插件。
 * @author 马建华
 *
 */
public class OptimisticLockMethodPlugin extends PluginAdapter{
	
	private String updateDatetimeColumnName;
	
	private String deleteFlagColumnName;
	
	private String offValue;

	@Override
	public boolean validate(List<String> warnings) {
		updateDatetimeColumnName = properties.getProperty("updateDatetimeColumnName");
		deleteFlagColumnName = properties.getProperty("deleteFlagColumnName");
		offValue = properties.getProperty("offValue");
		return true;
	}
	
	@Override
	public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(
			Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		if(ignore(introspectedTable)){
			return true;
		}
		
		Method m = new Method();
		m.setName("updateByPrimaryKeySelectiveWithUDate");
		copyMethod(m, method);
		m.addParameter(new Parameter(new FullyQualifiedJavaType("java.sql.Timestamp"), "updateDate"));
		m.addException(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));
		
		interfaze.addMethod(m);
		interfaze.addImportedType(new FullyQualifiedJavaType("java.sql.Timestamp"));
		interfaze.addImportedType(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));
		return true;
	}

	@Override
	public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(
			Method method, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		if(ignore(introspectedTable)){
			return true;
		}
		Method m = new Method();
		m.setName("updateByPrimaryKeySelectiveWithUDate");
		copyMethod(m, method);
		m.addParameter(new Parameter(new FullyQualifiedJavaType("java.sql.Timestamp"), "updateDate"));
		m.addException(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));
		addBodyLines(m, introspectedTable, "int rows = updateByCriteriaSelective(record,criteria);","record");
		
		topLevelClass.addMethod(m);
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.sql.Timestamp"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));

		return true;
	}

	@Override
	public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(
			Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		if(ignore(introspectedTable)){
			return true;
		}
		Method m = new Method();
		m.setName("updateByPrimaryKeyWithBWithUDate");
		copyMethod(m, method);
		m.addParameter(new Parameter(new FullyQualifiedJavaType("java.sql.Timestamp"), "updateDate"));
		m.addException(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));

		interfaze.addMethod(m);
		interfaze.addImportedType(new FullyQualifiedJavaType("java.sql.Timestamp"));
		interfaze.addImportedType(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));
		return true;
	}

	@Override
	public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(
			Method method, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		if(ignore(introspectedTable)){
			return true;
		}
		Method m = new Method();
		m.setName("updateByPrimaryKeyWithBWithUDate");
		copyMethod(m, method);
		m.addParameter(new Parameter(new FullyQualifiedJavaType("java.sql.Timestamp"), "updateDate"));
		m.addException(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));
		addBodyLines(m, introspectedTable, "int rows = updateByCriteria(record,criteria);","record");

		topLevelClass.addMethod(m);
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.sql.Timestamp"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));
		return true;
	}

	@Override
	public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(
			Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		if(ignore(introspectedTable)){
			return true;
		}
		Method m = new Method();
		m.setName("updateByPrimaryKeyWithUDate");
		copyMethod(m, method);
		m.addParameter(new Parameter(new FullyQualifiedJavaType("java.sql.Timestamp"), "updateDate"));
		m.addException(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));

		interfaze.addMethod(m);
		interfaze.addImportedType(new FullyQualifiedJavaType("java.sql.Timestamp"));
		interfaze.addImportedType(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));
		return true;
	}

	@Override
	public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(
			Method method, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		if(ignore(introspectedTable)){
			return true;
		}
		Method m = new Method();
		m.setName("updateByPrimaryKeyWithUDate");
		copyMethod(m, method);
		m.addParameter(new Parameter(new FullyQualifiedJavaType("java.sql.Timestamp"), "updateDate"));
		m.addException(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));
		addBodyLines(m, introspectedTable, "int rows = updateByCriteria(record,criteria);","record");

		topLevelClass.addMethod(m);
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.sql.Timestamp"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));
		return true;
	}

	@Override
	public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		if(ignore(introspectedTable)){
			return true;
		}
		Method m = new Method();
		m.setName("deleteByPrimaryKeyWithUDate");
		copyMethod(m, method);
		m.addParameter(new Parameter(new FullyQualifiedJavaType("java.sql.Timestamp"), "updateDate"));
		m.addException(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));

		interfaze.addMethod(m);
		interfaze.addImportedType(new FullyQualifiedJavaType("java.sql.Timestamp"));
		interfaze.addImportedType(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));
		return true;
	}

	@Override
	public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		if(ignore(introspectedTable)){
			return true;
		}
		Method m = new Method();
		m.setName("deleteByPrimaryKeyWithUDate");
		copyMethod(m, method);
		m.addParameter(new Parameter(new FullyQualifiedJavaType("java.sql.Timestamp"), "updateDate"));
		m.addException(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));
		addBodyLines(m, introspectedTable, "int rows = deleteByCriteria(criteria);",introspectedTable.getRules().generatePrimaryKeyClass()?"_key":null);

		topLevelClass.addMethod(m);
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.sql.Timestamp"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.visualbusiness.vr3d.common.VbOptimisticLockException"));
		return true;
	}
	
	private boolean ignore(IntrospectedTable introspectedTable){
		IntrospectedColumn flg = introspectedTable.getColumn(deleteFlagColumnName);
		IntrospectedColumn datetime = introspectedTable.getColumn(updateDatetimeColumnName);
		if(flg == null || datetime == null){
			return true;
		}
		return false;
	}

	private void addBodyLines(Method m,IntrospectedTable introspectedTable,String callLine,String variableName){
		
		FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(introspectedTable.getExampleType());
		
		
		m.addBodyLine(exampleType.getShortName() + " criteria = new " + exampleType.getShortName() + "();");
		m.addBodyLine("criteria.createCriteria()");

		for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
			if(variableName != null){
				m.addBodyLine(".and" + toUpperFirst(introspectedColumn.getJavaProperty()) + "EqualTo(" + variableName + "." + JavaBeansUtil.getGetterMethodName(introspectedColumn
						.getJavaProperty(),introspectedColumn.getFullyQualifiedJavaType()) + "())");
			}else{
				m.addBodyLine(".and" + toUpperFirst(introspectedColumn.getJavaProperty()) + "EqualTo(" + introspectedColumn.getJavaProperty() + ")");
			}
		}

		
		IntrospectedColumn column = introspectedTable.getColumn(deleteFlagColumnName);
		IntrospectedColumn datetime = introspectedTable.getColumn(updateDatetimeColumnName);
		if(column != null){
			//updateDate
			m.addBodyLine(".and" + toUpperFirst(datetime.getJavaProperty()) + "EqualTo(updateDate)");
			m.addBodyLine(".and" + toUpperFirst(column.getJavaProperty()) + "EqualTo(\"" + offValue + "\");");
		}else{
			//updateDate
			m.addBodyLine(".and" + toUpperFirst(datetime.getJavaProperty()) + "EqualTo(updateDate);");
		}

		m.addBodyLine(callLine);
		
		m.addBodyLine("if(rows == 0)throw new VbOptimisticLockException();");
		m.addBodyLine("return rows;");
	}
	
	protected String toUpperFirst(String property){
		String first = String.valueOf(property.charAt(0)).toUpperCase();
		return first + property.substring(1);
	}
	
	private void copyMethod(Method m,Method method){
		//m.setName(method.getName());
		m.setReturnType(method.getReturnType());
		m.setVisibility(method.getVisibility());
		m.setFinal(method.isFinal());
		m.setStatic(method.isStatic());
		m.setConstructor(method.isConstructor());
		
		for(Parameter p : method.getParameters()){
			m.addParameter(p);
		}
		for(FullyQualifiedJavaType jt : method.getExceptions()){
			m.addException(jt);
		}
		for(String s : method.getAnnotations()){
			m.addAnnotation(s);
		}
		for(String s : method.getJavaDocLines()){
			m.addJavaDocLine(s);
		}
	}
}
