package com.github.rotlug.glebabrushes;

import net.minecraft.network.chat.Component;

public enum BrushStateError {
    SELECTION_TOO_BIG("selection_too_big");

    public final Component translatable;

    BrushStateError(String id) {
        this.translatable = Component.translatable(Brushes.MODID + ".error." + id);
    }
}
