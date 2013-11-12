package com.caved_in.commons.handlers.YML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;

public class YML_Wrapper
{
	private YMLIO YML;

	public YML_Wrapper(String File_Location)
	{
		try
		{
			this.YML = new YMLIO(new File(File_Location));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (InvalidConfigurationException e)
		{
			e.printStackTrace();
		}
	}

	private void setForceFalse()
	{
		this.YML.setForceSave(false);
	}

	private void setForceTrue()
	{
		this.YML.setForceSave(true);
	}

	private void Save()
	{
		this.YML.save();
	}

	public boolean getBoolean(String Path)
	{
		boolean BoolReturn = false;
		setForceFalse();
		BoolReturn = this.YML.get(Path, BoolReturn);
		return BoolReturn;
	}

	public String getString(String Path)
	{
		String Returning_String = "";
		setForceFalse();
		Returning_String = this.YML.get(Path, Returning_String);
		return Returning_String;
	}

	public int getInt(String Path)
	{
		int Returning_Int = 0;
		setForceFalse();
		Returning_Int = this.YML.get(Path, Returning_Int);
		return Returning_Int;
	}

	public double getDouble(String Path)
	{
		double Returning_Double = 0.0D;
		setForceFalse();
		Returning_Double = this.YML.get(Path, Returning_Double);
		return Returning_Double;
	}

	public void setDouble(String Path, double Data)
	{
		double SetData = Data;
		setForceTrue();
		SetData = this.YML.get(Path, SetData);
		setForceFalse();
		Save();
	}

	public boolean Contains(String Path)
	{
		return this.YML.contains(Path);
	}

	public void setBoolean(String Path, boolean Data)
	{
		boolean SetData = Data;
		setForceTrue();
		SetData = this.YML.get(Path, SetData);
		setForceFalse();
		Save();
	}

	public void setString(String Path, String Data)
	{
		String SetData = Data;
		setForceTrue();
		SetData = this.YML.get(Path, SetData);
		setForceFalse();
		Save();
	}

	public void setInt(String Path, int Data)
	{
		int SetData = Data;
		setForceTrue();
		SetData = this.YML.get(Path, SetData);
		setForceFalse();
		Save();
	}

	public long getLong(String Path)
	{
		long Returning = 0L;
		setForceFalse();
		Returning = this.YML.get(Path, Returning);
		return Returning;
	}

	public void setLong(String Path, long Data)
	{
		long SetData = Data;
		setForceTrue();
		SetData = this.YML.get(Path, SetData);
		setForceFalse();
		Save();
	}
}

/*
 * Location: C:\Users\Brandon\Desktop\TotalWar.jar Qualified Name:
 * com.caved_in.Config.YML_Wrapper JD-Core Version: 0.6.2
 */