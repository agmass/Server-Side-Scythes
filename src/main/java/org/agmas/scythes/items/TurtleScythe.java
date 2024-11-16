package org.agmas.scythes.items;

import eu.pb4.polymer.common.api.PolymerCommonUtils;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.networking.api.server.PolymerServerNetworking;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtInt;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.potion.Potions;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.agmas.scythes.Scythes;
import org.agmas.scythes.materials.CloudMaterial;
import org.agmas.scythes.materials.TurtleMaterial;
import org.jetbrains.annotations.Nullable;

public class TurtleScythe extends Scythe {

    public TurtleScythe(Settings settings, ToolMaterial material, String modelName, Item item) {
        super(settings,material,modelName,item);
    }

    @Override
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        if (target.isInsideWaterOrBubbleColumn()) {
            Entity player = damageSource.getSource();
            if (player instanceof ServerPlayerEntity spe) {
                return super.getBonusAttackDamage(target, baseAttackDamage, damageSource) + 6 + (spe.isUsingRiptide() ? 6 : 0);
            }
        }
        return super.getBonusAttackDamage(target, baseAttackDamage, damageSource);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.isTouchingWaterOrRain()) {
            attacker.setVelocity(attacker.getRotationVector().multiply(1.6).x, attacker.getRotationVector().multiply(1.6).y, attacker.getRotationVector().multiply(1.6).z);
            attacker.velocityDirty = true;
            if (attacker instanceof ServerPlayerEntity spe) {
                spe.useRiptide(80, 5.0F, stack);
            }
            attacker.setAir(attacker.getMaxAir());
            attacker.velocityModified = true;
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {
        var itemStack1 = super.getPolymerItemStack(itemStack, tooltipType, lookup, player);
        if (player != null) {
            if (itemStack1.getItem().equals(Items.TIPPED_ARROW) && PolymerResourcePackUtils.hasPack(player, player.getUuid())) {
                itemStack1.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Potions.LUCK));
            }
        }
        return itemStack1;
    }

}
