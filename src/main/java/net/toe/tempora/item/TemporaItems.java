package net.toe.tempora.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.toe.tempora.Tempora;

public class TemporaItems {

    public static final Item TEMPORAL_CRYSTAL = registerItem("temporal_crystal",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item PREHISTORIC_TEMPORAL_CRYSTAL = registerItem("prehistoric_temporal_crystal",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Tempora.MOD_ID, name), item);
    }

    public static void registerItems() {
        Tempora.LOGGER.info("Registering items for " + Tempora.MOD_ID);
    }

}
