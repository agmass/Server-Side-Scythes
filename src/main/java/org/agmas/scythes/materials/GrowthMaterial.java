package org.agmas.scythes.materials;

import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

public class GrowthMaterial implements ToolMaterial {
    public static final GrowthMaterial INSTANCE = new GrowthMaterial();
    @Override
    public int getDurability() {
        return 8;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 7.0f;
    }

    @Override
    public float getAttackDamage() {
        return 1.5F;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return null;
    }

    @Override
    public int getEnchantability() {
        return 10;
    }


    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.BLAZE_POWDER.asItem());
    }
}
