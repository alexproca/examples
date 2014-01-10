package com.axway.migration.xjc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassContainer;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.addon.code_injector.Const;
import com.sun.tools.xjc.model.CClassInfoParent;
import com.sun.tools.xjc.outline.Aspect;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

public class CloneablePlugin extends Plugin
{

	public String getOptionName()
	{
		return "Xclone";
	}

	public List<String> getCustomizationURIs()
	{
		return Collections.singletonList(Const.NS);
	}

	public boolean isCustomizationTagName(String nsUri, String localName)
	{
		return false; // nsUri.equals(Const.NS) && localName.equals("code");
	}

	public String getUsage()
	{
		return "  -Xclone      :  inherits from cloneable each class";
	}
	Map<JType, JType> imap = new HashMap<JType, JType>();
	Map<JClass, JClass> cmap = new HashMap<JClass, JClass>();

	// List<String> outlineClasses = new ArrayList<String>();
	Map<String, String> outlineClasses = new HashMap<String, String>();

	@Override
	public boolean run(Outline otln, Options optns, ErrorHandler eh) throws SAXException
	{

		for (ClassOutline c : otln.getClasses())
		{

			// outlineClasses.add(c.implClass.name());
			outlineClasses.put(c.implClass.name(), c.implClass.fullName());
		}

		// System.out.println(outlineClasses);
		System.out.println(outlineClasses.keySet());
		System.out.println(outlineClasses.values());

		for (ClassOutline c : otln.getClasses())
		{

			// System.out.println("Processing " + c.implClass.name());
			JClassContainer container = otln.getContainer((CClassInfoParent)c.target, Aspect.IMPLEMENTATION);

			try
			{
				makeCloneable(c, otln, container, false);
			}
			catch (ClassNotFoundException ex)
			{
				Logger.getLogger(CloneablePlugin.class.getName()).log(Level.SEVERE, null, ex);
			}
			// System.out.println("----------------------------------");

		}

		return true;
	}

	private ClassOutline getOutline(JClass c, Outline otln)
	{
		for (ClassOutline c2 : otln.getClasses())
		{
			if (c.equals(c2.implClass))
				return c2;
		}
		return null;
	}

	private Map<String, String> primitiveWrappers = new HashMap<String, String>()
	{
		{
			put("byte", "Byte");
			put("short", "Short");
			put("int", "Integer");
			put("long", "Long");
			put("float", "Float");
			put("double", "Double");
			put("char", "Character");
			put("boolean", "Boolean");
		}
	};

	private JDefinedClass makeCloneable(ClassOutline c, Outline otln, JClassContainer container, boolean inner)
		throws ClassNotFoundException
	{
		JClassContainer ccon = (inner) ? container : container.parentContainer();
		// System.out.println("implementing clonable " + c.implClass.name() + " in " + ((ccon.isPackage()) ?
		// ccon.getPackage().name() : ccon.toString()));
		// if its already defined then return it without processing.

		if (cmap.containsKey(c.implClass))
		{
			System.out.println("already done " + c.implClass.name());
			return (JDefinedClass)cmap.get(c.implClass);
		}

		JDefinedClass dc = c.implClass;

		dc._implements(Cloneable.class);
		cmap.put(c.implClass, dc);
		int mods = JMod.PUBLIC;

		JMethod clone = dc.method(mods, dc, "clone");
		clone.annotate(java.lang.Override.class);

		clone.body()._return(JExpr.invoke("clone").arg(JExpr._new(dc)));

		JMethod fakeClone = dc.method(mods, dc, "clone");
		fakeClone.param(dc, "clone");

		// JVar jvarAvpImpl = fakeClone.body().decl(dc, "clone");
		// jvarAvpImpl.init(JExpr._new(dc));

		for (String fieldName : dc.fields().keySet())
		{

			JFieldVar field = dc.fields().get(fieldName);
			JType fieldType = field.type();
			String fieldTypeName = field.type().name();

			createClone(fieldType, fieldTypeName, fieldName, fakeClone);

		}

		// var2.body().directStatement("clone.getModelTransientData().putAll(getModelTransientData());");

		fakeClone.body()._return(JExpr.cast(dc, JExpr._super().invoke("clone").arg(JExpr.direct("clone"))));
		// .directStatement("return super.clone(clone);");

		return dc;
	}

