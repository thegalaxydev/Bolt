![](/banner.png)

Bolt is a utility mod for modpacks that adds numerous tweaks and miscellaneous features that are configurable.

Bolt is used by all Luna Pixel modpacks, and more. Simply add it to your modpack and run it once to generate the default configuration!

The original aim of Bolt is to minimize the amount of QoL and Tweak based mods in modpacks, allowing for more mods that actually add content - users prefer to download modpacks with about 200-350 mods, QoL and Tweak mods take up 15% of that, with a rough average of 30-50 mods being single mixins and little tweaks.

Currently, Bolt merges together 8 different mods; however it will expand to be much larger in the future.

**Mods that Bolt replaces:**

- Better Compatability Checker 
- Cut Through Grass
- Skip Splash Fade
- Skip Title Fade
- Skip Toast Fade
- CrashBrander
- Netherportal Fix
- Disable Chat Clearing
- Infinite Chat History
- Disable Experimental Warning 
- Item Despawn Blinker
- Hand Hider

## Tweaks, Features and Utilities

| __**Config Key**__           | __**Default Value**__ | __**Description**__                                                                                                                                                                                     |
|------------------------------|-----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `clearChatHistory`           | false                 | Allows you to control whether chat history is cleared.                                                                                                                                                  |
| `disableChatClearing`        | true                  | Chat messages don't fade out, letting you scroll up infinitely since you joined the world/server.                                                                                                       |
| `disableResourcePackWarning` | true                  | Disables that annoying resource pack warning.                                                                                                                                                           |
| `skipLoadingTransition`      | true                  | Skips the loading screen fade.                                                                                                                                                                          |
| `skipToastFadeOut`           | true                  | Skips toast's fading out effect and disables the fade out sound effect.                                                                                                                                 |
| `skipTitleFadeIn`            | true                  | Skips the title screen's fade in effect.                                                                                                                                                                |
| `disableExperimentalWarning` | true                  | Disables the experimental warning when joining modded worlds or worlds with datapacks enabled.                                                                                                          |
| `enableItemDespawnBlink`     | true                  | Makes it so items blink when they're about to despawn.                                                                                                                                                  |
| `despawnBlinkSpeed`          | 6000                  | The amount of time in milliseconds before the item should start blinking.                                                                                                                               |
| `despawnBlinkStartTime`      | 20                    | The amount of time in milliseconds between a single blink.                                                                                                                                              |
| `modpackBranding`            | See below             | This will be appended to crash logs to provide information to the user and whoever is giving support.<br/>It will also be used to check if servers are compatible with the currently installed modpack. |
| `enableCutThroughGrass`      | false                 | Allows the player to attack entities through grass.                                                                                                                                                     |
| `enableHideHandKeybind`      | false                 | Allows the hands of the player to be hidden when pressing a keybind (F10 by default)                                                                                                                    |

### Always Enabled Utilities

Features that are always enabled, as it makes zero practical sense to make them configurable are as followed:

#### Mixin Blame

When a crash involves a mixin, Bolt will show what mod the mixin is from, and output necessary information.

#### Nether Portal Linking Fix

Fixes issue with linking nether portals that are rotated facing West or East

### Modpack Branding

When the game crashes, Bolt will add modpack information to the crash log, allowing for easier identification on what version of the modpack was being used by the user.

By default, this is disabled. You will need to enable it and fill out your details into the `modpackBranding` config value.

Example: (Better MC Fabric)

```json
{
  // ...
  "modpackBranding": {
    "enabled": true,
    "modpackName": "Better MC [Fabric]",
    "titleScreenBrander": true, // only works on Fabric, disable if you have FancyMenu
    "modpackVersion": {
      "ID": "69420",
      "semName": "v18",
      "releaseType": "Release"
    },
    "modpackID": "better-mc-fabric",
    "modpackAuthors": [
      "Luna Pixel Studios",
      "Shark person"
    ],
    "URLS": {
      "website": "https://luna-pixel-studios/",
      "support": "https://luna-pixel-studios/",
      "repository": "https://luna-pixel-studios/"
    }
  },
  // ...
}
```
