package org.agmas.scythes.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.WorldBorderCenterChangedS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderSizeChangedS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;

public class BorderRoom {
    public LivingEntity attacker;
    public LivingEntity victim;
    public BlockPos pos;
    public World world;
    public boolean remove = false;

    public BorderRoom(LivingEntity attacker, LivingEntity victim, BlockPos pos) {
        this.attacker = attacker;
        this.victim = victim;
        this.pos = pos;
        this.world = victim.getWorld();
    }

    public void tick() {
        //make sure that mobs that aren't players can't leave the area!
        if (victim.getPos().multiply(1,0,1).distanceTo(pos.toCenterPos().multiply(1,0,1)) >= 12) {
            victim.setPosition(victim.prevX,victim.prevY,victim.prevZ);
        }
        if (attacker.getPos().multiply(1,0,1).distanceTo(pos.toCenterPos().multiply(1,0,1)) >= 12) {
            attacker.setPosition(attacker.prevX,attacker.prevY,attacker.prevZ);
        }
        if (attacker.getWorld() != world || victim.getWorld() != world) {
            deactivate();
            remove = true;
        }
        if (!attacker.isAlive() || !victim.isAlive()) {
            deactivate();
            remove = true;
        }
    }

    public void deactivate() {
        if (attacker instanceof ServerPlayerEntity spe) {
            WorldBorder worldBorder = spe.getWorld().getWorldBorder();
            spe.networkHandler.sendPacket(new WorldBorderCenterChangedS2CPacket(worldBorder));
            spe.networkHandler.sendPacket(new WorldBorderSizeChangedS2CPacket(worldBorder));
        }
        if (victim instanceof ServerPlayerEntity spe) {
            WorldBorder worldBorder = spe.getWorld().getWorldBorder();
            spe.networkHandler.sendPacket(new WorldBorderCenterChangedS2CPacket(worldBorder));
            spe.networkHandler.sendPacket(new WorldBorderSizeChangedS2CPacket(worldBorder));
        }
    }

    public void activate() {
        if (attacker instanceof ServerPlayerEntity spe) {
            WorldBorder worldBorder = new WorldBorder();
            worldBorder.setCenter(pos.getX(), pos.getZ());
            worldBorder.setSize(24);
            spe.networkHandler.sendPacket(new WorldBorderCenterChangedS2CPacket(worldBorder));
            spe.networkHandler.sendPacket(new WorldBorderSizeChangedS2CPacket(worldBorder));
        }
        if (victim instanceof ServerPlayerEntity spe) {
            WorldBorder worldBorder = new WorldBorder();
            worldBorder.setCenter(pos.getX(), pos.getZ());
            worldBorder.setSize(24);
            spe.networkHandler.sendPacket(new WorldBorderCenterChangedS2CPacket(worldBorder));
            spe.networkHandler.sendPacket(new WorldBorderSizeChangedS2CPacket(worldBorder));
        }
    }
}
