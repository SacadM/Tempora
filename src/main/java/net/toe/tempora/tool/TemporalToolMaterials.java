package net.toe.tempora.tool;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;
import net.toe.tempora.item.TemporaItems;

import java.util.function.Supplier;

public enum TemporalToolMaterials implements ToolMaterial {
        CHRONOSTONE(2, 1000, 6.0F, 2.5F, 25, () -> {return Ingredient.ofItems(TemporaItems.CHRONOSTONE);});

        private final int miningLevel;
        private final int itemDurability;
        private final float miningSpeed;
        private final float attackDamage;
        private final int enchantability;
        private final Lazy<Ingredient> repairIngredient;

        TemporalToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
            this.miningLevel = miningLevel;
            this.itemDurability = itemDurability;
            this.miningSpeed = miningSpeed;
            this.attackDamage = attackDamage;
            this.enchantability = enchantability;
            this.repairIngredient = new Lazy(repairIngredient);
        }

        public int getDurability() {
            return this.itemDurability;
        }

        public float getMiningSpeedMultiplier() {
            return this.miningSpeed;
        }

        public float getAttackDamage() {
            return this.attackDamage;
        }

        public int getMiningLevel() {
            return this.miningLevel;
        }

        public int getEnchantability() {
            return this.enchantability;
        }

        public Ingredient getRepairIngredient() {
            return (Ingredient)this.repairIngredient.get();
        }
}
