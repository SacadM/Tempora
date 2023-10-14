package net.toe.tempora.tool;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.toe.tempora.Tempora;
import net.toe.tempora.item.TemporaItemGroup;
import net.toe.tempora.item.custom.TemporaAxe;
import net.toe.tempora.item.custom.TemporaHoe;
import net.toe.tempora.item.custom.TemporaPickaxe;

public class TemporaTools {

    // Prehistoric
    public static final Item CHRONOSTONE_PICKAXE = registerTool("chronostone_pickaxe",
            new TemporaPickaxe(TemporalToolMaterials.CHRONOSTONE, 1, 0f, new FabricItemSettings().group(TemporaItemGroup.TEMPORA)));
    public static final Item CHRONOSTONE_SWORD = registerTool("chronostone_sword",
            new SwordItem(TemporalToolMaterials.CHRONOSTONE, 1, 0f, new FabricItemSettings().group(TemporaItemGroup.TEMPORA)));
    public static final Item CHRONOSTONE_AXE = registerTool("chronostone_axe",
            new TemporaAxe(TemporalToolMaterials.CHRONOSTONE, 1, 0f, new FabricItemSettings().group(TemporaItemGroup.TEMPORA)));
    public static final Item CHRONOSTONE_SHOVEL = registerTool("chronostone_shovel",
            new ShovelItem(TemporalToolMaterials.CHRONOSTONE, 1, 0f, new FabricItemSettings().group(TemporaItemGroup.TEMPORA)));
    public static final Item CHRONOSTONE_HOE = registerTool("chronostone_hoe",
            new TemporaHoe(TemporalToolMaterials.CHRONOSTONE, 1, 0f, new FabricItemSettings().group(TemporaItemGroup.TEMPORA)));


    private static Item registerTool(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Tempora.MOD_ID, name), item);
    }

    public static void registerTemporaTools() {
        Tempora.LOGGER.info("Registering tools for " + Tempora.MOD_ID);
    }
}
