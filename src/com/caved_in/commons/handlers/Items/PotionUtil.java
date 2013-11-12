package com.caved_in.commons.handlers.Items;

import java.util.List;

import com.caved_in.commons.handlers.Utilities.NumberUtil;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionUtil
{
	public static PotionEffect getPotionEffect(PotionEffectType Type, int Duration, int Strength)
	{
		return new PotionEffect(Type, Duration, Strength);
	}

	public static PotionEffect getPotionEffect(PotionEffectType Type, int Minimum_Duration, int Maximum_Duration, int Strength)
	{
		return new PotionEffect(Type, NumberUtil.getRandomInRange(Minimum_Duration, Maximum_Duration), Strength);
	}

	public static void addPotionEffects(Player Player, PotionEffect... Effects)
	{
		for (PotionEffect Effect : Effects)
		{
			if (!Player.hasPotionEffect(Effect.getType()))
			{
				Player.addPotionEffect(Effect);
			}
		}
	}

	public static void addPotionEffects(Player Player, List<PotionEffect> Effects)
	{
		for (PotionEffect Effect : Effects)
		{
			if (!Player.hasPotionEffect(Effect.getType()))
			{
				Player.addPotionEffect(Effect);
			}
		}
	}
}
