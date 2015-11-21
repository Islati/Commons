package com.caved_in.commons.event;

import org.bukkit.event.Event;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum BukkitEventType {
    /*BLOCK EVENTS */
    BLOCK_BREAK(org.bukkit.event.block.BlockBreakEvent.class),
    BLOCK_BURN(org.bukkit.event.block.BlockBurnEvent.class),
    BLOCK_CAN_BUILD(org.bukkit.event.block.BlockCanBuildEvent.class),
    BLOCK_DAMAGE(org.bukkit.event.block.BlockDamageEvent.class),
    BLOCK_DISPENSE(org.bukkit.event.block.BlockDispenseEvent.class),
    BLOCK_EXP(org.bukkit.event.block.BlockExpEvent.class),
    BLOCK_EXPLODE(org.bukkit.event.block.BlockExplodeEvent.class),
    BLOCK_FADE(org.bukkit.event.block.BlockFadeEvent.class),
    BLOCK_FORM(BlockFormEvent.class),
    BLOCK_FROM_TO(BlockFromToEvent.class),
    BLOCK_GROW(BlockGrowEvent.class),
    BLOCK_IGNITE(BlockIgniteEvent.class),
    BLOCK_MULTI_PLACE(BlockMultiPlaceEvent.class),
    BLOCK_PHYSICS(BlockPhysicsEvent.class),
    BLOCK_PISTON(BlockPistonEvent.class),
    BLOCK_PISTON_EXTEND(BlockPistonExtendEvent.class),
    BLOCK_PISTON_RETRACT(BlockPistonRetractEvent.class),
    BLOCK_PLACE(BlockPlaceEvent.class),
    BLOCK_REDSTONE(BlockRedstoneEvent.class),
    BLOCK_SPREAD(BlockSpreadEvent.class),
    ENTITY_BLOCK_FORM(EntityBlockFormEvent.class),
    LEAVES_DECAY(LeavesDecayEvent.class),
    NOTE_PLAY(NotePlayEvent.class),
    SIGN_CHANGE(SignChangeEvent.class),
    /*ENCHANTMENT EVENTS */
    ENCHANT_ITEM(EnchantItemEvent.class),
    PREPARE_ENCHANT_ITEM(PrepareItemEnchantEvent.class);
    /*ENTITY EVENTS */

    private static Map<Class<? extends Event>, BukkitEventType> eventClasses = new HashMap<>();

    static {
        for(BukkitEventType eventType : EnumSet.allOf(BukkitEventType.class)) {
            eventClasses.put(eventType.aClass,eventType);
        }
    }

    private Class<? extends Event> aClass;
    BukkitEventType(Class<? extends Event> clazz) {

    }

    public static BukkitEventType getType(Event e) {
        for(Class<? extends Event> eClass : eventClasses.keySet()) {
            if (eClass.isInstance(e)) {
                return eventClasses.get(eClass);
            }
        }
        return null;
    }
}
