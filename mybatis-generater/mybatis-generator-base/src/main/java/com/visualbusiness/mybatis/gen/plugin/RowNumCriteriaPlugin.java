package com.visualbusiness.mybatis.gen.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 追加行编号条件
 * 
 * @author Ma JianHua
 *
 */
public class RowNumCriteriaPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> arg0) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.mybatis.generator.api.PluginAdapter#modelFieldGenerated(org.mybatis.generator.api.dom.java.Field, org.mybatis.generator.api.dom.java.TopLevelClass, org.mybatis.generator.api.IntrospectedColumn, org.mybatis.generator.api.IntrospectedTable, org.mybatis.generator.api.Plugin.ModelClassType)
	 */
	@Override
	public boolean modelFieldGenerated(Field field,
			TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
			IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		
		return super.modelFieldGenerated(field, topLevelClass, introspectedColumn,
				introspectedTable, modelClassType);
	}


}
