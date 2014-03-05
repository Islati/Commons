package com.caved_in.commons.npc.wrappers;

import com.caved_in.commons.Commons;


public class BasicWrapper {

	private Object handle;

	protected void setHandle(Object handle) {
		if (handle == null) {
			Commons.messageConsole("Cannot set Wrapper-handle to NULL!");
			return;
		}
		this.handle = handle;
	}

	public Object getHandle() {
		return handle;
	}
}
