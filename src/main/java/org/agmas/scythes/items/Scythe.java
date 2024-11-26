package org.agmas.scythes.items;

import eu.pb4.polymer.common.api.PolymerCommonUtils;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerClientDecoded;
import eu.pb4.polymer.core.api.utils.PolymerKeepModel;
import eu.pb4.polymer.core.api.utils.PolymerUtils;
import eu.pb4.polymer.networking.api.server.PolymerServerNetworking;
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

public class Scythe extends ToolItem implements PolymerItem, PolymerKeepModel, PolymerClientDecoded {

    public ToolMaterial toolMaterial;
    Item item;

    public Scythe(Settings settings, ToolMaterial material, String modelName, Item item) {
        super(material, settings);
        this.item = item;
        toolMaterial = material;
    }
    public Scythe(Settings settings, ToolMaterial material, String modelName, Item item, String modelNamespace) {
        super(material, settings);
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
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        return super.postHit(stack, target, attacker);
    }



    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        if (player == null) return item;
        if (PolymerServerNetworking.getMetadata(player.networkHandler, Scythes.REGISTER_PACKET, NbtInt.TYPE) != null) {
            return this;
        } else {
            return item;
        }
    }
}
