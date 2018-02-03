package com.devsteady.onyx.debug.gadget;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.effect.ParticleEffect;
import com.devsteady.onyx.effect.Particles;
import com.devsteady.onyx.entity.Entities;
import com.devsteady.onyx.exceptions.ProjectileCreationException;
import com.devsteady.onyx.game.gadget.Gadgets;
import com.devsteady.onyx.game.guns.BaseGun;
import com.devsteady.onyx.game.item.BaseWeapon;
import com.devsteady.onyx.item.ItemBuilder;
import com.devsteady.onyx.player.Players;
import com.devsteady.onyx.potion.Potions;
import com.devsteady.onyx.sound.Sounds;
import com.devsteady.onyx.time.TimeType;
import com.devsteady.onyx.utilities.NumberUtil;
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
        Particles.sendToLocation(p.getLocation(), ParticleEffect.SUSPENDED_DEPTH, NumberUtil.getRandomInRange(1, 4));
        Particles.sendToLocation(Players.getTargetLocation(p, 30), ParticleEffect.SUSPENDED_DEPTH, NumberUtil.getRandomInRange(1, 4));
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
        Sounds.playSound(p, Sound.ENDERMAN_HIT);
        Chat.message(p, "&7The dark-side isn't fond of that disrespect");
        Players.addPotionEffect(p, Potions.getPotionEffect(PotionEffectType.BLINDNESS, 1, 160));
        item.remove();
    }
}
