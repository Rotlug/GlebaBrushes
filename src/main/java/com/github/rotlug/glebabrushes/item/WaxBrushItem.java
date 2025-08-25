package com.github.rotlug.glebabrushes.item;

import com.github.rotlug.glebabrushes.BrushState;
import net.createmod.catnip.theme.Color;
import net.minecraft.world.item.context.UseOnContext;

public class WaxBrushItem extends BaseBrushItem {
    public WaxBrushItem() {
        super(250);
    }

    @Override
    public void onBrushStart(BrushState state, UseOnContext context) {
        state.color = new Color(255,192,0);
        super.onBrushStart(state, context);
    }

    @Override
    public String getName() {
        return "wax";
    }
}
