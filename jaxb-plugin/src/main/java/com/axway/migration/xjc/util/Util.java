package com.axway.migration.xjc.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFormatter;

public class Util {

	public static String jDefinedClassToString(JDefinedClass r)
	{
		try {
			StringWriter toString = new StringWriter();
			JFormatter f = new JFormatter(new PrintWriter(toString));
			Method write = f.getClass().getDeclaredMethod("write", new Class[]{JDefinedClass.class});
					
			
			write.setAccessible(true);
			
			write.invoke(f, new Object[]{r});
			
			write.setAccessible(false);
			
			f.close();
			return toString.toString();
			
		} catch (Throwable e) {
			return "";
		}
	}
	
}
