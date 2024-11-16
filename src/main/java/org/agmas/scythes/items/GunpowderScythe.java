package org.agmas.scythes.items;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.potion.Potions;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.agmas.scythes.Scythes;
import org.jetbrains.annotations.Nullable;

public class GunpowderScythe extends Scythe {

    public GunpowderScythe(Settings settings, ToolMaterial material, String modelName, Item item) {
        super(settings,material,modelName,item);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient) {
            attacker.getWorld().createExplosion(null, attacker.getX(), attacker.getY(), attacker.getZ(), 2.3f, World.ExplosionSourceType.MOB);
        }
        return super.postHit(stack, target, attacker);
    }
    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {
        var itemStack1 = super.getPolymerItemStack(itemStack, tooltipType, lookup, player);
        if (player != null) {
            if (itemStack1.getItem().equals(Items.TIPPED_ARROW) && PolymerResourcePackUtils.hasPack(player, player.getUuid())) {
                itemStack1.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Potions.INVISIBILITY));
            }
        }
        return itemStack1;
    }

}
