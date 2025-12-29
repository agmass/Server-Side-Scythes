package org.agmas.scythes;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.nbt.NbtInt;
import net.minecraft.util.Identifier;
import net.mokus.mokuslib.api.MokusLibClientAPI;

public class ScythesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MokusLibClientAPI.registerItemModels(Identifier.of(Scythes.MOD_ID, "cloud_scythe_inv"), Identifier.of(Scythes.MOD_ID, "cloud_scythe_alt_1"), Identifier.of(Scythes.MOD_ID, "cloud_scythe_alt_1_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "ice_scythe_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "growth_scythe_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "ender_scythe_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "ban_scythe_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "border_scythe_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "golem_scythe_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "gunpowder_scythe_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "suspicious_scythe_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "turtle_scythe_inv"));

        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "diamond_scythe_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "netherite_scythe_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "iron_scythe_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "golden_scythe_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "stone_scythe_inv"));
        MokusLibClientAPI.registerItemModel(Identifier.of(Scythes.MOD_ID, "wooden_scythe_inv"));
    }
}
