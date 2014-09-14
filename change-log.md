September 14'th
===============
- Added methods for direction (vector subtraction), and subtraction to Vectors.
- Updated CreatureHitAction and BlockHitAction to both take a player argument
- Moved Bullet*Events to game.events package
- Moved BaseGun to guns package
- Created BulletProperties class, containing information specific to the bullets being fired.
- Created FancyBullet, which is followed by a trail of particles (UN-TESTED)
- Created XmlPotionEffect 
- Fixed bug with ItemBuilders not translating color-codes in lore

September 11'th
===============
- Gave each player seperate ammo counts. derp, was supposed to have that last time.

September 10'th
================
- Added cooldown between shots for guns
- Added bullet spread for guns
- Created interfaces for bullet actions, allowing the same characteristics to be applied to different guns
- Renamed ProjectileLauncher to Gun
- Renamed ItemProjectile to Bullet
- Created CreatureHitAction
- Created BlockHitAction
- Created BulletActions
- Created Vectors package work utilities. So far only contains the method to retrieve bullet-spread for vectors.
- Created BulletHitBlockEvent
- Created BulletHitCreatureEvent
- Added cluster shots to guns
- Added adjustable reload speeds to guns
- Created the FishCannon easter-egg gun, you'll only ever know of it by using /debug fish_cannon ;)
- Created guns api
- Wrote new events for guns and bullets

September 8'th
================
- Finished Projectiles. WOOOOO
- Wrote test implementation for projectiles.

September 4'th
================

Additions
----------
- Finished hotbar implementation.
- Wrote methods in Players to assign the new hotbar implementation to players
- Wrote ProjectileCreationException
- Wrote ItemProjectile class
- Wrote ProjectileBuilder class
- Wrote PropertiesBuilder and PropertiesItem interfaces
- Wrote ItemOperation interface

September 3'rd
================

Additions
-----------
- New Hotbar class; Serializable hotbar that's assignable to players
- Entities: getLivingEntitiesNear(Entity, double, double, double)
- Entities: selectEntitiesNear(Entity, double, double, double, EntityType...)

Reductions
-----------
- Removed WrappedPlayer class, better to use the User class.

September 2'nd
================

Additions
-----------
- Created User class: Basic, abstract implementation of PlayerWrapper
- Began projectile package, used to create throwable items.

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
