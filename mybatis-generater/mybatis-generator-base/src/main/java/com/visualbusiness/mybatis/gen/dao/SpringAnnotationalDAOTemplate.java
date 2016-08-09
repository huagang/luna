package com.visualbusiness.mybatis.gen.dao;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.codegen.ibatis2.dao.templates.SpringDAOTemplate;

/**
 * 
 * @author Mark
 *
 */
public class SpringAnnotationalDAOTemplate extends SpringDAOTemplate {

	private String superClassName;
	
	public SpringAnnotationalDAOTemplate(){
	}
	public SpringAnnotationalDAOTemplate(String superClassName) {
		this.superClassName = superClassName;
	}

//	@Override
//	protected void configureConstructorTemplate() {
//		
//		Method m = new Method();
//		m.setConstructor(true);
//		m.setVisibility(JavaVisibility.PUBLIC);
//		
//		FullyQualifiedJavaType sqlmapClient = new FullyQualifiedJavaType("com.ibatis.sqlmap.client.SqlMapClient");
//		this.addImplementationImport(sqlmapClient);
//		
//		Parameter p = new Parameter(sqlmapClient,"sqlmapClient");
//		m.addParameter(p);
//		m.addBodyLine("super();");
//		m.addBodyLine("this.setSqlMapClient(sqlmapClient);");
//		
//		this.setConstructorTemplate(m);
//	}

	public String getSuperClassName(){
		return superClassName;
	}

	@Override
	protected void configureSuperClass() {
		String superClassName = getSuperClassName();
		if(superClassName != null){
			this.setSuperClass(new FullyQualifiedJavaType(superClassName));
		}
	}
}
