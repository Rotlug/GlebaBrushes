package com.github.rotlug.glebabrushes.datagen;

import com.github.rotlug.glebabrushes.registry.BItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.github.rotlug.glebabrushes.registry.BItems.*;

public class BRecipeProvider extends RecipeProvider {

    public BRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.WHITE_DYE, 8), PAINT_BRUSH_WHITE.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.LIGHT_GRAY_DYE, 8), PAINT_BRUSH_LIGHT_GRAY.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.GRAY_DYE, 8), PAINT_BRUSH_GRAY.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.BLACK_DYE, 8), PAINT_BRUSH_BLACK.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.BROWN_DYE, 8), PAINT_BRUSH_BROWN.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.RED_DYE, 8), PAINT_BRUSH_RED.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.ORANGE_DYE, 8), PAINT_BRUSH_ORANGE.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.YELLOW_DYE, 8), PAINT_BRUSH_YELLOW.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.LIME_DYE, 8), PAINT_BRUSH_LIME.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.GREEN_DYE, 8), PAINT_BRUSH_GREEN.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.LIGHT_BLUE_DYE, 8), PAINT_BRUSH_LIGHT_BLUE.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.BLUE_DYE, 8), PAINT_BRUSH_BLUE.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.PURPLE_DYE, 8), PAINT_BRUSH_PURPLE.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.MAGENTA_DYE, 8), PAINT_BRUSH_MAGENTA.get());
        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.PINK_DYE, 8), PAINT_BRUSH_PINK.get());

        shapeless(recipeOutput, Map.of(Items.BRUSH, 1, Items.HONEYCOMB, 1), WAX_BRUSH.get());
    }

    public void shapeless(RecipeOutput recipeOutput, Map<ItemLike, Integer> items, Item itemOut) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, itemOut);
        items.keySet().forEach(itemLike -> {
            for (int i = 0; i < items.get(itemLike); i++) {
                builder.requires(itemLike);
            }
        });

        Item firstItem = (Item) items.keySet().toArray()[0];

        builder.unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(firstItem).getPath(), has(firstItem));

        builder.save(recipeOutput);
    }
}

