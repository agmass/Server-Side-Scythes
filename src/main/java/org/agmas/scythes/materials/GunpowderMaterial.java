package org.agmas.scythes.materials;

import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

public class GunpowderMaterial implements ToolMaterial {
    public static final GunpowderMaterial INSTANCE = new GunpowderMaterial();
    @Override
    public int getDurability() {
        return 1;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 7.0f;
    }

    @Override
    public float getAttackDamage() {
        return 0.5F;
    }

    @Override
    public int getMiningLevel() {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 10;
    }


    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.GUNPOWDER.asItem());
    }
}
