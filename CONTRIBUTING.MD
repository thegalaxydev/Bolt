# Contributing
## Licensing
All assets _must_ be MIT licensed, and you can _not_ contribute code that used non MIT assets.
## Naming Consistency
### Class Names
#### Mixins
Mixins should be formatted as `<ClassName><Mixin/Accessor>`, not the other way around.
If you are mixing an inner class (e.g `Item$Properties`), create a mixin for `Item` and `Item$Properties` and register them _both_ in the mixins json.

Example:
```java
@Mixin(Item.class)
public class ItemMixin {
    @Mixin(Item.Properties.class)
    public static class PropertiesMixin {
//       ...
    }
}
```
Interfaces added to classes via mixins should be suffixed with `Extension` and be in the `mine.block.bolt.extension` package.