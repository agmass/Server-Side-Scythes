package org.agmas.scythes.items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtInt;
import net.minecraft.potion.Potions;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.agmas.scythes.Scythes;

import java.util.List;
import java.util.Random;

public class IceScythe extends Scythe {

    public IceScythe(Settings settings, ToolMaterial material) {
        super(settings,material);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        return damageSource.getAttacker().getFrozenTicks() > 0 ? baseAttackDamage : super.getBonusAttackDamage(target,baseAttackDamage,damageSource);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity spe) {
            if (!spe.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                spe.getItemCooldownManager().set(stack.getItem(), 20 * 3);
                target.setFrozenTicks(300);
                target.getWorld().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1f, 1f);
            }
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of("Deals 2x damage when frozen."));
        tooltip.add(Text.of("Freezes enemies."));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
