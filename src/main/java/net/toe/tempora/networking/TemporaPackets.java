package net.toe.tempora.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.toe.tempora.Tempora;
import net.toe.tempora.networking.packet.GiveSoulRingPacket;
import net.toe.tempora.networking.packet.ListSkillPacket;
import net.toe.tempora.networking.packet.SkillUnlockPacket;

public class TemporaPackets {

    public static final Identifier SKILL_UNLOCK_PACKET = new Identifier(Tempora.MOD_ID, "unlock_skill");
    public static final Identifier LIST_SKILLS_PACKET = new Identifier(Tempora.MOD_ID, "list_skills");
    public static final Identifier GIVE_SOUL_RING = new Identifier(Tempora.MOD_ID, "give_soul_ring");


    public static void registerC2SPacket() {
        ServerPlayNetworking.registerGlobalReceiver(SKILL_UNLOCK_PACKET, SkillUnlockPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(LIST_SKILLS_PACKET, ListSkillPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(GIVE_SOUL_RING, GiveSoulRingPacket::receive);
    }
}
