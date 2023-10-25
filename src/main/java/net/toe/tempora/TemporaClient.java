package net.toe.tempora;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.toe.tempora.client.skill.ui.SkillTreeUI;
import net.toe.tempora.keybind.Keybindings;
import net.toe.tempora.networking.TemporaPackets;
import net.toe.tempora.util.Persist;
import java.util.Objects;

public class TemporaClient implements ClientModInitializer {

    private static long previousTick = 0;
    private static SkillTreeUI skillTreeUI;

    @Override
    public void onInitializeClient() {
        Keybindings.registerKeybindings();
        registerConnectionEvents();
        registerTickEvents();
    }

    private void registerTickEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (Keybindings.TOGGLE_SKILL_TREE_UI.wasPressed()) {
                long currentTick = client.world.getTime();
                if (currentTick > (previousTick + 15)) {
                    skillTreeUI.refresh(Objects.requireNonNull(client.getServer()));
                    MinecraftClient.getInstance().setScreen(skillTreeUI);
                    previousTick = currentTick;
                }
            }
        });
    }

    private void registerConnectionEvents() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            Persist.getServerState(Objects.requireNonNull(client.getServer()));
            skillTreeUI = new SkillTreeUI(Text.literal("Skill Tree"), client.player, Objects.requireNonNull(client.getServer()));
            requestSoulRing();
        });
        ClientPlayConnectionEvents.DISCONNECT.register(((handler, client) -> {
            skillTreeUI = null;
        }));
    }

    public static void refreshTree(MinecraftServer server) {
        if (server != null && skillTreeUI != null) {
            skillTreeUI.refresh(server);
        }
    }

    private static void requestSoulRing() {
        ClientPlayNetworking.send(TemporaPackets.GIVE_SOUL_RING, new PacketByteBuf(Unpooled.buffer()));
    }
}

