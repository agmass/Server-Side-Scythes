package org.agmas.scythes.items;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.Random;

public class SuspicousScythe extends Scythe {

    public SuspicousScythe(Settings settings, ToolMaterial material, String modelName, Item item) {
        super(settings,material,modelName,item);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity spe) {
            if (!spe.getItemCooldownManager().isCoolingDown(stack)) {
                spe.getItemCooldownManager().set(stack, 20*10);
                target.addStatusEffect(new StatusEffectInstance[]{
                        new StatusEffectInstance(StatusEffects.DARKNESS, 20*5, 0),
                        new StatusEffectInstance(StatusEffects.NAUSEA, 20*5, 0),
                        new StatusEffectInstance(StatusEffects.SLOWNESS, 20*5, 0),
                        new StatusEffectInstance(StatusEffects.POISON, 20*5, 0),
                        new StatusEffectInstance(StatusEffects.WIND_CHARGED, 20*5, 0),
                        new StatusEffectInstance(StatusEffects.GLOWING, 20*5, 0),
                }[new Random().nextInt(0,7)]);
            }
        }
        return super.postHit(stack, target, attacker);
    }


    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, PacketContext context) {
        var itemStack1 = super.getPolymerItemStack(itemStack, tooltipType, context);
        if (context.getPlayer() != null) {
            if (itemStack1.getItem().equals(Items.TIPPED_ARROW) && PolymerResourcePackUtils.hasPack(context.getPlayer(), context.getPlayer().getUuid())) {
                itemStack1.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Potions.POISON));
            }
        }
        return itemStack1;
    }

}
