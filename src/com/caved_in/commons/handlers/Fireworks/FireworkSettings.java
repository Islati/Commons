package com.caved_in.commons.handlers.Fireworks;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;

public class FireworkSettings
{

	FireworkEffectPlayer fplayer = new FireworkEffectPlayer();

	public static Type randomType()
	{
		switch (new Random().nextInt(5))
		{
		case 0:
			return Type.BALL;
		case 1:
			return Type.BURST;
		case 2:
			return Type.CREEPER;
		case 3:
			return Type.STAR;
		case 4:
			return Type.BALL_LARGE;
		default:
			return Type.BALL;
		}
	}

	public static Color randomColor()
	{
		switch (new Random().nextInt(9))
		{
		case 1:
			return Color.WHITE;
		case 2:
			return Color.ORANGE;
		case 3:
			return Color.BLUE;
		case 4:
			return Color.YELLOW;
		case 5:
			return Color.GREEN;
		case 6:
			return Color.GRAY;
		case 7:
			return Color.PURPLE;
		case 8:
			return Color.RED;
		default:
			return Color.RED;
		}
	}

	public static boolean randomBoolean()
	{
		switch (new Random().nextInt(3))
		{
		case 1:
			return true;
		case 2:
			return false;
		default:
			return true;
		}
	}

	public void playFw(Location location, FireworkEffect fwEffect)
	{
		try
		{
			fplayer.playFirework(location.getWorld(), location, fwEffect);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public FireworkEffect randomFireworkEffect()
	{
		return FireworkEffect.builder().with(randomType()).withColor(randomColor()).withFade(randomColor()).trail(randomBoolean()).flicker(randomBoolean()).build();
	}
}