package com.axway.migration.xjc;

import java.util.Collection;

import org.jvnet.jaxb2_commons.plugin.equals.EqualsPlugin;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.outline.ClassOutline;

public class MigrationEqualsPlugin extends EqualsPlugin
{

	@Override
	public String getOptionName()
	{
		return "Xequals-hjid";
	}

	@Override
	public String getUsage()
	{
		return "-Xequals-hjid: Generates special equals for JPA";
	}

	protected void processClassOutline(ClassOutline classOutline)
	{
		final JDefinedClass theClass = classOutline.implClass;

		@SuppressWarnings("unused")
		final JMethod objectEquals = generateObject$equals(classOutline, theClass);
	}

	protected JMethod generateObject$equals(ClassOutline classOutline, final JDefinedClass theClass)
	{
		String primaryKey = getPrimaryKeyField(theClass);
		final JCodeModel codeModel = theClass.owner();
		final JMethod objectEquals = theClass.method(JMod.PUBLIC, codeModel.BOOLEAN, "equals");
		{
			final JVar object = objectEquals.param(Object.class, "object");
			final JBlock body = objectEquals.body();

			final JConditional ifNotInstanceof = body._if(JOp.not(object._instanceof(theClass)));
			ifNotInstanceof._then()
							._return(JExpr.FALSE);

			//
			body._if(JExpr._this()
							.eq(object))
				._then()
				._return(JExpr.TRUE);

			body._if(JExpr.ref(primaryKey)
							.eq(JExpr._null()))
				._then()
				._return(JExpr.FALSE);

			JExpression castObject = JExpr.cast(theClass, JExpr.ref("object"));
			body._return(JExpr.ref(primaryKey)
								.invoke("equals")
								.arg(castObject.invoke("getHjid")));
		}
		return objectEquals;
	}

	private String getPrimaryKeyField(JDefinedClass theClass)
	{
		Collection<JAnnotationUse> annotations;
		for (JMethod method : theClass.methods())
		{
			annotations = method.annotations();
			for (JAnnotationUse annotation : annotations)
			{
				if ("Id".equals(annotation.getAnnotationClass()
											.name()))
					return method.name()
									.substring(3)
									.toLowerCase();
			}
		}
		return "hjid";
	}
}
