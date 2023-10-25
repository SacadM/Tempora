package net.toe.tempora.client.skill;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

@FunctionalInterface
public interface SkillEffect {

    void apply(PlayerEntity player, float scale);

    UUID SPEED_UUID = UUID.randomUUID();
    UUID STRENGTH_UUID = UUID.randomUUID();

    SkillEffect STRENGTH = (player, scale) -> {
        /*EntityAttributeModifier attackDamageBoostModifier = new EntityAttributeModifier(STRENGTH_UUID, "StrengthBoost", scale, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        EntityAttributeInstance attackDamageAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);

        if (attackDamageAttribute != null) {
            attackDamageAttribute.removeModifier(attackDamageBoostModifier);
            attackDamageAttribute.addPersistentModifier(attackDamageBoostModifier);
        }*/
        StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, (int) scale, false, true);
        player.addStatusEffect(statusEffectInstance);
    };

    SkillEffect SPEED = ((player, scale) -> {
        /*EntityAttributeModifier speedBoostModifier = new EntityAttributeModifier(SPEED_UUID, "SpeedBoost", scale, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        EntityAttributeInstance movementSpeedAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

        if (movementSpeedAttribute != null) {
            movementSpeedAttribute.removeModifier(speedBoostModifier);
            movementSpeedAttribute.addPersistentModifier(speedBoostModifier);
        }*/
        StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.SPEED, Integer.MAX_VALUE, (int) scale, false, true);
        player.addStatusEffect(statusEffectInstance);
    });

    SkillEffect HASTE = ((player, scale) -> {
        int amplifier = (int) scale;

        StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.HASTE, Integer.MAX_VALUE, amplifier, true, true);
        player.addStatusEffect(statusEffectInstance);
    });


}
