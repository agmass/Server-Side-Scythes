package org.agmas.scythes.materials;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

public class Materials {
    public static final ToolMaterial CLOUD_MATERIAL = new ToolMaterial(null,500,7.0f,2.0f,10, TagKey.of(Registries.ITEM.getKey(), Items.CALCITE.asItem().getRegistryEntry().getKey().get().getValue()));
    public static final ToolMaterial ENDER_MATERIAL = new ToolMaterial(null,550,7.0f,1.5f,10, TagKey.of(Registries.ITEM.getKey(), Items.ENDER_PEARL.asItem().getRegistryEntry().getKey().get().getValue()));
    public static final ToolMaterial GOLEM_SCYTHE = new ToolMaterial(null,550,7.0f,4.0f,10, TagKey.of(Registries.ITEM.getKey(), Items.IRON_BLOCK.asItem().getRegistryEntry().getKey().get().getValue()));
    public static final ToolMaterial GROWTH_MATERIAL = new ToolMaterial(null,8,7.0f,1.5f,10, TagKey.of(Registries.ITEM.getKey(), Items.BLAZE_POWDER.asItem().getRegistryEntry().getKey().get().getValue()));
    public static final ToolMaterial GUNPOWDER_MATERIAL = new ToolMaterial(null,1,7.0f,0.5f,10, TagKey.of(Registries.ITEM.getKey(), Items.GUNPOWDER.asItem().getRegistryEntry().getKey().get().getValue()));
    public static final ToolMaterial ICE_MATERIAL = new ToolMaterial(null,330,7.0f,1.5f,10, TagKey.of(Registries.ITEM.getKey(), Items.ICE.asItem().getRegistryEntry().getKey().get().getValue()));
    public static final ToolMaterial TURTLE_MATERIAL = new ToolMaterial(null,330,7.0f,1.25f,10, TagKey.of(Registries.ITEM.getKey(), Items.TURTLE_SCUTE.asItem().getRegistryEntry().getKey().get().getValue()));
    public static final ToolMaterial SUSPICIOUS_MATERIAL = new ToolMaterial(null,330,7.0f,1.25f,10, TagKey.of(Registries.ITEM.getKey(), Items.BOWL.asItem().getRegistryEntry().getKey().get().getValue()));
}
