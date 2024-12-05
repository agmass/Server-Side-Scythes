package org.agmas.scythes;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.agmas.scythes.items.*;
import org.agmas.scythes.materials.*;

public class ScythesItems {

    public static final Item DIAMOND_SCYTHE = register(
            new Scythe(new Item.Settings(), ToolMaterials.DIAMOND, "diamond_scythe", Items.DIAMOND_HOE, 0),
            "diamond_scythe"
    );
    public static final Item IRON_SCYTHE = register(
            new Scythe(new Item.Settings(), ToolMaterials.IRON, "iron_scythe", Items.IRON_HOE, 0),
            "iron_scythe"
    );
    public static final Item GOLDEN_SCYTHE = register(
            new Scythe(new Item.Settings(), ToolMaterials.GOLD, "golden_scythe", Items.GOLDEN_HOE, 0),
            "golden_scythe"
    );
    public static final Item NETHERITE_SCYTHE = register(
            new Scythe(new Item.Settings(), ToolMaterials.NETHERITE, "netherite_scythe", Items.NETHERITE_HOE, 0.3f),
            "netherite_scythe"
    );
    public static final Item WOODEN_SCYTHE = register(
            new Scythe(new Item.Settings(), ToolMaterials.WOOD, "wooden_scythe", Items.WOODEN_HOE, 0),
            "wooden_scythe"
    );
    public static final Item STONE_SCYTHE = register(
            new Scythe(new Item.Settings(), ToolMaterials.STONE, "stone_scythe", Items.STONE_HOE, 0),
            "stone_scythe"
    );
    public static final Item CLOUD_SCYTHE = register(
            new CloudScythe(new Item.Settings(), CloudMaterial.INSTANCE, "cloud_scythe", Items.IRON_HOE),
            "cloud_scythe"
    );
    public static final Item TURTLE_SCYTHE = register(
            new TurtleScythe(new Item.Settings(), TurtleMaterial.INSTANCE, "turtle_scythe", Items.TIPPED_ARROW),
            "turtle_scythe"
    );
    public static final Item GUNPOWDER_SCYTHE = register(
            new GunpowderScythe(new Item.Settings(), GunpowderMaterial.INSTANCE, "gunpowder_scythe", Items.TIPPED_ARROW),
            "gunpowder_scythe"
    );
    public static final Item SUSPICIOUS_SCYTHE = register(
            new SuspicousScythe(new Item.Settings(), SuspiciousMaterial.INSTANCE, "suspicious_scythe", Items.TIPPED_ARROW),
            "suspicious_scythe"
    );
    public static final Item GOLEM_SCYTHE = register(
            new GolemScythe(new Item.Settings(), GolemMaterial.INSTANCE, "golem_scythe", Items.IRON_HOE),
            "golem_scythe"
    );
    public static final Item ICE_SCYTHE = register(
            new IceScythe(new Item.Settings(), IceMaterial.INSTANCE, "golem_scythe", Items.TIPPED_ARROW),
            "ice_scythe"
    );
    public static final Item ENDER_SCYTHE = register(
            new EnderScythe(new Item.Settings(), EnderMaterial.INSTANCE, "ender_scythe", Items.TIPPED_ARROW),
            "ender_scythe"
    );
    public static final Item BORDER_SCYTHE = register(
            new BorderScythe(new Item.Settings(), ToolMaterials.DIAMOND, "border_scythe", Items.TIPPED_ARROW),
            "border_scythe"
    );
        public static final Item BAN_SCYTHE = register(
            new BanScythe(new Item.Settings(), ToolMaterials.DIAMOND, "border_scythe", Items.TIPPED_ARROW),
            "ban_scythe"
    );
    public static final Item GROWTH_SCYTHE = register(
            new GrowthScythe(new Item.Settings(), GrowthMaterial.INSTANCE, "growth_scythe", Items.TIPPED_ARROW),
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
