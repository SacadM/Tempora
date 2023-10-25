package net.toe.tempora.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.toe.tempora.Tempora;
import net.toe.tempora.client.skill.Skill;
import net.toe.tempora.client.skill.SkillEnum;

public class SkillUnlockPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {

        int skillID = buf.readInt();
        server.execute(() -> {
            Skill skillToUnlock = SkillEnum.getSkillByID(skillID);
            if (skillToUnlock != null) {
                skillToUnlock.unlock(player, server);
            } else {
                Tempora.LOGGER.warn("Tempora@WARN: Woah there buddy... Peculiar Skill ID you've sent there: " + skillID);
            }
        });
    }

}
