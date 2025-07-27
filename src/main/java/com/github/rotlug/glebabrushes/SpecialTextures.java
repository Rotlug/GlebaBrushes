package com.github.rotlug.glebabrushes;

import net.createmod.catnip.render.BindableTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public enum SpecialTextures implements BindableTexture {

    PAINT("paint.png");

    private final ResourceLocation location;

    public static final String ASSET_PATH = "textures/special/";

    SpecialTextures(String location) {
        this.location = ResourceLocation.fromNamespaceAndPath(Brushes.MODID, ASSET_PATH + location);
    }

    @Override
    public @NotNull ResourceLocation getLocation() {
        return location;
    }
}
