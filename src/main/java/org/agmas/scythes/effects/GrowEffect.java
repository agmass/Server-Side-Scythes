package org.agmas.scythes.effects;

import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public class GrowEffect extends StatusEffect implements PolymerStatusEffect {
    public GrowEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        ScaleData data = ScaleTypes.HEIGHT.getScaleData(entity);
        data.setTargetScale(1.75f);
        data = ScaleTypes.WIDTH.getScaleData(entity);
        data.setTargetScale(1.75f);
        super.onApplied(entity, attributes, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        ScaleData data = ScaleTypes.HEIGHT.getScaleData(entity);
        data.setTargetScale(1f);
        data = ScaleTypes.WIDTH.getScaleData(entity);
        data.setTargetScale(1f);
        super.onRemoved(entity, attributes, amplifier);
    }
}
