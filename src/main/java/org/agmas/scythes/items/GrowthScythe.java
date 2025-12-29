package org.agmas.scythes.items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.potion.Potions;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.agmas.scythes.ScythesEffects;
import java.util.List;

public class GrowthScythe extends Scythe {

    public GrowthScythe(Settings settings, ToolMaterial material) {
        super(settings,material);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity spe) {
            if (!spe.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                target.addStatusEffect(new StatusEffectInstance(ScythesEffects.GROW, 20*10, 0));
                spe.getItemCooldownManager().set(stack.getItem(), 20*30);
            }
        }
        return super.postHit(stack, target, attacker);
    }
}
