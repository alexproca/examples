package com.axway.migration.xjc.test;

import java.io.File;
import java.util.List;

import org.jvnet.hyperjaxb3.maven2.Hyperjaxb3Mojo;
import org.jvnet.jaxb2.maven2.AbstractXJC2Mojo;
import org.jvnet.jaxb2.maven2.test.RunXJC2Mojo;

import com.google.common.collect.ImmutableList;

public class TestJaxbPlugin extends RunXJC2Mojo
{

	@Override
	@SuppressWarnings("rawtypes")
	protected AbstractXJC2Mojo createMojo()
	{
		return new Hyperjaxb3Mojo();
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void configureMojo(AbstractXJC2Mojo mojo)
	{

		super.configureMojo(mojo);

		mojo.setExtension(true);
		mojo.setStrict(false);
		mojo.setForceRegenerate(true);

	}

	@Override
	public File getSchemaDirectory()
	{
		return new File("src/test/resources");
	}

	@Override
	protected File getGeneratedDirectory()
	{
		return new File("target/generated-sources/xjc");
	}

	// @Override
	// protected File getBaseDir()
	// {
	// return new File("");
	// }

	@Override
	public List<String> getArgs()
	{
		return ImmutableList.<String> of("-Xclone", "-Xfix-primitives");
	}

}
