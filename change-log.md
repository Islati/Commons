August 21'st
================

Additions
---------
- CreatureBuilder class, a builder class to easily spawn and customize creatures
- ArmorInventory class, a wrapper around EntityEquipment, used in conjunction with Entities / Players to easily assign equipment.
- ArmorBuilder class, a builder interface for the ArmorInventory wrapper.
- Added registerDebugActions method to BukkitPlugin class
- Added some Javadocs to Players
- Created DebugCreatureBuilder
- Created DebugArmorBuilder

Reductions
---------
- Removed EntityArmorSlot and moved calls globally to ArmorSlot

August 20'th
================

Additions
---------
- Javadocs for Entities class & methods

Notes
---------
- renamed Entities.spawnRainbowSheep to spawnRandomSheep

August 18'th, 2014
================

Additions
---------
- Added messages for actions regarding arenas (Loading, adding, etc)

Notes
---------
- Chanded the addArena method in ArenaHandler to return a boolean.

August 14'th, 2014
==================

Additions
---------
- Added 'filterCollection' method to Entities
- Added 'reduceCollection' method to Entities
- Added 'selectEntitiesNearLocation' method to Entities
- Added 'selectLivingEntitiesNearLocation' method to Entities
- Added 'setHotbarSelection' method to Players
- Added 'setHotbarItem' method to Players
- Added 'setHotbarContents' method to Players

Reductions
---------
- Removed 'getHandle' method from Entities
- Removed 'setPositionRotation' method from Entities
- Removed 'addEntity' method from Entities
- Removed 'invokeProjectile' method from Entities
- Removed 'getBukkitEntity' method from Entities

Notes
---------

- I deprecated Items.makeItem(Material, String, String...) as it wouldn't compile due to how var-args work.
- Moved 'registerGadgets' method from Subclass MiniGame to super-class BukkitPlugin
- Removed deprication on Sounds.playSoundForPlayersAtLocation

Date
================

Additions
---------

Reductions
---------

Notes
---------
