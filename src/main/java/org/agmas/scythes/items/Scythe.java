package org.agmas.scythes.items;

import eu.pb4.polymer.common.api.PolymerCommonUtils;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerUtils;
import eu.pb4.polymer.networking.api.server.PolymerServerNetworking;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
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
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.agmas.scythes.Scythes;
import org.agmas.scythes.materials.CloudMaterial;
import org.jetbrains.annotations.Nullable;

public class Scythe extends ToolItem implements PolymerItem {

    public ToolMaterial toolMaterial;
    PolymerModelData modelData;
    Item item;

    public Scythe(Settings settings, ToolMaterial material, String modelName, Item item) {
        super(material, settings);
        modelData = PolymerResourcePackUtils.requestModel(item, Identifier.of("scythes", "item/"+modelName));
        this.item = item;
        toolMaterial = material;
    }
    public Scythe(Settings settings, ToolMaterial material, String modelName, Item item, String modelNamespace) {
        super(material, settings);
        modelData = PolymerResourcePackUtils.requestModel(item, Identifier.of(modelNamespace, "item/"+modelName));
        this.item = item;
        toolMaterial = material;
    }

    @Override
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        if (damageSource.getAttacker() instanceof ServerPlayerEntity spe) {
            if (PolymerCommonUtils.isBedrockPlayer(spe)) {
                spe.sendMessage(Text.of("You can't use the reach modifier on bedrock, so you dealt +4 extra attack damage."), true);
                return super.getBonusAttackDamage(target, baseAttackDamage+4, damageSource);
            }
        }
        return super.getBonusAttackDamage(target, baseAttackDamage, damageSource);
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder().add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(Identifier.of("scythes", "reach"), 2.6, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)(baseAttackDamage + material.getAttackDamage()), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, (double)attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (toolMaterial.equals(CloudMaterial.INSTANCE)) {
            if (entity instanceof ServerPlayerEntity spe) {
                PlayerAbilities abilities = spe.getAbilities();
                Scythes.canDoubleJump.putIfAbsent(spe.getUuid(), true);
                if (selected) {
                    if (abilities.flying) {
                        Scythes.canDoubleJump.put(spe.getUuid(), false);
                        spe.setVelocity(spe.getRotationVector().multiply(1.2).x,spe.getRotationVector().multiply(1.2).y,spe.getRotationVector().multiply(1.2).z);
                        spe.velocityDirty = true;
                        spe.velocityModified = true;
                        stack.damage(1, spe, EquipmentSlot.MAINHAND);
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
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {
        var itemStack1 = PolymerItem.super.getPolymerItemStack(itemStack, tooltipType, lookup, player);
        itemStack1.set(DataComponentTypes.CUSTOM_MODEL_DATA, modelData.asComponent());
        return itemStack1;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        return super.postHit(stack, target, attacker);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        if (player == null) return item;
        if (PolymerServerNetworking.getMetadata(player.networkHandler, Scythes.REGISTER_PACKET, NbtInt.TYPE) == NbtInt.of(1)) {
            return this;
        } else {
            return item;
        }
    }
}
