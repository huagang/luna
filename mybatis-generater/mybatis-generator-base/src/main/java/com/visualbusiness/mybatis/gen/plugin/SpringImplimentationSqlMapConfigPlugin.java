package com.visualbusiness.mybatis.gen.plugin;

import java.util.List;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.DefaultXmlFormatter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.plugins.SqlMapConfigPlugin;

/**
 * 生成SqlMapConfigp(SpringDAO用的实装类的SQLMAP)
 * 
 * @author 马建华
 *
 */
public class SpringImplimentationSqlMapConfigPlugin extends SqlMapConfigPlugin{
	
	private String extraPackage;
	
	private String targetProject;
	
	@Override
	public boolean validate(List<String> warnings) {
		extraPackage = properties.getProperty("extraPackage");
		targetProject = properties.getProperty("implProject");
		return true;
	}

	@Override
	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap,
			IntrospectedTable introspectedTable) {
		
		Document doc = new Document(XmlConstants.IBATIS2_SQL_MAP_PUBLIC_ID, XmlConstants.IBATIS2_SQL_MAP_SYSTEM_ID);
		XmlElement el = new XmlElement("sqlMap");
		el.addAttribute(new Attribute("namespace", introspectedTable.getIbatis2SqlMapNamespace()));
		
		doc.setRootElement(el);
		
		GeneratedXmlFile impl = new GeneratedXmlFile(doc,
				sqlMap.getFileName().replace("/dao/","/dao/custom/").replace("_SqlMap", "_ex_SqlMap"),
				extraPackage + ".sqlmap", targetProject, false, new DefaultXmlFormatter());
		
		super.sqlMapGenerated(sqlMap, introspectedTable);
		return super.sqlMapGenerated(impl, introspectedTable);
	}

