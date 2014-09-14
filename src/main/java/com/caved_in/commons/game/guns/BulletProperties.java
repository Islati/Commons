package com.caved_in.commons.game.guns;

import com.caved_in.commons.config.XmlParticleEffect;
import com.caved_in.commons.effect.ParticleEffects;
import org.simpleframework.xml.Element;

public class BulletProperties {
	/**
	 * How much damage each bullet does.
	 */
	@Element(name = "bullet-damage")
	public double damage = 2;

	@Element(name = "bullet-speed")
	public double speed = 6.5;

	@Element(name = "bullet-spread")
	public double spread = 0.0;

	@Element(name = "bullet-delay-ticks")
	public long delay = 2l;

	@Element(name = "particles", required = false, type = XmlParticleEffect.class)
	private XmlParticleEffect effect;

	private Gun parent;

	public BulletProperties(@Element(name = "bullet-damage") double damage, @Element(name = "bullet-speed") double speed, @Element(name = "bullet-spread") double spread, @Element(name = "bullet-delay-ticks") long delay, @Element(name = "particles", required = false, type = XmlParticleEffect.class) XmlParticleEffect effect) {
		this.damage = damage;
		this.speed = speed;
		this.spread = spread;
		this.delay = delay;
		this.effect = effect;
	}

	public BulletProperties(Gun gun) {
		this.parent = gun;
	}

	public BulletProperties() {
	}

	public BulletProperties damage(double damage) {
		this.damage = damage;
		return this;
	}

	public BulletProperties speed(double speed) {
		this.speed = speed;
		return this;
	}

	public BulletProperties spread(double spread) {
		this.spread = spread;
		return this;
	}

	public BulletProperties delayBetweenRounds(long ticks) {
		this.delay = ticks;
		return this;
	}

	public BulletProperties parent(Gun gun) {
		parent = gun;
		return this;
	}

	public BulletProperties effect(ParticleEffects effect) {
		this.effect = XmlParticleEffect.of(effect);
		return this;
	}

	public Gun parent() {
		return parent;
	}

	public boolean hasEffect() {
		return effect.getEffect() != null;
	}

	public ParticleEffects getEffect() {
		return effect.getEffect();
	}
}
