package net.toe.tempora.networking.packet;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.toe.tempora.Tempora;
import net.toe.tempora.client.PlayerData;
import net.toe.tempora.client.skill.Skill;
import net.toe.tempora.client.skill.SkillEnum;
import net.toe.tempora.networking.TemporaPackets;
import net.toe.tempora.util.Persist;

import java.util.List;
import java.util.Objects;

public class ListSkillPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                                        PacketByteBuf buf, PacketSender responseSender) {

        server.execute(() -> {
            Persist playerDataState = Persist.getServerState(Objects.requireNonNull(player.getServer()));
            PlayerData playerData = playerDataState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());
            List<Integer> unlockedSkills = playerData.getUnlockedSkills();

            PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
            data.writeInt(unlockedSkills.size());
            for (int skillID : unlockedSkills) {
                data.writeInt(skillID);
            }

            responseSender.sendPacket(TemporaPackets.LIST_SKILLS_PACKET, data);
        });
    }
}
