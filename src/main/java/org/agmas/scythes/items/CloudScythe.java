package org.agmas.scythes.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.agmas.scythes.Scythes;

import java.util.List;

public class CloudScythe extends Scythe {

    public CloudScythe(Settings settings, ToolMaterial material, String modelName, Item item) {
        super(settings,material,modelName,item);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof ServerPlayerEntity spe) {
            PlayerAbilities abilities = spe.getAbilities();
            if (spe.getInventory().getMainHandStack().isOf(this)) {
                if (spe.isOnGround() && !abilities.allowFlying) {
                    abilities.allowFlying = true;
                    Scythes.flyingFromScythe.add(spe);
                    spe.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(abilities));
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }


    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of("Allows you to double jump when held"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
