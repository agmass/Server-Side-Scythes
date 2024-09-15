package org.agmas.scythes;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.agmas.scythes.items.Scythe;
import org.agmas.scythes.materials.CloudMaterial;

public class ScythesItems {

    public static final Item DIAMOND_SCYTHE = register(
            new Scythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterials.DIAMOND, 4f, -3.2f)), ToolMaterials.DIAMOND),
            "diamond_scythe"
    );
    public static final Item IRON_SCYTHE = register(
            new Scythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterials.IRON, 4f, -3.2f)), ToolMaterials.IRON),
            "iron_scythe"
    );
    public static final Item GOLDEN_SCYTHE = register(
            new Scythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterials.GOLD, 4f, -3.2f)), ToolMaterials.GOLD),
            "golden_scythe"
    );
    public static final Item NETHERITE_SCYTHE = register(
            new Scythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterials.NETHERITE, 3.6f, -3.2f)), ToolMaterials.NETHERITE),
            "netherite_scythe"
    );
    public static final Item WOODEN_SCYTHE = register(
            new Scythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterials.WOOD, 4f, -3.2f)), ToolMaterials.WOOD),
            "wooden_scythe"
    );
    public static final Item STONE_SCYTHE = register(
            new Scythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(ToolMaterials.STONE, 4f, -3.2f)), ToolMaterials.STONE),
            "stone_scythe"
    );
    public static final Item CLOUD_SCYTHE = register(
            new Scythe(new Item.Settings().attributeModifiers(Scythe.createAttributeModifiers(CloudMaterial.INSTANCE, 4f, -3.2f)), CloudMaterial.INSTANCE),
            "cloud_scythe"
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
