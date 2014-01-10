package com.axway.migration.xjc.util;

import com.sun.codemodel.JDefinedClass;

/**
 * For debug purpose to see the code generated until that moment 
 * 
 * @author anproca
 *
 */
public class JDefinedClassWrapper {

	private JDefinedClass c;
	
	public JDefinedClassWrapper(JDefinedClass c)
	{
		this.setC(c);
	}
	
	@Override
	public String toString()
	{
		return Util.jDefinedClassToString(c);
	}

	public JDefinedClass getC() {
		return c;
	}

	public void setC(JDefinedClass c) {
		this.c = c;
	}
	
}
