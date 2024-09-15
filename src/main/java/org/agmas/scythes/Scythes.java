package org.agmas.scythes;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

import java.util.HashMap;
import java.util.UUID;

public class Scythes implements ModInitializer {

    public static HashMap<UUID, Boolean> canDoubleJump = new HashMap<>();

    @Override
    public void onInitialize() {
        ScythesItems.initalize();
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((a)->{
            a.add(ScythesItems.WOODEN_SCYTHE);
            a.add(ScythesItems.STONE_SCYTHE);
            a.add(ScythesItems.IRON_SCYTHE);
            a.add(ScythesItems.CLOUD_SCYTHE);
            a.add(ScythesItems.GOLDEN_SCYTHE);
            a.add(ScythesItems.DIAMOND_SCYTHE);
            a.add(ScythesItems.NETHERITE_SCYTHE);
        });
        PolymerResourcePackUtils.addModAssets("scythes");
    }
}
