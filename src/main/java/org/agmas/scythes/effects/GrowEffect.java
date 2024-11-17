package org.agmas.scythes.effects;

import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class GrowEffect extends StatusEffect implements PolymerStatusEffect {
    public GrowEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
}
