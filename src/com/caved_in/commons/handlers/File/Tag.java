package com.caved_in.commons.handlers.File;

public class Tag
{
	private String openTag = "";
	private String closeTag = "";

	public Tag(String tag)
	{
		this.openTag = "<" + tag + ">";
		this.closeTag = "</" + tag + ">";
	}

	public String getOpen()
	{
		return this.openTag;
	}

	public String getClose()
	{
		return this.closeTag;
	}

	public String open()
	{
		return this.openTag;
	}

	public String close()
	{
		return this.closeTag;
	}
}
