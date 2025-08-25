package com.github.rotlug.glebabrushes.datagen;

import com.github.rotlug.glebabrushes.Brushes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;

import javax.annotation.Nullable;

import static com.github.rotlug.glebabrushes.registry.BItems.*;

public class BLangProvider extends LanguageProvider {

    public BLangProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        item(WAX_BRUSH.get());
        item(PAINT_BRUSH_WHITE.get());
        item(PAINT_BRUSH_LIGHT_GRAY.get());
        item(PAINT_BRUSH_GRAY.get());
        item(PAINT_BRUSH_BLACK.get());
        item(PAINT_BRUSH_BROWN.get());
        item(PAINT_BRUSH_RED.get());
        item(PAINT_BRUSH_ORANGE.get());
        item(PAINT_BRUSH_YELLOW.get());
        item(PAINT_BRUSH_LIME.get());
        item(PAINT_BRUSH_GREEN.get());
        item(PAINT_BRUSH_CYAN.get());
        item(PAINT_BRUSH_LIGHT_BLUE.get());
        item(PAINT_BRUSH_BLUE.get());
        item(PAINT_BRUSH_PINK.get());
        item(PAINT_BRUSH_PURPLE.get());
        item(PAINT_BRUSH_MAGENTA.get());
        if (SOAP_BRUSH != null) {
            item(SOAP_BRUSH.get());
        }

        add("itemGroup.glebabrushes", "Brushes");
    }

    private void item(Item item) {
        String name = formatString(BuiltInRegistries.ITEM.getKey(item).getPath());
        add(item, name);
    }

    private void error(String id, String translation) {
        add(Brushes.MODID + ".error." + id, translation);
    }

    private String formatString(String key) {
        String[] strArr = key.split("_");
        StringBuffer res = new StringBuffer();
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);

            res.append(str).append(" ");
        }
        return res.toString().trim();
    }
}
