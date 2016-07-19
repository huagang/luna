package com.visualbusiness.mybatis.gen.dao;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.ibatis2.dao.DAOGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 生成DAO
 * @author Mark
 */

public class SpringAnnotationalDAOGenerator extends DAOGenerator{
	
	private SpringAnnotationalDAOTemplate daoTemplate; 
	
	public SpringAnnotationalDAOGenerator(){
		this(new SpringAnnotationalDAOTemplate());
	}
	
	public SpringAnnotationalDAOGenerator(SpringAnnotationalDAOTemplate daoTemplate) {
		super(daoTemplate, true);
		this.daoTemplate = daoTemplate;
	}

	@Override
	protected TopLevelClass getTopLevelClassShell() {
		
		
		FullyQualifiedJavaType interfaceType = new FullyQualifiedJavaType(
                introspectedTable.getDAOInterfaceType().replace("DAO", "DAOBase"));
        FullyQualifiedJavaType implementationType = new FullyQualifiedJavaType(
        		introspectedTable.getDAOImplementationType().replace("DAOImpl", "DAOBaseImpl"));
        
        CommentGenerator commentGenerator = context.getCommentGenerator();

        TopLevelClass answer = new TopLevelClass(implementationType);
        answer.setVisibility(JavaVisibility.PUBLIC);
        answer.setAbstract(true);
        answer.setSuperClass(daoTemplate.getSuperClass());
        answer.addImportedType(daoTemplate.getSuperClass());
        answer.addSuperInterface(interfaceType);
        answer.addImportedType(interfaceType);

        for (FullyQualifiedJavaType fqjt : daoTemplate
                .getImplementationImports()) {
            answer.addImportedType(fqjt);
        }

        commentGenerator.addJavaFileComment(answer);

        // add constructor from the template
        answer.addMethod(daoTemplate.getConstructorClone(commentGenerator,
                implementationType, introspectedTable));

        // add any fields from the template
        for (Field field : daoTemplate.getFieldClones(commentGenerator,
                introspectedTable)) {
            answer.addField(field);
        }

        // add any methods from the template
        for (Method method : daoTemplate.getMethodClones(commentGenerator,
                introspectedTable)) {
            answer.addMethod(method);
        }

        return answer;
	}

	@Override
	protected Interface getInterfaceShell() {
		Interface answer = new Interface(new FullyQualifiedJavaType(
				introspectedTable.getDAOInterfaceType().replace("DAO", "DAOBase")));
        answer.setVisibility(JavaVisibility.PUBLIC);

        String rootInterface = introspectedTable
                .getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (rootInterface == null) {
            rootInterface = context.getJavaClientGeneratorConfiguration()
                    .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (StringUtility.stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(
                    rootInterface);
            answer.addSuperInterface(fqjt);
            answer.addImportedType(fqjt);
        }

        for (FullyQualifiedJavaType fqjt : daoTemplate.getInterfaceImports()) {
            answer.addImportedType(fqjt);
        }

        context.getCommentGenerator().addJavaFileComment(answer);

        return answer;
	}
}
