package org.agmas.scythes.items;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnderScythe extends Scythe {

    public EnderScythe(Settings settings, ToolMaterial material, String modelName, Item item) {
        super(settings,material,modelName,item);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity spe) {
            if (!spe.getItemCooldownManager().isCoolingDown(this)) {
                spe.getItemCooldownManager().set(this, 20*15);
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

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {
        var itemStack1 = super.getPolymerItemStack(itemStack, tooltipType, lookup, player);
        if (player != null) {
            if (itemStack1.getItem().equals(Items.TIPPED_ARROW) && PolymerResourcePackUtils.hasPack(player, player.getUuid())) {
                itemStack1.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Potions.WEAKNESS));
            }
        }
        return itemStack1;
    }

}
