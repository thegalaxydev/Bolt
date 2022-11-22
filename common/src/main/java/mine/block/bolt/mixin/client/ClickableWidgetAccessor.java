package mine.block.bolt.mixin.client;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClickableWidget.class)
public interface ClickableWidgetAccessor {
    @Accessor
    Text getMessage();

    @Accessor
    void setWidth(int width);
}
