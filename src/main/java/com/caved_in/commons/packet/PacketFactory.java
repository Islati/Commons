package com.caved_in.commons.packet;

public class PacketFactory {

	private static int asFixedPoint(double value) {
		return (int) (value * 32.0D);
	}

	private static double fromFixedPoint(double value) {
		return value / 32.0D;
	}

	private static byte toPackedByte(float f) {
		return (byte) ((byte) f * 256.0F / 360.0F);
	}

	private static byte asFractionOf360(float f) {
		return (byte) ((byte) f / 360);
	}
}
