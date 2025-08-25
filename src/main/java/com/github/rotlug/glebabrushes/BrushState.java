package com.github.rotlug.glebabrushes;

import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;

/*
Represents the state of the brush on the client
 */
public class BrushState {
    public Level level;
    public @Nullable BlockPos startPos;
    public @Nullable BlockPos endPos;

    public Color color;
    public String name;

    public @Nullable BrushStateError error;

    public BrushState(Level level) {
        this.level = level;
        this.name = "";
        this.error = null;

        color = Color.WHITE;
    }

    @Override
    public String toString() {
        return String.format("{name=%s,start=%s,end=%s,color=%s}", name, startPos, endPos, color);
    }
}

