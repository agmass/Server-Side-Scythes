package org.agmas.scythes.items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.potion.Potions;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.List;

public class EnderScythe extends Scythe {

    public EnderScythe(Settings settings, ToolMaterial material) {
        super(settings,material);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity spe) {
            if (!spe.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                spe.getItemCooldownManager().set(stack.getItem(), 20*15);
                spe.getWorld().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK, SoundCategory.PLAYERS, 1f, 1f);
                target.getActiveStatusEffects().forEach((s,si)->{
                    attacker.addStatusEffect(si);
                });
                target.clearStatusEffects();
            }
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of("Steals status effects from the victim."));
        super.appendTooltip(stack, context, tooltip, type);
    }

}
