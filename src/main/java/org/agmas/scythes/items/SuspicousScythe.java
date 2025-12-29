package org.agmas.scythes.items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class SuspicousScythe extends Scythe {

    public SuspicousScythe(Settings settings, ToolMaterial material) {
        super(settings,material);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity spe) {
            if (!spe.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                spe.getItemCooldownManager().set(stack.getItem(), 20*10);
                spe.getWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENTITY_MOOSHROOM_SUSPICIOUS_MILK, SoundCategory.PLAYERS, 1f, 1f);
                spe.getActiveStatusEffects().forEach((s,si)->{
                    target.addStatusEffect(si);
                });
                spe.clearStatusEffects();
            }
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of("Transfers all the attacker's effects to the victim."));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
