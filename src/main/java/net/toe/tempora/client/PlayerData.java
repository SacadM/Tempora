package net.toe.tempora.client;

import net.toe.tempora.Tempora;
import net.toe.tempora.client.skill.Skill;
import net.toe.tempora.client.skill.SkillEnum;
import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    private List<Integer> unlockedSkills = new ArrayList<>();
    private final List<Integer> PASSIVE_IDS = loadPassiveSkills();
    private List<Skill> passiveSkills = new ArrayList<>();

    private static List<Integer> loadPassiveSkills() {
        List<Integer> passive_ids = new ArrayList<>();
        for (SkillEnum skillEnum : SkillEnum.values()) {
            if (skillEnum.getSkill().isPassive()) {
                passive_ids.add(skillEnum.getSkill().getID());
            }
        }
        return passive_ids;
    }

    public void unlockSkill(int skillID) {
        if (!unlockedSkills.contains(skillID)) {
            unlockedSkills.add(skillID);
            if (PASSIVE_IDS.contains(skillID)) {
                passiveSkills.add(SkillEnum.getSkillByID(skillID));
            }
            //SoulRing.updateSoulRing();
            Tempora.LOGGER.info("Purchased skill: " + skillID);
        } else {
            Tempora.LOGGER.info("You already have this skill: " + skillID + "!");
        }
    }

    @Override
    public String toString() {
        return "PlayerData {" +
                "unlocked Skills = " + unlockedSkills +
                "}";
    }

    public List<Integer> getUnlockedSkills() {
        return unlockedSkills;
    }

    public void setUnlockedSkills(List<Integer> skills) {
        unlockedSkills = skills;
        passiveSkills.clear();
        for (Integer skillID : unlockedSkills) {
            if (PASSIVE_IDS.contains(skillID)) {
                passiveSkills.add(SkillEnum.getSkillByID(skillID));
            }
        }
    }

    public List<Skill> getPassiveSkills() {
        return passiveSkills;
    }
}
