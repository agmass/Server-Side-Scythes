package org.agmas.scythes.items;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.potion.Potions;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.agmas.scythes.Scythes;
import org.agmas.scythes.util.BorderRoom;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BorderScythe extends Scythe {

    public BorderScythe(Settings settings, ToolMaterial material, String modelName, Item item) {
        super(settings,material,modelName,item,0);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean alreadyInBorder = false;
        for (BorderRoom b : Scythes.borderRooms) {
            if (b.victim.equals(target) || b.victim.equals(attacker) || b.attacker.equals(target) || b.attacker.equals(attacker)) {
                alreadyInBorder = true;
                break;
            }
        }
        if (!alreadyInBorder) {
            BorderRoom room = new BorderRoom(attacker,target,attacker.getBlockPos());
            Scythes.borderRooms.add(room);
            room.activate();
        }
        return super.postHit(stack, target, attacker);
    }


}
