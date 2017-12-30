package com.caved_in.commons.item;

import org.bukkit.enchantments.Enchantment;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Enchantments {
    ARROW_DAMAGE(Enchantment.ARROW_DAMAGE, "arrowdamage", "power", "arrowpower", "ad"),
    ARROW_FIRE(Enchantment.ARROW_FIRE, "arrowfire", "firearrow", "flamearrow", "flame", "af"),
    ARROW_INFINITE(Enchantment.ARROW_INFINITE, "infinity", "infinitearrows", "infarrow", "infinite", "unlimited", "unlimitedarrows", "ai"),
    ARROW_KNOCKBACK(Enchantment.ARROW_KNOCKBACK, "arrowknockback", "punch", "ak", "arrowpunch", "arrowkb"),
    DAMAGE_ALL(Enchantment.DAMAGE_ALL, "damageall", "alldamage", "alldmg", "sharpness", "sharp", "dal"),
    DAMAGE_ARTHROPODS(Enchantment.DAMAGE_ARTHROPODS, "ardmg", "baneofarthropods", "arthropod", "damagearthropod", "baneofarthropod", "dar"),
    DAMAGE_UNDEAD(Enchantment.DAMAGE_UNDEAD, "smite", "damageundead", "du", "damage_undead"),
    DIG_SPEED(Enchantment.DIG_SPEED, "digspeed", "effeciency", "minespeed", "cutspeed","eff"),
    DURABILITY(Enchantment.DURABILITY, "durability", "dura", "unbreaking"),
    FIRE_ASPECT(Enchantment.FIRE_ASPECT, "fireaspect", "fire", "meleefire", "meleeflame", "fa"),


    KNOCKBACK(Enchantment.KNOCKBACK, "knockback", "kback", "kb", "k"),
    LOOT_BONUS_BLOCKS(Enchantment.LOOT_BONUS_MOBS, "blockslootbonus", "fortune", "fort", "lbb"),
    LOOT_BONUS_MOBS(Enchantment.LOOT_BONUS_MOBS, "moblootbonus", "mobloot", "looting", "lbm"),
    LUCK(Enchantment.LUCK, "luck", "luckofsea", "luckofseas", "rodluck"),
    LURE(Enchantment.LURE, "lure", "rodlure"),
    /** New In 1.9 **/
    OXYGEN(Enchantment.OXYGEN, "oxygen", "respiration", "breathing", "breath", "o"),
    PROTECTION(Enchantment.PROTECTION_ENVIRONMENTAL, "protection", "prot", "protect", "p"),
    PROTECTION_EXPLOSION(Enchantment.PROTECTION_EXPLOSIONS, "blastprotect", "explodeprotect", "expprotect", "bprotect", "pe"),
    PROTECTION_FALL(Enchantment.PROTECTION_FALL, "fallprotection", "fallprotect", "featherfall", "pfa", "featherfalling"),
    PROTECTION_FIRE(Enchantment.PROTECTION_FIRE, "fireprotection", "fireprotect", "flameprotection", "flameprotect", "pf"),
    PROTECTION_PROJECTILE(Enchantment.PROTECTION_PROJECTILE, "projectileprotection", "protectprojectile", "projprot", "pp", "protproj"),
    SILK_TOUCH(Enchantment.SILK_TOUCH, "silktouch", "st", "softtouch", "silkt"),
    THORNS(Enchantment.THORNS, "thorns", "crit", "highcrit", "t", "thorn"),
    WATER_WORKER(Enchantment.WATER_WORKER, "waterworker", "aquaaffinity", "watermine", "ww");

    private static Map<String, Enchantment> enchantments = new HashMap<>();

    static {
        //Loop through all the enchantments and add them to our enchantments list
        for (Enchantments enchant : EnumSet.allOf(Enchantments.class)) {
            //Add all the enchantment aliases to a map for easier searching
            for (String alias : enchant.enchantmentNames) {
                enchantments.put(alias, enchant.enchantment);
            }
        }

        for (Enchantment enchant : Enchantment.values()) {
            enchantments.put(enchant.getName(), enchant);
        }
    }

    private Enchantment enchantment;
    private String[] enchantmentNames;

    Enchantments(Enchantment enchantment, String... enchantmentNames) {
        this.enchantment = enchantment;
        this.enchantmentNames = enchantmentNames;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public String[] getAliases() {
        return enchantmentNames;
    }

    public String getMainAlias() {
        return getAliases()[0];
    }

    public static Enchantment getEnchantment(String name) {
        return enchantments.get(name.toLowerCase());
    }

    public static boolean isEnchantment(String name) {
        return enchantments.containsKey(name) || Enchantment.getByName(name) != null;
    }

}
