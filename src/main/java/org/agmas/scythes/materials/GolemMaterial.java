package org.agmas.scythes.materials;

import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

public class GolemMaterial implements ToolMaterial {
    public static final GolemMaterial INSTANCE = new GolemMaterial();
    @Override
    public int getDurability() {
        return 550;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 8.0f;
    }

    @Override
    public float getAttackDamage() {
        return 4F;
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
        return Ingredient.ofItems(Items.IRON_BLOCK.asItem());
    }
}
