package com.visualbusiness.mybatis.gen.plugin;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.DefaultXmlFormatter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;

import com.visualbusiness.mybatis.gen.util.JavaUtils;

/**
 * 追加实装用的SpringDAO的空类
 * 
 * @author 马建华
 *
 */
public class SpringImplimentationClassGeneratePlugin extends PluginAdapter{
	
	private String extraPackage;
	
	private String implProject;
	
	private String otherProject;
	private String testSuperClass;
	
	private boolean createMock;
	private boolean createTest;
	
	@Override
	public boolean validate(List<String> warnings) {
		extraPackage = properties.getProperty("extraPackage");
		implProject = properties.getProperty("implProject");
		
		otherProject = properties.getProperty("otherProject");
		testSuperClass = properties.getProperty("testSuperClass");
		
		String cm = properties.getProperty("createMock");
		createMock = cm != null && cm.equals("true")?true:false;
		String ct = properties.getProperty("createTest");
		createTest = ct != null && ct.equals("true")?true:false;
		return true;
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
			IntrospectedTable introspectedTable) {
		
		List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
		
		//CLASS
		FullyQualifiedJavaType impl = new FullyQualifiedJavaType(extraPackage + introspectedTable.getDAOImplementationType().substring(introspectedTable.getDAOImplementationType().lastIndexOf(".")));
		
		TopLevelClass answer = new TopLevelClass(impl);
		answer.setVisibility(JavaVisibility.PUBLIC);
		answer.setSuperClass(introspectedTable.getDAOImplementationType().replace("DAOImpl", "DAOBaseImpl"));
		answer.addImportedType(new FullyQualifiedJavaType(introspectedTable.getDAOImplementationType().replace("DAOImpl", "DAOBaseImpl")));
		answer.addSuperInterface(new FullyQualifiedJavaType(extraPackage + introspectedTable.getDAOInterfaceType().substring(introspectedTable.getDAOInterfaceType().lastIndexOf("."))));
		
		answer.addAnnotation(String.format("@Repository(\"%s\")",JavaUtils.toVariableName(introspectedTable.getDAOInterfaceType().substring(introspectedTable.getDAOInterfaceType().lastIndexOf(".") + 1))));
		answer.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));

		GeneratedJavaFile gjf = new GeneratedJavaFile(answer, implProject, "UTF-8", new DefaultJavaFormatter());
		list.add(gjf);
		
		//IF
		FullyQualifiedJavaType iface = new FullyQualifiedJavaType(extraPackage + introspectedTable.getDAOInterfaceType().substring(introspectedTable.getDAOInterfaceType().lastIndexOf("."))); 
		
		Interface iii = new Interface(iface);
		iii.setVisibility(JavaVisibility.PUBLIC);
		iii.addSuperInterface(new FullyQualifiedJavaType(introspectedTable.getDAOInterfaceType().replace("DAO", "DAOBase")));
		iii.addImportedType(new FullyQualifiedJavaType(introspectedTable.getDAOInterfaceType().replace("DAO", "DAOBase")));
		gjf = new GeneratedJavaFile(iii, implProject, "UTF-8", new DefaultJavaFormatter());
		list.add(gjf);
		
		if(createMock){
			FullyQualifiedJavaType mock = new FullyQualifiedJavaType(extraPackage + introspectedTable.getDAOInterfaceType().substring(introspectedTable.getDAOInterfaceType().lastIndexOf(".")) + "Mock");

			TopLevelClass mockClass = new TopLevelClass(mock);
			mockClass.setVisibility(JavaVisibility.PUBLIC);
			mockClass.setSuperClass(answer.getType());
			
			gjf = new GeneratedJavaFile(mockClass, otherProject, "UTF-8", new DefaultJavaFormatter());
			list.add(gjf);
		}
		
		if(createTest){
			FullyQualifiedJavaType test = new FullyQualifiedJavaType(extraPackage + introspectedTable.getDAOInterfaceType().substring(introspectedTable.getDAOInterfaceType().lastIndexOf(".")) + "Test");

			TopLevelClass testClass = new TopLevelClass(test);
			testClass.setVisibility(JavaVisibility.PUBLIC);
			testClass.setSuperClass(testSuperClass);
			testClass.addImportedType(new FullyQualifiedJavaType(testSuperClass));
			
			gjf = new GeneratedJavaFile(testClass, otherProject, "UTF-8", new DefaultJavaFormatter());
			list.add(gjf);
		}
		
		return list;
	}

	@Override
	public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(
			IntrospectedTable introspectedTable) {
		Document doc = new Document(XmlConstants.IBATIS2_SQL_MAP_PUBLIC_ID, XmlConstants.IBATIS2_SQL_MAP_SYSTEM_ID);
		XmlElement el = new XmlElement("sqlMap");
		el.addAttribute(new Attribute("namespace", introspectedTable.getIbatis2SqlMapNamespace()));

		doc.setRootElement(el);

		GeneratedXmlFile impl = new GeneratedXmlFile(doc,
				introspectedTable.getIbatis2SqlMapFileName().replace("/dao/","/dao/custom/").replace("_SqlMap", "_ex_SqlMap"),
				extraPackage + ".sqlmap", implProject, false, new DefaultXmlFormatter());
		
		List<GeneratedXmlFile> list = new ArrayList<GeneratedXmlFile>();
		list.add(impl);
		
		return list;
	}
}
