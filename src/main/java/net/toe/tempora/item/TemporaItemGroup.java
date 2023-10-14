package net.toe.tempora.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.toe.tempora.Tempora;

public class TemporaItemGroup {
    public static final ItemGroup TEMPORA = FabricItemGroupBuilder.build(
            new Identifier(Tempora.MOD_ID, "tempora"), () -> new ItemStack(TemporaItems.TEMPORAL_CRYSTAL));
}
