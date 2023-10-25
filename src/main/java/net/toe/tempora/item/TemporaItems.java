package net.toe.tempora.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.toe.tempora.Tempora;
import net.toe.tempora.item.custom.SoulRing;

public class TemporaItems {

    // SOUL RING
    public static final Item SOUL_RING = registerItem("soul_ring",
            new SoulRing(new FabricItemSettings().group(TemporaItemGroup.TEMPORA)));

    // DEFAULT JADE
    public static final Item TEMPORAL_CRYSTAL = registerItem("temporal_crystal",
            new Item(new FabricItemSettings().group(TemporaItemGroup.TEMPORA)));

    // PREHISTORIC JADES
    public static final Item PREHISTORIC_TEMPORAL_CRYSTAL = registerItem("prehistoric_temporal_crystal",
            new Item(new FabricItemSettings().group(TemporaItemGroup.TEMPORA)));

    public static final Item PREHISTORIC_CRYSTAL = registerItem("prehistoric_crystal",
            new Item(new FabricItemSettings().group(TemporaItemGroup.TEMPORA)));

    // PREHISTORIC ORES

    public static final Item RAW_CHRONOSTONE = registerItem("raw_chronostone",
            new Item(new FabricItemSettings().group(TemporaItemGroup.TEMPORA)));

    public static final Item CHRONOSTONE = registerItem("chronostone",
            new Item(new FabricItemSettings().group(TemporaItemGroup.TEMPORA)));

    // MEDIEVAL JADES
    public static final Item MEDIEVAL_TEMPORAL_CRYSTAL = registerItem("medieval_temporal_crystal",
            new Item(new FabricItemSettings().group(TemporaItemGroup.TEMPORA)));

    public static final Item MEDIEVAL_CRYSTAL = registerItem("medieval_crystal",
            new Item(new FabricItemSettings().group(TemporaItemGroup.TEMPORA)));

    // MODERN JADES


    // FUTURISTIC JADES

    // ********************

    // Helper methods
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Tempora.MOD_ID, name), item);
    }

    public static void registerItems() {
        Tempora.LOGGER.info("Registering items.");
    }

}
