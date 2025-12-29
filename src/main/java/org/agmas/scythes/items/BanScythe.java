package org.agmas.scythes.items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.potion.Potions;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class BanScythe extends Scythe {

    public BanScythe(Settings settings, ToolMaterial material) {
        super(settings, material);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                if (target instanceof ServerPlayerEntity vic) {
                    vic.damage(vic.getDamageSources().mobAttack(attacker), 99999);
                    vic.getServer().getPlayerManager().getUserBanList().add(new BannedPlayerEntry(vic.getGameProfile()));
                    vic.networkHandler.sendPacket(new DisconnectS2CPacket(Text.translatable("multiplayer.disconnect.banned")));
                }
        return super.postHit(stack, target, attacker);
    }

}
