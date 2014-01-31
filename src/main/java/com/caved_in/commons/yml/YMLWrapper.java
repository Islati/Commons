package com.caved_in.commons.yml;

import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class YMLWrapper {
	private YMLIO YML;

	public YMLWrapper(String File_Location) {
		try {
			this.YML = new YMLIO(new File(File_Location));
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	private void disableForceSave() {
		this.YML.setForceSave(false);
	}

	private void enableForceSave() {
		this.YML.setForceSave(true);
	}

	public void setForceSave(boolean forceSave) {
		this.YML.setForceSave(forceSave);
	}

	private void saveData() {
		this.YML.save();
	}

	public boolean getBoolean(String Path) {
		boolean BoolReturn = false;
		disableForceSave();
		BoolReturn = this.YML.get(Path, BoolReturn);
		return BoolReturn;
	}

	public String getString(String Path) {
		String Returning_String = "";
		disableForceSave();
		Returning_String = this.YML.get(Path, Returning_String);
		return Returning_String;
	}

	public int getInt(String Path) {
		int Returning_Int = 0;
		disableForceSave();
		Returning_Int = this.YML.get(Path, Returning_Int);
		return Returning_Int;
	}

	public double getDouble(String Path) {
		double Returning_Double = 0.0D;
		disableForceSave();
		Returning_Double = this.YML.get(Path, Returning_Double);
		return Returning_Double;
	}

	public void setDouble(String Path, double Data) {
		double SetData = Data;
		enableForceSave();
		SetData = this.YML.get(Path, SetData);
		disableForceSave();
		saveData();
	}

	public boolean Contains(String Path) {
		return this.YML.contains(Path);
	}

	public void setBoolean(String Path, boolean Data) {
		boolean SetData = Data;
		enableForceSave();
		SetData = this.YML.get(Path, SetData);
		disableForceSave();
		saveData();
	}

	public void setString(String Path, String Data) {
		String SetData = Data;
		enableForceSave();
		SetData = this.YML.get(Path, SetData);
		disableForceSave();
		saveData();
	}

	public void setInt(String Path, int Data) {
		int SetData = Data;
		enableForceSave();
		SetData = this.YML.get(Path, SetData);
		disableForceSave();
		saveData();
	}

	public long getLong(String Path) {
		long Returning = 0L;
		disableForceSave();
		Returning = this.YML.get(Path, Returning);
		return Returning;
	}

	public void setLong(String Path, long Data) {
		long SetData = Data;
		enableForceSave();
		SetData = this.YML.get(Path, SetData);
		disableForceSave();
		saveData();
	}
}

/*
 * Location: C:\Users\Brandon\Desktop\TotalWar.jar Qualified Name:
 * com.caved_in.Config.YMLWrapper JD-Core Version: 0.6.2
 */