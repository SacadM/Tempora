package net.toe.tempora.client.skill;

import net.minecraft.util.Identifier;

public enum SkillEnum {
    STRENGTH_1("ABILITY", new Skill("Strength I", SkillEffect.STRENGTH, 1.1f, true, new Identifier("minecraft", "textures/item/stone_sword.png"), 8, null, 0)),
    STRENGTH_2("ABILITY", new Skill("Strength II", SkillEffect.STRENGTH, 1.2f, true, new Identifier("minecraft", "textures/item/iron_sword.png"), 10, STRENGTH_1.getSkill(), 1)),
    HASTE_1("ABILITY", new Skill("Haste I", SkillEffect.HASTE, 10f, true, new Identifier("minecraft", "textures/item/stone_pickaxe.png"), 8, null, 2)),
    SPEED_1("ABILITY", new Skill("Speed I", SkillEffect.SPEED, 1.1f, true, new Identifier("minecraft", "textures/item/feather.png"), 8, null, 3));



    private final String treeType;
    private final Skill skill;

    SkillEnum(String treeType, Skill skill) {
        this.treeType = treeType;
        this.skill = skill;
    }

    public static Skill getSkillByID(int ID) {
        for (SkillEnum skillEnum : SkillEnum.values()) {
            if (skillEnum.getSkill().getID() == ID) {
                return skillEnum.getSkill();
            }
        }
        return null;
    }

    public static SkillEnum getSkillEnumByID(int ID) {
        for (SkillEnum skillEnum : SkillEnum.values()) {
            if (skillEnum.getSkill().getID() == ID) {
                return skillEnum;
            }
        }
        return null;
    }

    public Skill getSkill() {
        return this.skill;
    }

    public String getTreeType() {
        return this.treeType;
    }
}

