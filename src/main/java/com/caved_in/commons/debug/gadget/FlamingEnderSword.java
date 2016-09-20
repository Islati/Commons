package com.caved_in.commons.debug.gadget;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.exceptions.ProjectileCreationException;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.game.guns.BaseGun;
import com.caved_in.commons.game.item.BaseWeapon;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.potion.Potions;
import com.caved_in.commons.sound.Sounds;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class FlamingEnderSword extends BaseWeapon {

    private static FlamingEnderSword instance = null;

    public static FlamingEnderSword getInstance() {
        if (instance == null) {
            instance = new FlamingEnderSword();
            Gadgets.registerGadget(instance);

        }
        return instance;
    }

    private BaseGun enderGun = new BaseGun(ItemBuilder.of(Material.ENDER_STONE)) {

        @Override
        public void onFire(Player shooter) {

        }
    };

    protected FlamingEnderSword() {
        super(ItemBuilder.of(Material.WOOD_SWORD).name("&2Sword of Enders").lore("&cScorch your foes!"));
        properties().droppable(true).breakable(false);

        enderGun.bulletProperties().speed(4).damage(5).damageCondition((shooter, target) -> target.getType() != EntityType.ENDERMAN);
        enderGun.getBulletBuilder().gun(enderGun).type(Material.ENDER_PEARL);
    }

    @Override
    public void onSwing(Player p) {
        ParticleEffects.sendToLocation(ParticleEffects.DEPTH_SUSPEND, p.getLocation(), NumberUtil.getRandomInRange(1, 4));
        ParticleEffects.sendToLocation(ParticleEffects.DEPTH_SUSPEND, Players.getTargetLocation(p, 30), NumberUtil.getRandomInRange(1, 4));
    }

    @Override
    public void onActivate(Player p) {
        try {
            enderGun.getBulletBuilder().shooter(p).shoot();
        } catch (ProjectileCreationException e) {
            Chat.message(p, "Unable to fire bullets at target. Projectile Creation Exception");
        }
    }

    @Override
    public void onAttack(Player p, LivingEntity e) {
        EntityType type = e.getType();
        if (type == EntityType.ENDERMAN) {
            Chat.message(p, "&eYou attacked an enderman! F0k, let's kill em!");
            Entities.kill(e);
            return;
        }

        Entities.burn(e, 1, TimeType.MINUTE);
    }

    @Override
    public void onBreak(Player p) {
        Chat.message(p, "Your sword of enders has broken");
    }

    @Override
    public void onDrop(Player p, Item item) {
        Sounds.playSound(p, Sound.ENTITY_ENDERMEN_STARE);
        Chat.message(p, "&7The dark-side isn't fond of that disrespect");
        Players.addPotionEffect(p, Potions.getPotionEffect(PotionEffectType.BLINDNESS, 1, 160));
        item.remove();
    }
}
