package org.agmas.scythes.items;

import eu.pb4.polymer.common.api.PolymerCommonUtils;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerClientDecoded;
import eu.pb4.polymer.core.api.utils.PolymerKeepModel;
import eu.pb4.polymer.networking.api.server.PolymerServerNetworking;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtInt;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.agmas.scythes.Scythes;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.List;

public class Scythe extends Item implements PolymerItem, PolymerKeepModel, PolymerClientDecoded {

    public ToolMaterial toolMaterial;
    Item item;
    String modelName;

    public Scythe(Settings settings, ToolMaterial material, String modelName, Item item) {
        super(settings.registryKey(keyOf(modelName)).useItemPrefixedTranslationKey().component(DataComponentTypes.TOOL, new ToolComponent(List.of(), 15.0F, 0)).maxDamage(material.durability()));

        this.item = item;
        this.modelName = modelName;
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
        return AttributeModifiersComponent.builder().add(EntityAttributes.ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(Identifier.of("scythes", "reach"), 2.6, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)(baseAttackDamage + material.attackDamageBonus()), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, (double)attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        return super.postHit(stack, target, attacker);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext context) {
        if (context.getPlayer() == null) return item;
        if (PolymerServerNetworking.getMetadata(context.getPlayer().networkHandler, Scythes.REGISTER_PACKET, NbtInt.TYPE) != null) {
            return this;
        } else {
            return item;
        }
    }

    @Override
    public @Nullable Identifier getPolymerItemModel(ItemStack stack, PacketContext context) {
        if (context.getPlayer() == null) return Identifier.of("minecraft", item.toString().split(":")[1]);
        if (PolymerServerNetworking.getMetadata(context.getPlayer().networkHandler, Scythes.REGISTER_PACKET, NbtInt.TYPE) != null) {
            return Identifier.of("scythes", modelName);
        } else {
            return Identifier.of("minecraft", item.toString().split(":")[1]);
        }
    }

    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of("scythes", id));
    }

}
