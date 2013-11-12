package com.caved_in.commons.handlers.Time;

import java.util.HashMap;

public class Cooldown
{
	private HashMap<String, Long> cooldowns = new HashMap<String, Long>();
	private int cooldownTime = 0;

	public Cooldown(int CooldownTime)
	{
		this.cooldownTime = CooldownTime;
	}

	public void setOnCooldown(String player)
	{
		this.cooldowns.put(player, Long.valueOf(System.currentTimeMillis() / 1000L));
	}

	public void setCooldownTime(int cooldownTime)
	{
		this.cooldownTime = cooldownTime;
	}

	public double getRemainingSeconds(String player)
	{
		if (this.cooldowns.containsKey(player))
		{
			double Last_Used = this.cooldowns.get(player).longValue();
			long Time_Check = System.currentTimeMillis() / 1000L;
			return Time_Check - Last_Used;
		}

		return 0.0D;
	}

	public double getRemainingMinutes(String player)
	{
		if (this.cooldowns.containsKey(player))
		{
			double Last_Used = this.cooldowns.get(player).longValue();
			long Time_Check = System.currentTimeMillis() / 1000L;
			return (Time_Check - Last_Used) / 60.0D;
		}

		return 0.0D;
	}

	public boolean isOnCooldown(String player)
	{
		if (this.cooldowns.containsKey(player))
		{
			double Last_Used = this.cooldowns.get(player).longValue();
			long Time_Check = System.currentTimeMillis() / 1000L;
			return (Time_Check - Last_Used) < this.cooldownTime;
		}
		return false;
	}
}