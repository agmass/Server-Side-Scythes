package org.agmas.scythes;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.agmas.scythes.effects.GrowEffect;

public class ScythesEffects
{

    public static final RegistryEntry<StatusEffect> GROW;

    static {
        GROW = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of("scythes", "grow"), (new GrowEffect(StatusEffectCategory.HARMFUL, Colors.RED).addAttributeModifier(EntityAttributes.GENERIC_SCALE, Identifier.ofVanilla("effect.grow"), 0.75, EntityAttributeModifier.Operation.ADD_VALUE)));
    }

    public static void init() {}
}
