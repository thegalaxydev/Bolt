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

| __**Config Key**__| __**Default Value**__ | __**Description**__|
|-------------|-----------------------|----------------|
|clearChatHistory| false                 |Allows you to control whether chat history is cleared.|
|disableChatClearing| true                  |Whether or not chat messages fade out, letting you scroll up infinitely since you joined the world/server.|
|disableResourcePackWarning| true                  |Disable the annoying resource pack warning.|
|skipLoadingTransition| true                  |Skips the loading screen fade.|
|skipToastFadeOut| false                 |Skips toast's fading out effect and disables the fade out sound effect.|
|skipTitleFadeIn| true                  |Skips the title screen's fade in effect.|
|disableExperimentalWarning| true                  | Disables the experimental warning when joining modded worlds or worlds with datapacks enabled.|
|enableItemDespawnBlink| true                  |Makes it so items blink when they're about to despawn.|
|despawnBlinkSpeed| 6000                  |The amount of time in milliseconds before the item should start blinking.|
|despawnBlinkStartTime| 20                    |The amount of time in milliseconds between a single blink.|
|enableQuickCrouch| false                 |If quick crouching should be enabled - should crouching be sped up?|
|quickCrouchSpeed| 10.0                  |The time that it takes to crouch in milliseconds.|
|enableCutThroughGrass| false                 |Allows the player to attack entities through grass.|
|enableHideHandKeybind| false                 |Allows the hands of the player to be hidden when pressing a keybind (F10 by default)|
|modpackBranding| See below.            |When the game crashes, Bolt will add modpack information to the crash log, allowing for easier identification on what version of the modpack was being used by the user.|

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

```json5
{
  // ...
  "modpackBranding": {
    // Whether to enable modpack branding.
    "enabled": false,
    /* Whether to show the modpack name and version on the main menu (Fabric only.) 
       Please disable if FancyMenu is installed.
    */
    "enableTitlescreenBranding": true,
    // The name of the modpack.
    "modpackName": "Modpack Name",
    // Information on the current version of the modpack.
    "modpackVersion": {
      // A human-readable ID of the current version.
      "ID": "v69-my-modpack-fabric",
      // The current semantic version of the modpack.
      "semName": "69.0.0",
      /* The release type of the current version.
         Beta, Release or Alpha
      */
      "releaseType": "Beta",
    },
    // The ID of the modpack (curseforge/modrinth slug)
    "modpackID": "",
    // The authors of the modpack.
    "modpackAuthors": [
      "Author 1",
      "Author 2",
    ],
    /* Mods that are provided by the modpack - used for badges on fabric and marks any mods not in this list as non-included in crash reports.
       Use %CF_SEARCH% inside the array to automatically add values from the curseforge modpack instance json.
    */
    "providedByModpack": [],
    // URLs that will be put in the crash report to help users find support.
    "URLS": {
      "website": "https://example.com/emergency-meeting",
      "support": "https://discord.gg/red-is-the-imposter",
      "repository": "https://github.com/iamdefinitely/notsus",
    },
  },
  // ...
}
```
