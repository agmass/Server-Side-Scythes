package org.agmas.scythes;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.agmas.scythes.items.*;
import org.agmas.scythes.materials.*;

public class ScythesItems {


    public static final Item DIAMOND_SCYTHE = register(
            new Scythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterial.DIAMOND, 4f, -3.2f)), ToolMaterial.DIAMOND, "diamond_scythe", Items.DIAMOND_HOE),
            "diamond_scythe"
    );
    public static final Item IRON_SCYTHE = register(
            new Scythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterial.IRON, 4f, -3.2f)), ToolMaterial.IRON, "iron_scythe", Items.IRON_HOE),
            "iron_scythe"
    );
    public static final Item GOLDEN_SCYTHE = register(
            new Scythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterial.GOLD, 4f, -3.2f)), ToolMaterial.GOLD, "golden_scythe", Items.GOLDEN_HOE),
            "golden_scythe"
    );
    public static final Item NETHERITE_SCYTHE = register(
            new Scythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterial.NETHERITE, 3.6f, -3.2f)), ToolMaterial.NETHERITE, "netherite_scythe", Items.NETHERITE_HOE),
            "netherite_scythe"
    );
    public static final Item WOODEN_SCYTHE = register(
            new Scythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterial.WOOD, 4f, -3.2f)), ToolMaterial.WOOD, "wooden_scythe", Items.WOODEN_HOE),
            "wooden_scythe"
    );
    public static final Item STONE_SCYTHE = register(
            new Scythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterial.STONE, 4f, -3.2f)), ToolMaterial.STONE, "stone_scythe", Items.STONE_HOE),
            "stone_scythe"
    );
    public static final Item CLOUD_SCYTHE = register(
            new CloudScythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(Materials.CLOUD_MATERIAL, 4f, -3.2f)), Materials.CLOUD_MATERIAL, "cloud_scythe", Items.IRON_HOE),
            "cloud_scythe"
    );
    public static final Item TURTLE_SCYTHE = register(
            new TurtleScythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(Materials.TURTLE_MATERIAL, 4f, -3.2f)), Materials.TURTLE_MATERIAL, "turtle_scythe", Items.TIPPED_ARROW),
            "turtle_scythe"
    );
    public static final Item GUNPOWDER_SCYTHE = register(
            new GunpowderScythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(Materials.GUNPOWDER_MATERIAL, 4f, -3.2f)), Materials.GUNPOWDER_MATERIAL, "gunpowder_scythe", Items.TIPPED_ARROW),
            "gunpowder_scythe"
    );
    public static final Item SUSPICIOUS_SCYTHE = register(
            new SuspicousScythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(Materials.SUSPICIOUS_MATERIAL, 4f, -3.2f)), Materials.SUSPICIOUS_MATERIAL, "suspicious_scythe", Items.TIPPED_ARROW),
            "suspicious_scythe"
    );
    public static final Item GOLEM_SCYTHE = register(
            new GolemScythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(Materials.GOLEM_SCYTHE, 4f, -3.5F)), Materials.GOLEM_SCYTHE, "golem_scythe", Items.IRON_HOE),
            "golem_scythe"
    );
    public static final Item ICE_SCYTHE = register(
            new IceScythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(Materials.ICE_MATERIAL, 4f, -3.2F)), Materials.ICE_MATERIAL, "ice_scythe", Items.TIPPED_ARROW),
            "ice_scythe"
    );
    public static final Item ENDER_SCYTHE = register(
            new EnderScythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(Materials.ENDER_MATERIAL, 4f, -3.2F)), Materials.ENDER_MATERIAL, "ender_scythe", Items.TIPPED_ARROW),
            "ender_scythe"
    );
    public static final Item BORDER_SCYTHE = register(
            new BorderScythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterial.DIAMOND, 4f, -3.2F)), ToolMaterial.DIAMOND, "border_scythe", Items.TIPPED_ARROW),
            "border_scythe"
    );
        public static final Item BAN_SCYTHE = register(
            new BanScythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterial.DIAMOND, 4f, -3.2F)), ToolMaterial.DIAMOND, "ban_scythe", Items.TIPPED_ARROW),
            "ban_scythe"
    );
    public static final Item GROWTH_SCYTHE = register(
            new GrowthScythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(Materials.GROWTH_MATERIAL, 4f, -3.2F)), Materials.GROWTH_MATERIAL, "growth_scythe", Items.TIPPED_ARROW),
            "growth_scythe"
    );





    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = Identifier.of("scythes", id);

        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }

    public static void initalize() {}
}
