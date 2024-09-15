package org.agmas.scythes.items;

import eu.pb4.polymer.common.api.PolymerCommonUtils;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerUtils;
import net.minecraft.block.Block;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.item.*;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.agmas.scythes.Scythes;
import org.agmas.scythes.materials.CloudMaterial;
import org.jetbrains.annotations.Nullable;

public class Scythe extends ToolItem implements PolymerItem {

    ToolMaterial toolMaterial;

    public Scythe(Settings settings, ToolMaterial material) {
        super(material, settings);
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
                        spe.setVelocity(spe.getRotationVector().multiply(1.3).x,spe.getRotationVector().multiply(0.9).y,spe.getRotationVector().multiply(1.3).z);
                        spe.velocityDirty = true;
                        spe.velocityModified = true;
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
                } else {
                    if (spe.getAbilities().allowFlying && spe.interactionManager.getGameMode().isSurvivalLike()) {
                        boolean skip = false;
                        if (spe.getInventory().getMainHandStack().getItem() instanceof Scythe s) {
                            if (s.toolMaterial.equals(CloudMaterial.INSTANCE)) {
                                skip = true;
                            }
                        }
                        if (!skip) {
                            spe.interactionManager.getGameMode().setAbilities(abilities);
                            spe.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(abilities));
                        }
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        try {
            if (itemStack.getItem() instanceof Scythe s) {
                if (s.toolMaterial.equals(CloudMaterial.INSTANCE)) {
                    return Items.IRON_HOE.asItem();
                }

                /*

                "oh why don't you use a switch statement here"
                IDK I TRIED TO AND IT FOR SOME REASOn CAUSED TEN BILLION ERRORS IMS ORRYY

                 */
                if (s.toolMaterial == ToolMaterials.IRON) {
                    return Items.IRON_HOE.asItem();
                }
                if (s.toolMaterial == ToolMaterials.DIAMOND) {
                    return Items.DIAMOND_HOE.asItem();
                }
                if (s.toolMaterial == ToolMaterials.NETHERITE) {
                    return Items.NETHERITE_HOE.asItem();
                }
                if (s.toolMaterial == ToolMaterials.STONE) {
                    return Items.STONE_HOE.asItem();
                }
                if (s.toolMaterial == ToolMaterials.WOOD) {
                    return Items.WOODEN_HOE.asItem();
                }
                if (s.toolMaterial == ToolMaterials.GOLD) {
                    return Items.GOLDEN_HOE.asItem();
                }
                return Items.BEDROCK.asItem();
            } else {
                return Items.WOODEN_SWORD.asItem();
            }
        } catch (Exception e) {
            return Items.DEEPSLATE.asItem();
        }
    }
}
