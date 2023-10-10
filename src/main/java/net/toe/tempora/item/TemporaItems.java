package net.toe.tempora.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.toe.tempora.Tempora;

public class TemporaItems {

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Tempora.MOD_ID, name), item);
    }

    public static void registerItems() {
        Tempora.LOGGER.info("Registering items for " + Tempora.MOD_ID);
    }

}
