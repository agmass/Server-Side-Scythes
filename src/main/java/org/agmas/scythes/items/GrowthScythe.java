package org.agmas.scythes.items;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
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
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.List;

public class GrowthScythe extends Scythe {

    public GrowthScythe(Settings settings, ToolMaterial material, String modelName, Item item) {
        super(settings,material,modelName,item);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity spe) {
            if (!spe.getItemCooldownManager().isCoolingDown(stack)) {
                target.addStatusEffect(new StatusEffectInstance(RegistryEntry.of(ScythesEffects.GROW), 20*10, 0));
                spe.getItemCooldownManager().set(stack, 20*15);
            }
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, PacketContext context) {
        var itemStack1 = super.getPolymerItemStack(itemStack, tooltipType, context);
        if (context.getPlayer() != null) {
            if (itemStack1.getItem().equals(Items.TIPPED_ARROW) && PolymerResourcePackUtils.hasPack(context.getPlayer(), context.getPlayer().getUuid())) {
                itemStack1.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Potions.STRENGTH));
            }
        }
        return itemStack1;
    }

}
