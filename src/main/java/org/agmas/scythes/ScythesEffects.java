package org.agmas.scythes;

import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.agmas.scythes.effects.GrowEffect;

public class ScythesEffects implements PolymerStatusEffect
{

    public static final StatusEffect GROW;

    static {
        GROW = Registry.register(Registries.STATUS_EFFECT, Identifier.of("scythes", "grow"), (new GrowEffect(StatusEffectCategory.HARMFUL, Colors.RED).addAttributeModifier(EntityAttributes.SCALE, Identifier.ofVanilla("effect.grow"), 0.75, EntityAttributeModifier.Operation.ADD_VALUE)));
    }

    public static void init() {}
}
