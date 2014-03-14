package com.caved_in.commons.packet.protocol;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.packet.PacketUtil;
import com.caved_in.commons.reflection.SafeField;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;

public class Packet {

	private Object handle;
	private List<SafeField> fields;

	public Packet(Protocol protocol, Sender sender, int id) {
		this.handle = PacketUtil.getPacket(protocol, sender, id);

		fields = Lists.newArrayList();

		for (Field field : handle.getClass().getDeclaredFields()) {
			fields.add(new SafeField(field));
		}
	}

	public Packet(Object handle) {
		if (handle != null) {
			this.handle = handle;
			fields = Lists.newArrayList();

			for (Field field : handle.getClass().getDeclaredFields()) {
				fields.add(new SafeField(field));
			}
		} else {
			Commons.messageConsole(Messages.CANT_CREATE_NULL_PACKETS);
		}
	}

	public void write(int index, Object value) {
		fields.get(index).set(handle, value);
	}

	public void write(String name, Object value) {
		new SafeField(handle.getClass(), name).set(handle, value);
	}

	public Object read(int index) {
		return fields.get(index).get(handle);
	}

	public <T> T read(String name) {
		return new SafeField<T>(handle.getClass(), name).get(handle);
	}

	public Object getHandle() {
		return handle;
	}
}
