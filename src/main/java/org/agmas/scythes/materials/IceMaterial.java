package org.agmas.scythes.materials;

import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

public class IceMaterial implements ToolMaterial {
    public static final IceMaterial INSTANCE = new IceMaterial();
    @Override
    public int getDurability() {
        return 330;
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
    public int getMiningLevel() {
        return 0;
    }
    @Override
    public int getEnchantability() {
        return 10;
    }


    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.ICE.asItem());
    }
}
