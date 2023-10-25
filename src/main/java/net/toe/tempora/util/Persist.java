package net.toe.tempora.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.toe.tempora.Tempora;
import net.toe.tempora.client.PlayerData;
import net.toe.tempora.client.skill.Skill;

import java.util.*;

public class Persist extends PersistentState {

    public HashMap<UUID, PlayerData> players = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound playersNbt = new NbtCompound();
        players.forEach(((uuid, playerData) -> {
            NbtCompound player = new NbtCompound();
            player.putIntArray("unlockedSkills", playerData.getUnlockedSkills());
            playersNbt.put(String.valueOf(uuid), player);
        }));
        nbt.put("players", playersNbt);

        return nbt;
    }


    public static Persist createFromNbt(NbtCompound tag) {
        Persist state = new Persist();

        NbtCompound playersNbt = tag.getCompound("players");
        playersNbt.getKeys().forEach(key -> {
            PlayerData playerData = new PlayerData();

            // Retrieve the skill IDs from the player's NBT and add them to the PlayerData object
            int[] skillIDArray = playersNbt.getCompound(key).getIntArray("unlockedSkills");
            List<Integer> skillsToPass = new ArrayList<>();
            for (int skillID : skillIDArray) {
                skillsToPass.add(skillID);
            }
            playerData.setUnlockedSkills(skillsToPass);

            UUID uuid = UUID.fromString(key);
            state.players.put(uuid, playerData);
        });

        return state;
    }


    public static Persist getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        return persistentStateManager.getOrCreate(Persist::createFromNbt, Persist::new, Tempora.MOD_ID);
    }

    public static void markStateAsDirty(MinecraftServer server) {
        Tempora.LOGGER.info("Trying to save...");
        getServerState(server).markDirty();
    }
}
