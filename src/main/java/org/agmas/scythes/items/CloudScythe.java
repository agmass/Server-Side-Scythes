package org.agmas.scythes.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.agmas.scythes.Scythes;

import java.util.List;
import java.util.Random;

public class CloudScythe extends Scythe {

    public CloudScythe(Settings settings, ToolMaterial material, String modelName, Item item) {
        super(settings,material,modelName,item,0);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof ServerPlayerEntity spe) {
            PlayerAbilities abilities = spe.getAbilities();
            Scythes.canDoubleJump.putIfAbsent(spe.getUuid(), true);
            if (selected) {
                if (abilities.flying) {
                    Scythes.canDoubleJump.put(spe.getUuid(), false);
                    spe.setVelocity(spe.getRotationVector().multiply(1.2).x,spe.getRotationVector().multiply(1.2).y,spe.getRotationVector().multiply(1.2).z);
                    spe.velocityDirty = true;
                    spe.velocityModified = true;

                    stack.damage(1, spe.getRandom(),spe);
                    spe.setFrozenTicks(10);
                    abilities.flying = false;
                    spe.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(abilities));
                }
                if (abilities.allowFlying && !Scythes.canDoubleJump.get(spe.getUuid())) {
                    abilities.allowFlying = false;
                    spe.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(abilities));
                }
                if (spe.isOnGround() && !abilities.allowFlying) {
                    Scythes.canDoubleJump.put(spe.getUuid(), true);
                    abilities.allowFlying = true;
                    spe.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(abilities));
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

}
