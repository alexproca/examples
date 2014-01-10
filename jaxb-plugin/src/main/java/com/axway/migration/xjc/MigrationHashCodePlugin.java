package com.axway.migration.xjc;

import java.util.Collection;
import java.util.Map;

import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.plugin.hashcode.HashCodePlugin;
import org.jvnet.jaxb2_commons.util.ClassUtils;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.tools.xjc.outline.ClassOutline;

public class MigrationHashCodePlugin extends HashCodePlugin
{

	@Override
	public String getOptionName()
	{
		return "XhashCode-hjid";
	}
	
	@Override
	public String getUsage()
	{
		return "-XhashCode-hjid: Generates special hashCode for JPA";
	}

	protected void processClassOutline(ClassOutline classOutline)
	{
		final JDefinedClass theClass = classOutline.implClass;

		@SuppressWarnings("unused")
		final JMethod object$hashCode = generateObject$hashCode(classOutline, theClass);
	}

	protected JMethod generateObject$hashCode(final ClassOutline classOutline, final JDefinedClass theClass)
	{
		String primaryKey = getPrimaryKeyField(theClass);
		final JMethod object$hashCode = theClass.method(JMod.PUBLIC, theClass.owner().INT, "hashCode");
		{
			final JBlock body = object$hashCode.body();
			JConditional ifElse = body._if(JExpr.ref(primaryKey)
												.ne(JExpr._null()));
			ifElse._then()
					._return(JExpr.ref(primaryKey)
									.invoke("hashCode"));
			ifElse._else()
					._return(JExpr._super()
									.invoke("hashCode"));
		}
		return object$hashCode;
	}
	
	private String getPrimaryKeyField(JDefinedClass theClass)
	{
		Collection<JAnnotationUse> annotations;
		for(JMethod method : theClass.methods())
		{
			annotations = method.annotations();
			for(JAnnotationUse annotation : annotations)
			{
				if("Id".equals(annotation.getAnnotationClass().name()))
					return method.name().substring(3).toLowerCase();
			}
		}
		return "hjid";
	}

}
