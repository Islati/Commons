August 26'th
================

Additions
-----------
- Documented block & chat package along with all classes in each
- Documented ColorCode class
- Documented all Xml Wrapper classes (XmlEnchantment, XmlInventory, XmlItemStack, XmlLocation, XmlMenuItem, XmlPotionEffects, XmlSound)
- Documented cuboid package and all contained classes.
- Documented Debugger and DebugAction
- Documented effect package and all contained classes.
- Documented entity package and all contained classes.
- Created two ban methods in Players: ban(Player, Punishment) and ban(String, Punishment)
- Documented Players class
- Added ItemMessage to BukkitPlugin for plugins to use.
- Added debug method and logger creation to BukkitPlugin

Reductions
-----------
- Removed the 'packet' package
- Remove the 'disguise' package
- Removed the 'Action' class from event package.
- Renamed the DataHandler class to TextFile
- Moved the Tag class to be nested inside TextFile
- Removed debug method from static context
- Removed executors and handlers from static-view in Commons

Notes
-----------
- Moved the ChestType class from block.chest.ChestType to block.ChestType
- Refactored the ban command to use the new Players.ban(String / Player, Punishment)

August 25th
================

Additions
---------
- Added the clear(Player) method to PlayerTicker
- Created LimitedGadget class, which is a gadget limited by it's amount of uses
- Added the remove(Inventory, ItemStack) method to Inventories
- Added the removeItem(Player, ItemStack) method to Players
- Added the hasGadget(Player, Gadget) method to Players

Notes
---------
- Moved the Game API from commons.plugin.game to commons.game for Commons 1.5

August 22nd
================

Additions
---------
- Created Cuboids class, with basic world-edit like functionality.
- Made cuboid class serializable from an xml interface
- Created block replace data object, which holds information about block manipulation. (What chance a block has to be replaced when using it in an action).

Reductions
---------

Notes
---------
- Refactored ItemBuilder to have a much simpler naming scheme.

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