//	@Override
//	public boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement element,
//			IntrospectedTable introspectedTable) {
//		element.getElements().remove(1);
//
//		XmlElement dynamicElement = new XmlElement("dynamic"); //$NON-NLS-1$
//		dynamicElement.addAttribute(new Attribute("prepend", "set")); //$NON-NLS-1$ //$NON-NLS-2$
//		String EIG_KEIH_REVISION = ",EIG_KEIH_REVISION = EIG_KEIH_REVISION + 1";
//		String REVISION = ",REVISION = REVISION + 1";
//		StringBuilder sb = new StringBuilder();
//		for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
//
//			if ("eigKeihRevision".equals(introspectedColumn.getJavaProperty())) {
//				dynamicElement.addElement(new TextElement(EIG_KEIH_REVISION));
//			} else if ("revision".equals(introspectedColumn.getJavaProperty())) {
//				dynamicElement.addElement(new TextElement(REVISION));
//			} else {
//				XmlElement isNotNullElement = new XmlElement("isNotNull"); //$NON-NLS-1$
//				isNotNullElement.addAttribute(new Attribute("prepend", ",")); //$NON-NLS-1$ //$NON-NLS-2$
//				isNotNullElement.addAttribute(new Attribute("property",
//						"record." +introspectedColumn.getJavaProperty())); //$NON-NLS-1$ //$NON-NLS-2$
//
//				sb.setLength(0);
//				sb.append(introspectedColumn.getActualColumnName());
//				sb.append(" = #record."); //$NON-NLS-1$
//				sb.append(introspectedColumn.getJavaProperty()); //$NON-NLS-1$
//				sb.append(":" + introspectedColumn.getJdbcTypeName()); //$NON-NLS-1$
//				sb.append("#"); //$NON-NLS-1$
//
//				isNotNullElement.addElement(new TextElement(sb.toString()));
//				dynamicElement.addElement(isNotNullElement);
//			}
//        }
//        element.addElement(1, dynamicElement);  
//		return super.sqlMapUpdateByExampleSelectiveElementGenerated(element,
//				introspectedTable);
//	}
//	
//	/* (非 Javadoc)
//	 * @see org.mybatis.generator.api.PluginAdapter#sqlMapUpdateByPrimaryKeySelectiveElementGenerated(org.mybatis.generator.api.dom.xml.XmlElement, org.mybatis.generator.api.IntrospectedTable)
//	 */
//	@Override
//	public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(
//			XmlElement element, IntrospectedTable introspectedTable) {
//
//		element.getElements().remove(1);
//		XmlElement dynamicElement = new XmlElement("dynamic"); //$NON-NLS-1$
//		dynamicElement.addAttribute(new Attribute("prepend", "set")); //$NON-NLS-1$ //$NON-NLS-2$
//		String EIG_KEIH_REVISION = ",EIG_KEIH_REVISION = EIG_KEIH_REVISION + 1";
//		String REVISION = ",REVISION = REVISION + 1";
//		StringBuilder sb = new StringBuilder();
//		int index = 0;
//		Set<String> keys = new HashSet<String>();
//		for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
//			keys.add(introspectedColumn.getActualColumnName());
//		}
//		
//		for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
//
//			if (keys.contains(introspectedColumn.getActualColumnName())) {
//				continue;
//			}
//			if ("eigKeihRevision".equals(introspectedColumn.getJavaProperty())) {
//				dynamicElement.addElement(index++, new TextElement(EIG_KEIH_REVISION));
//			} else if ("revision".equals(introspectedColumn.getJavaProperty())) {
//				dynamicElement.addElement(index++, new TextElement(REVISION));
//			} else {
//				XmlElement isNotNullElement = new XmlElement("isNotNull"); //$NON-NLS-1$
//				isNotNullElement.addAttribute(new Attribute("prepend", ",")); //$NON-NLS-1$ //$NON-NLS-2$
//				isNotNullElement.addAttribute(new Attribute("property",
//						introspectedColumn.getJavaProperty())); //$NON-NLS-1$ //$NON-NLS-2$
//
//				sb.setLength(0);
//				sb.append(introspectedColumn.getActualColumnName());
//				sb.append(" = #"); //$NON-NLS-1$
//				sb.append(introspectedColumn.getJavaProperty()); //$NON-NLS-1$
//				sb.append(":" + introspectedColumn.getJdbcTypeName()); //$NON-NLS-1$
//				sb.append("#"); //$NON-NLS-1$
//
//				isNotNullElement.addElement(new TextElement(sb.toString()));
//				dynamicElement.addElement(index++, isNotNullElement);
//			}
//        }
//        element.addElement(1, dynamicElement);  
//		return super.sqlMapUpdateByExampleSelectiveElementGenerated(element,
//				introspectedTable);
//	}
//	
//	public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(
//			XmlElement element, IntrospectedTable introspectedTable) {
//
//		Iterator<Element> itr = element.getElements().iterator();
//		while (itr.hasNext()) {
//			Element el = itr.next();
//			if (el instanceof TextElement) {
//				itr.remove();
//			}
//		}
//        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
//        StringBuilder sb = new StringBuilder();
//        sb.append("update ");
//        sb.append(table.getIntrospectedTableName());
//        sb.append("\r\n    set ");
//
//        int index = 0;
//		for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
//			if (index++ != 0) {
//				sb.append("\r\n      ");
//			}
//			if ("eigKeihRevision".equals(introspectedColumn.getJavaProperty())) {
//				sb.append("EIG_KEIH_REVISION = EIG_KEIH_REVISION + 1,");
//			} else if ("revision".equals(introspectedColumn.getJavaProperty())) {
//				sb.append("REVISION = REVISION + 1,");
//			} else {
//				sb.append(introspectedColumn.getActualColumnName());
//				sb.append(" = #record."); //$NON-NLS-1$
//				sb.append(introspectedColumn.getJavaProperty()); //$NON-NLS-1$
//				sb.append(":" + introspectedColumn.getJdbcTypeName()); //$NON-NLS-1$
//				sb.append("#,"); //$NON-NLS-1$
//			}
//        }
//		sb.delete(sb.length()-1, sb.length());
//        element.addElement(0, new TextElement(sb.toString()));  
//		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
//	}
//
//
//
//	/* (非 Javadoc)
//	 * @see org.mybatis.generator.api.PluginAdapter#sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(org.mybatis.generator.api.dom.xml.XmlElement, org.mybatis.generator.api.IntrospectedTable)
//	 */
//	@Override
//	public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(
//			XmlElement element, IntrospectedTable introspectedTable) {
//
//		Iterator<Element> itr = element.getElements().iterator();
//		while (itr.hasNext()) {
//			Element el = itr.next();
//			if (el instanceof TextElement) {
//				itr.remove();
//			}
//		}
//		
//        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
//        StringBuilder keys = new StringBuilder();
//		keys.append("\r\n    where ");
//		int keyIndex = 0;
//		Set<String> keysSet = new HashSet<String>();
//		for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
//			keysSet.add(introspectedColumn.getActualColumnName());
//			if (keyIndex++ != 0) {
//				keys.append("\r\n      and ");
//			}
//			keys.append(introspectedColumn.getActualColumnName());
//			keys.append(" = #");
//			keys.append(introspectedColumn.getJavaProperty()); //$NON-NLS-1$
//			keys.append(":" + introspectedColumn.getJdbcTypeName()); //$NON-NLS-1$
//			keys.append("#"); //$NON-NLS-1$
//		}
//        
//        
//        StringBuilder sb = new StringBuilder();
//        sb.append("update ");
//        sb.append(table.getIntrospectedTableName());
//        sb.append("\r\n    set ");
//
//       
//        int index = 0;
//		for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
//			if (keysSet.contains(introspectedColumn.getActualColumnName())) {
//				continue;
//			}
//			if (index++ != 0) {
//				sb.append("\r\n      ");
//			}
//			if ("eigKeihRevision".equals(introspectedColumn.getJavaProperty())) {
//				sb.append("EIG_KEIH_REVISION = EIG_KEIH_REVISION + 1,");
//			} else if ("revision".equals(introspectedColumn.getJavaProperty())) {
//				sb.append("REVISION = REVISION + 1,");
//			} else {
//				sb.append(introspectedColumn.getActualColumnName());
//				sb.append(" = #"); //$NON-NLS-1$
//				sb.append(introspectedColumn.getJavaProperty()); //$NON-NLS-1$
//				sb.append(":" + introspectedColumn.getJdbcTypeName()); //$NON-NLS-1$
//				sb.append("#,"); //$NON-NLS-1$
//			}
//        }
//
//		
//		sb.delete(sb.length()-1, sb.length());
//		sb.append(keys);
//        element.addElement(0, new TextElement(sb.toString()));  
//		return super.sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(element,
//				introspectedTable);
//	}

	
}