	private void createClone(JType fieldType, String fieldTypeName, String fieldName, JMethod var2)
	{
		if (fieldTypeName.startsWith("List<"))
		{
			createCloneForLists(fieldType, fieldTypeName, fieldName, var2);
		}
		else
		{
			createCloneForFields(fieldType, fieldTypeName, fieldName, var2);
		}
	}

	int nestedCount = 0;

	private void createCloneForLists(JType fieldType, String fieldTypeName, String fieldName, JMethod var2)
	{
		// TODO Auto-generated method stub

		int begin = fieldTypeName.indexOf("<") + 1;
		int end = fieldTypeName.lastIndexOf(">");

		String elementsType = fieldTypeName.substring(begin, end);

		if (outlineClasses.keySet().contains(elementsType))
		{
			var2.body().directStatement(
				"List<" + outlineClasses.get(elementsType) + ">" + " list" + ++nestedCount + " = " + fieldName + ";");
			var2.body().directStatement("if( list" + nestedCount + " != null){");
			var2.body().directStatement(
				"for(" + outlineClasses.get(elementsType) + " i" + nestedCount + " : list" + nestedCount + ")");
		}
		else
		{
			var2.body().directStatement(fieldTypeName + " list" + ++nestedCount + " = " + fieldName + ";");
			var2.body().directStatement("if( list" + nestedCount + " != null){");
			var2.body().directStatement("for(" + elementsType + " i" + nestedCount + " : list" + nestedCount + ")");
		}

		var2.body().directStatement("{\n");

		createForStatment(elementsType, fieldName, "i" + nestedCount, var2);

		var2.body().directStatement("}\n");
		var2.body().directStatement("}\n");

	}

	private void createForStatment(String elementsType, String fieldName, String nestedVarName, JMethod var2)
	{

		if (primitiveWrappers.keySet().contains(elementsType))
		{
			var2.body().directStatement(
				"clone.get" + capitalize(fieldName) + "().add(" + "new " + primitiveWrappers.get(elementsType) + "("
					+ nestedVarName + ")." + elementsType + "Value());");
		}

		if (primitiveWrappers.values().contains(elementsType))
		{

			var2.body().directStatement(
				"clone.get" + capitalize(fieldName) + "().add(" + "new " + elementsType + "(" + nestedVarName + "));");
		}
		else
		{
			if (!fieldName.startsWith("List<"))
			{
				if (outlineClasses.keySet().contains(elementsType))
				{
					JConditional check = var2.body()._if(JExpr.direct(nestedVarName + "!= null"));
					check._then().directStatement(
						"clone.get" + capitalize(fieldName) + "().add(" + nestedVarName + ".clone());");
				}
				else
				{
					JConditional check = var2.body()._if(JExpr.direct(nestedVarName + "!= null"));
					check._then().directStatement(
						"clone.get" + capitalize(fieldName) + "().add(" + nestedVarName + ");");
				}

			}
		}

	}

	private void createCloneForFields(JType fieldType, String fieldTypeName, String fieldName, JMethod var2)
	{

		if (fieldType.isPrimitive())
		{
			var2.body().directStatement(
				"clone." + fieldName + " = new " + primitiveWrappers.get(fieldTypeName) + "(this." + fieldName + ")."
					+ fieldTypeName + "Value();");
			return;
		}

		if (primitiveWrappers.values().contains(fieldTypeName))
		{
			JConditional check = var2.body()._if(JExpr.direct(fieldName + "!= null"));
			check._then().directStatement(
				"clone." + fieldName + " = new " + fieldTypeName + "(this." + fieldName + ");");
		}
		else
		{
			if (!fieldTypeName.startsWith("List<"))
			{
				if (outlineClasses.keySet().contains(fieldTypeName))
				{
					JConditional check = var2.body()._if(JExpr.direct(fieldName + "!= null"));
					check._then().directStatement("clone." + fieldName + " = this." + fieldName + ".clone();");
				}
				else
				{
					// enums and other classes which are not generated
					JConditional check = var2.body()._if(JExpr.direct(fieldName + "!= null"));
					check._then().directStatement("clone." + fieldName + " = this." + fieldName + ";");
				}

			}
		}
	}

	public static String capitalize(String s)
	{
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
}
