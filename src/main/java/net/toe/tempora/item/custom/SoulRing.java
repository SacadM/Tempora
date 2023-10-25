package net.toe.tempora.item.custom;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.toe.tempora.client.skill.Skill;
import java.util.List;

public class SoulRing extends TrinketItem {

    private static List<Skill> passiveSkills = null;

    public SoulRing(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return false;
    }

    /*public static void updateSoulRing() {
        MinecraftClient client = MinecraftClient.getInstance();
        Persist state = Persist.getServerState(Objects.requireNonNull(client.getServer()));
        passiveSkills = state.players.get(client.player.getUuid()).getPassiveSkills();
    }*/

    /*@Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {

    }*/
}
