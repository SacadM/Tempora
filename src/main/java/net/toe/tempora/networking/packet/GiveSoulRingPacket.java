package net.toe.tempora.networking.packet;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.toe.tempora.item.TemporaItems;
import java.util.Map;
import java.util.Optional;

public class GiveSoulRingPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        Optional<TrinketComponent> optionalTrinketComponent = TrinketsApi.getTrinketComponent(player);
        if (optionalTrinketComponent.isPresent()) {
            TrinketComponent trinketComponent = optionalTrinketComponent.get();

            Map<String, Map<String, TrinketInventory>> inventories = trinketComponent.getInventory();
            Map<String, TrinketInventory> handInventory = inventories.get("hand");

            if (handInventory != null && handInventory.containsKey("ring")) {
                TrinketInventory ringInventory = handInventory.get("ring");

                ItemStack soulRing = new ItemStack(TemporaItems.SOUL_RING);
                soulRing.addEnchantment(Enchantments.BINDING_CURSE, 1);

                ringInventory.setStack(0, soulRing);
            }
        }
    }
}
