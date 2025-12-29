package org.agmas.scythes.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.mokus.mokuslib.components.MokusLibItemComponents;
import org.agmas.scythes.Scythes;

import java.awt.*;
import java.util.List;

public class CloudScythe extends Scythe {

    public CloudScythe(Settings settings, ToolMaterial material) {
        super(settings,material);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof ServerPlayerEntity spe) {
            PlayerAbilities abilities = spe.getAbilities();
            if (spe.getInventory().getMainHandStack().isOf(this)) {
                if (spe.isOnGround() && !abilities.allowFlying && (!spe.isCreative() && !spe.isSpectator())) {
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
        int variant = stack.getOrDefault(MokusLibItemComponents.MOKUS_SKIN_DATA,0);

        String variantName;
        int color = switch (variant) {
            case 1 -> {
                variantName = "Calcite";
                yield Color.WHITE.getRGB();
            }
            default -> {
                variantName = "Breeze";
                yield new Color(191, 253, 255).getRGB();
            }
        };

        tooltip.add(Text.literal("Skin: ").formatted(Formatting.GRAY)
                .append(Text.literal(variantName).setStyle(Style.EMPTY.withColor(color))));
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public int getVariantCount() {
        return 1;
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType != ClickType.RIGHT) {
            return false;
        }

        int skin_current = stack.getOrDefault(MokusLibItemComponents.MOKUS_SKIN_DATA,0);
        stack.set(MokusLibItemComponents.MOKUS_SKIN_DATA, (skin_current + 1) % 2);

        slot.markDirty();
        return true;
    }
}
