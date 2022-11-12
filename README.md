![](/banner.png)

Bolt is a utility mod for modpacks that adds numerous tweaks and miscelaneous features that are configurable.

Bolt is used by all Luna Pixel modpacks, and more. Simply add it to your modpack and run it once to generate the default configuration!

The original aim of Bolt is to minimize the amount of QoL and Tweak based mods in modpacks, allowing for more mods that actually add content - users prefer to download modpacks with about 200-350 mods, QoL and Tweak mods take up 15% of that, with a rough average of 30-50 mods being single mixins and little tweaks.

Currently, Bolt merges together 8 different mods; however it will expand to be much larger in the future.

## Tweaks, Features and Utilities

|Config Key| Default Value   |Description|
|----------|-----------------|-----------|
|`clearChatHistory`| false |Allows you to control whether chat history is cleared.|
|`disableChatClearing`| true |Chat messages don't fade out, letting you scroll up infinitely since you joined the world/server.|
|`disableResourcePackWarning`| true |Disables that annoying resource pack warning.|
|`skipLoadingTransition`| true Skips the loading screen fade.|
|`skipToastFadeOut`| true |Skips toast's fading out effect and disables the fade out sound effect.|
|`skipTitleFadeIn`| true |Skips the title screen's fade in effect.|
|`disableExperimentalWarning`| true |Disables the experimental warning when joining modded worlds or worlds with datapacks enabled.|
|`enableItemDespawnBlink`| true |Makes it so items blink when they're about to despawn.|
|`despawnBlinkSpeed`| 6000 |The amount of time in milliseconds before the item should start blinking.|
|`despawnBlinkStartTime`| 20 |The amount of time in milliseconds between a single blink.|
|`modpackBranding`| false |This will be appended to crash logs to provide infomation to the user and whoever is giving support.|

### Always Enabled Utilities

Features that are always enabled, as it makes zero sense to make them disablable or impossible to be configured are as followed:

#### Mixin Blame

When a crash involves a mixin, Bolt will show what mod the mixin is from, and output nessecary infomation.

### Modpack Branding

When the game crashes, Bolt will add modpack information to the crash log, allowing for easier identification on what version of the modpack was being used by the user.

By default, this is disabled. You will need to enable it and fill out your details into the `modpackBranding` config value.

```json
{
  // ...
  "modpackBranding": {
    "enabled": true,
    "modpackName": "Modpack Name",
    "modpackVersion": "1.0.0",
    "modpackAuthor": "Authors",
    "modpackWebsite": "https://example.com",
    "modpackSupport": "https://discord.gg/example"
  },
  // ...
}
```