package com.visualbusiness.mybatis.gen.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 检查乐观锁的方法的插件
 * @author 马建华
 *
 */
public class CustomInsertSelectiveMethodPlugin extends PluginAdapter{

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}
	
	@Override
	public boolean clientInsertSelectiveMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		Method cm = new Method();
		cm.setName("getSqlMapClientTemplate");
		cm.setReturnType(new FullyQualifiedJavaType("org.springframework.orm.ibatis.SqlMapClientTemplate"));
		interfaze.addMethod(cm);
		interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.orm.ibatis.SqlMapClientTemplate"));
		
		Method m = new Method();
		m.setName("insertSelective");
		copyMethod(m, method);
		m.addParameter(new Parameter(new FullyQualifiedJavaType("com.ibatis.sqlmap.client.SqlMapExecutor"), "executor"));

		interfaze.addMethod(m);
		interfaze.addImportedType(new FullyQualifiedJavaType("com.ibatis.sqlmap.client.SqlMapExecutor"));
		return true;
	}

	@Override
	public boolean clientInsertSelectiveMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		Method m = new Method();
		m.setName("insertSelective");
		copyMethod(m, method);
		m.addParameter(new Parameter(new FullyQualifiedJavaType("com.ibatis.sqlmap.client.SqlMapExecutor"), "executor"));
		
		m.addBodyLine("if(executor == null){");
		m.addBodyLine("insertSelective(record);");
		m.addBodyLine("}else{");
		m.addBodyLine("try {");
		m.addBodyLine(String.format("executor.insert(\"%s.%s\",record);",introspectedTable.getIbatis2SqlMapNamespace(),introspectedTable.getInsertSelectiveStatementId()));
		m.addBodyLine("}catch (SQLException e) {");
		m.addBodyLine("throw new RuntimeException(e);");
		m.addBodyLine("}");
		m.addBodyLine("}");

		topLevelClass.addMethod(m);
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.ibatis.sqlmap.client.SqlMapExecutor"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.sql.SQLException"));
		return true;
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
