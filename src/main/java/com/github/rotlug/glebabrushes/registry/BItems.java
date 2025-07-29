package com.github.rotlug.glebabrushes.registry;

import com.github.rotlug.glebabrushes.Brushes;
import com.github.rotlug.glebabrushes.item.PaintBrushItem;
import com.github.rotlug.glebabrushes.item.SoapBrushItem;
import com.github.rotlug.glebabrushes.item.WaxBrushItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;

public class BItems {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.createItems(Brushes.MODID);

    public static DeferredHolder<Item, Item> PAINT_BRUSH_WHITE = ITEMS.register("white_paint_brush", () -> new PaintBrushItem(DyeColor.WHITE));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_LIGHT_GRAY = ITEMS.register("light_gray_paint_brush", () -> new PaintBrushItem(DyeColor.LIGHT_GRAY));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_GRAY = ITEMS.register("gray_paint_brush", () -> new PaintBrushItem(DyeColor.GRAY));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_BLACK = ITEMS.register("black_paint_brush", () -> new PaintBrushItem(DyeColor.BLACK));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_BROWN = ITEMS.register("brown_paint_brush", () -> new PaintBrushItem(DyeColor.BROWN));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_RED = ITEMS.register("red_paint_brush", () -> new PaintBrushItem(DyeColor.RED));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_ORANGE = ITEMS.register("orange_paint_brush", () -> new PaintBrushItem(DyeColor.ORANGE));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_YELLOW = ITEMS.register("yellow_paint_brush", () -> new PaintBrushItem(DyeColor.YELLOW));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_LIME = ITEMS.register("lime_paint_brush", () -> new PaintBrushItem(DyeColor.LIME));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_GREEN = ITEMS.register("green_paint_brush", () -> new PaintBrushItem(DyeColor.GREEN));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_CYAN = ITEMS.register("cyan_paint_brush", () -> new PaintBrushItem(DyeColor.CYAN));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_LIGHT_BLUE = ITEMS.register("light_blue_paint_brush", () -> new PaintBrushItem(DyeColor.LIGHT_BLUE));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_BLUE = ITEMS.register("blue_paint_brush", () -> new PaintBrushItem(DyeColor.BLUE));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_PURPLE = ITEMS.register("purple_paint_brush", () -> new PaintBrushItem(DyeColor.PURPLE));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_MAGENTA = ITEMS.register("magenta_paint_brush", () -> new PaintBrushItem(DyeColor.MAGENTA));
    public static DeferredHolder<Item, Item> PAINT_BRUSH_PINK = ITEMS.register("pink_paint_brush", () -> new PaintBrushItem(DyeColor.PINK));

    public static DeferredHolder<Item, Item> WAX_BRUSH = ITEMS.register("wax_brush", () -> new WaxBrushItem());

    public static @Nullable DeferredHolder<Item, Item> SOAP_BRUSH = ModList.get().isLoaded("supplementaries") ? ITEMS.register("soap_brush", () -> new SoapBrushItem()) : null;
}
