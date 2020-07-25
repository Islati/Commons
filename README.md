Commons
========
_**Note: Commons is no longer maintained (July 2020)**_

Don't worry any more about the boiler-plating required to design a plugin, simply add Commons as a dependency, and get to work.



Features:
----

* Libraries shaded in Commons: Simple XML, JavaTuples, Commons-IO, Project Lombok, jOOR, and Reflections.
* Annotation based Command System! [Example](https://github.com/TechnicalBro/Commons-Examples/blob/master/src/main/java/com/caved_in/commonsexamples/basic_plugin/command/CommandsExample.java)
* Simple base for MiniGames [Example](https://github.com/TechnicalBro/Commons-Examples/blob/master/src/main/java/com/caved_in/commonsexamples/minigame/MiniGameExample.java)
* Easy to use Gadgets System! [Example](https://github.com/TechnicalBro/Commons-Examples/blob/master/src/main/java/com/caved_in/commonsexamples/basic_plugin/gadget/ChickenLauncher.java)
  * Custom Arrows [Example](https://github.com/TechnicalBro/Commons/blob/master/src/main/java/com/caved_in/commons/debug/gadget/ProtoExplosionArrow.java)
  * Guns and Bullets! [Example](https://github.com/TechnicalBro/Commons/blob/master/src/main/java/com/caved_in/commons/debug/gadget/FishCannon.java)
  * Custom Weapons [Example](https://github.com/TechnicalBro/Commons/blob/master/src/main/java/com/caved_in/commons/debug/gadget/KickStick.java)
  * Throwable Items [Example](https://github.com/TechnicalBro/Commons/blob/master/src/main/java/com/caved_in/commons/debug/gadget/ThrowableBrick.java)
* MiniGame API! ([Example](https://github.com/TechnicalBro/Commons-Examples/blob/master/src/main/java/com/caved_in/commonsexamples/minigame/MiniGameExample.java)
  * State based handling to easily control the flow of your game! (Examples: [Pre Game](https://github.com/TechnicalBro/Commons-Examples/blob/master/src/main/java/com/caved_in/commonsexamples/minigame/game/state/PendingGameState.java), [Game Running](https://github.com/TechnicalBro/Commons-Examples/blob/master/src/main/java/com/caved_in/commonsexamples/minigame/game/state/PlayingGameState.java), [Post Game](https://github.com/TechnicalBro/Commons-Examples/blob/master/src/main/java/com/caved_in/commonsexamples/minigame/game/state/EndGameState.java))
  * Abstracted User (player) data that can be serialized! ([Example](https://github.com/TechnicalBro/Commons-Examples/blob/master/src/main/java/com/caved_in/commonsexamples/minigame/users/GamePlayer.java), [Source](https://github.com/TechnicalBro/Commons/blob/master/src/main/java/com/caved_in/commons/player/User.java))
  * User Manager for handling data (say, loading stored player information)
* Utilities
  * Items
  * Inventories
  * Locations
  * Entities
  * Players
  * Blocks
  * Chat
  * Reflection (JOOR, thank me later)
  
* GUI Creation [Example](https://github.com/TechnicalBro/Commons/blob/master/src/main/java/com/caved_in/commons/menus/warpselection/WarpSelectionMenu.java)
* @Annotation YML Serialization with Type Adapters [Example](https://github.com/TechnicalBro/Commons/blob/master/src/main/java/com/caved_in/commons/config/CommonsYamlConfiguration.java)
* Full Featured Debugger! [Examples](https://github.com/TechnicalBro/Commons/tree/master/src/main/java/com/caved_in/commons/debug/actions)
* Tons of commands implemented to use or learn from. [Example](https://github.com/TechnicalBro/Commons/tree/master/src/main/java/com/caved_in/commons/command/commands)
* So much more...


Configuration:
----
```yaml

# Any concerns regarding the purpose of configuration nodes
# What they affect, or how they change aspects of the API
# are described under the Wiki on Commons GitHub page.

Database:
  Mysql:
    enable: false
    host: localhost
    port: '3306'
    database-name: minecraft
    username: username
  track-online-status: false
  # Used in the chosen database implementation
  # to identify the server.
  server-name: EDIT THIS
Commands:
  # By default Commons includes a plethora of commands
  # Designed to aid you in your server ventures!
  # Though if you're not requiring use of these commands, and
  # Wish to use Commons for only its API Features, then change this value to
  # False
  register-commands: true
  # Allows usage of 'Bukkit:' prefixed commands
  # Changing this value to false disable these commands
  # from being used on your server.
  enable-bukkit-commands: true
  # Changing the value of this option to false
  # Stops players from using '/plugins' on your server.
  enable-plugins-command: true
Server:
  # Whether or not to enable join messages
  # in chat when a player joins the server
  enable-join-message: true
  # Whether or not to enable leave messages
  # in chat when a player leaves the server
  enable-leave-messages: true
  # Whether or not to show 'player was kicked'
  # messages in chat, when a player is kicked.
  enable-kick-messages: true
  # Determines whether or not Commons should
  # handle chat formatting (in a very basic manner)
  # or to hand it off to another plugin
  external-chat-plugin: true
  # When enabled, only players with 'commons.silence.bypass'
  # in their permissions will be able to talk
  silence-chat: false
  Premium:
    # Determines whether or not to kick a non-premium player
    # when a premium user joins the server, and the server
    # is currently full.
    # Premium users are determined by the 'premium-user-permission'
    # node below.
    kick-when-full: false
    # Message to display to non-premium users after being
    # kicked to make room for a premium user.
    kick-when-full-message: '&eYou were kicked to make room for a Premium User. Sorry.'
    # When enabled, only users with premium (defined by a permission below)
    # will be able to join your server.
    # Those without premium will be displayed a configurable message
    premium-only-mode: false
    # This message will be shown to non-premium users who join during premium-only mode.
    premium-only-mode-kick-message: '&cThis server is currently in premium mode'
    # Used to restrict access during premium only mode
    # To players who have this permission
    premium-user-permission: commons.premiumuser
  Worlds:
    # All the options beneath this are used to control
    # various aspects of the worlds across all
    # the enabled worlds on your server.
    #
    # If you have another plugin enabled that also
    # Modifies any of these values, there's no guarantee
    # that they will function as expected.
    disable-weather: false
    # When enabled, players will be teleported to their world spawn when joining the server
    teleport-to-spawn-on-join: false
    # Changes whether or not lightning will strike during a storm
    disable-lightning: false
    # Changes whether or not thunder will rumble during a storm
    disable-thunder: false
    # Changes whether or not ice will spread and accumulate
    disable-ice-accumulation: false
    # Changes whether or not snow will accumulate while snowing
    disable-snow-accumulation: false
    # Changes whether or not mycelium will infect blocks around it, and spread
    disable-mycelium-spread: false
    # Changes whether or not fire will spread
    disable-fire-spread: false
    # Changes whether or not leaves will decay over time
    disable-leaf-decay: false
    # When enabled it changes pressure plates into launch pads, like many server hubs have
    launchpad-pressure-plates: false
    # Changes whether or not blocks can be broken outside of creative
    enable-block-break: true
    # Changes if players are able to pick up items that are dropped
    enable-item-pickup: true
    # Changes if players are able to drop their items
    enable-item-drop: true
    # Changes whether or not players lose their hunger while playing
    enable-food-change: true
    # When enabled, fireworks will launch and explode whenever a regular explosion happens
    fireworks-on-explosion: false
    # Changes whether or not players take fall damage
    enable-fall-damage: true
  Maintenance-Mode:
    # Maintenance mode enables admins, operators, and users
    # with the 'commons.maintenance.join' permission
    # to join while the server is undergoing maintenance.
    # At the same time, it keeps all players not permitted, out, until
    # maintenance is complete!
    #
    # Customizable MOTD (Server list message)
    # and kick message are available to notify users of
    # maintenance!
    enabled: false
    kick-message: '&cThis server is currently undergoing maintenance; Sorry for the
      inconvenience'
    motd: '&aThis server is currently undergoing maintenance'
Debug:
  # Debug options are very useful to developers!
  # Providing a StackTraceEvent, and various output options
  # which enable in-game players in debug mode
  # and developers hooking the event to
  # track, handle, change, and work with the headaches of bug fixing
  # in an easy and fun manner!
  stack-trace-event: true
  # When enabled in conjunction with stack-trace-event
  # users in debug mode will receive a Book in-game outlining
  # The error which happened, and it's stack trace written in the books
  # pages!
  stack-trace-book: false
  # When enabled in conjunction with stack-trace-event,
  # users in debug mode will receive the stack trace in their chat;
  # so eyes don't have to stray from game, to console, to code.
  #
  # Note: Can quickly and painfully spam your chat if to many
  # errors occur
  stack-trace-chat: true
Warps:
  # When enabled, it provides an interactive GUI
  # of which players can use to teleport and interact
  # with warps.
  enable-gui: true
```

Commands:
----
The list of commands is quickly growing to be to much to keep on this page; For information about each Command, its syntax, arguments, and permissions: [Click here to go to the Wiki.](https://github.com/TechnicalBro/Commons/wiki/Commands-&-Usage)

Permissions:
----
All the permissions have been copied from the plugin.yml file found inside Commons; Assign them accordingly. Any suggestions for actions that should be restricted via permission, please let me know!

Click here to view a comprehensive list of all the permissions inside Commons, on the Wiki.

Usage, and Tutorials!
----

To Start, adding 'depend: [Commons]' to your plugin.yml file, inside the plugin you're making, will allow you to use Commons as a dependency on the plugin level.

Next is to add your plugin as a dependency! If you're using maven enter the following information:
```xml
<dependency>
    <groupId>com.caved_in</groupId>
    <artifactId>commons</artifactId>
    <version>1.8.8-3</version>
    <scope>system</scope>
    <systemPath>commons-location/commons-{version}.jar</systemPath>
</dependency>
```
