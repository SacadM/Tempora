package net.toe.tempora.client.skill;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.toe.tempora.Tempora;
import net.toe.tempora.TemporaClient;
import net.toe.tempora.client.PlayerData;
import net.toe.tempora.client.skill.ui.SkillTreeUI;
import net.toe.tempora.util.Persist;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Skill {
    private final String name;
    private final SkillEffect effect;
    private final float scale;
    private final boolean isPassive;
    private final Identifier icon;
    private final int xpCost;
    private Skill predecessor;
    private final int ID;

    public Skill(String name, SkillEffect effect, float scale, boolean isPassive, Identifier icon, int xp, Skill predecessor, int ID) {
        this.name = name;
        this.effect = effect;
        this.scale = scale;
        this.isPassive = isPassive;
        this.icon = icon;
        this.xpCost = xp;
        this.predecessor = predecessor;
        this.ID = ID;
    }

    public void activate(PlayerEntity player) {
        effect.apply(player, scale);
    }

    public void unlock(PlayerEntity player, MinecraftServer server) {
        STATUS canUnlock = canUnlock(player, server);
        if (canUnlock == STATUS.PURCHASABLE) {
            Persist playerDataState = Persist.getServerState(Objects.requireNonNull(player.getServer()));
            PlayerData playerData = playerDataState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());
            playerData.unlockSkill(ID);

            player.experienceLevel -= xpCost;

            Persist.markStateAsDirty(player.getServer());
            server.save(true, true, true);

            TemporaClient.refreshTree(server);

            activate(player);
        } else {
            Tempora.LOGGER.info("Player UUID: " + player.getName() + " cannot unlock: " + this.name + " for reason: " + canUnlock.toString());
        }
    }

    public boolean isPassive() {
        return isPassive;
    }

    public Identifier getIcon() {
        return icon;
    }

    public int getID() {return ID;}

    public boolean hasPredecessor(PlayerEntity player, MinecraftServer server) {
        Persist serverState = Persist.getServerState(server);
        if (serverState.players.containsKey(player.getUuid())) {
            List<Integer> skills = serverState.players.get(player.getUuid()).getUnlockedSkills();
            return skills.contains(this.predecessor.getID());
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public STATUS canUnlock(PlayerEntity player, MinecraftServer server) {
        Persist serverState = Persist.getServerState(server);

        // Check if the player data is available
        PlayerData playerData = serverState.players.get(player.getUuid());
        if (playerData == null) {
            if (this.predecessor == null && player.experienceLevel >= xpCost) {
                return STATUS.PURCHASABLE;
            } else if (this.predecessor != null) {
                return STATUS.MISSING_PREDECESSOR;
            }
            return STATUS.BROKE;
        }

        // Already purchased check
        if (playerData.getUnlockedSkills().contains(ID)) {
            return STATUS.PURCHASED;
        }

        // Predecessor check
        if (this.predecessor != null && !hasPredecessor(player, server)) {
            return STATUS.MISSING_PREDECESSOR;
        }

        // XP cost check
        return player.experienceLevel >= this.xpCost ? STATUS.PURCHASABLE : STATUS.BROKE;
    }


    public SkillEffect getEffect() {
        return this.effect;
    }

    public enum STATUS {
        PURCHASED,
        PURCHASABLE,
        BROKE,
        MISSING_PREDECESSOR
    }

    @Override
    public String toString() {
        return this.name;
    }
}


