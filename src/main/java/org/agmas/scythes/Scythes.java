package org.agmas.scythes;

import com.mojang.serialization.Codec;
import eu.pb4.polymer.networking.api.PolymerNetworking;
import eu.pb4.polymer.networking.api.client.PolymerClientNetworking;
import eu.pb4.polymer.networking.api.server.PolymerServerNetworking;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.item.ItemGroups;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.util.Identifier;
import org.agmas.scythes.items.Scythe;
import org.agmas.scythes.materials.CloudMaterial;

import java.util.HashMap;
import java.util.UUID;

public class Scythes implements ModInitializer {

    public static HashMap<UUID, Boolean> canDoubleJump = new HashMap<>();
    public static Identifier REGISTER_PACKET = Identifier.of("scythes", "register_packet");

    @Override
    public void onInitialize() {
        ScythesItems.initalize();
        PolymerServerNetworking.setServerMetadata(REGISTER_PACKET, NbtInt.of(1));
        PolymerClientNetworking.setClientMetadata(REGISTER_PACKET, NbtInt.of(1));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((a)->{
            a.add(ScythesItems.WOODEN_SCYTHE);
            a.add(ScythesItems.STONE_SCYTHE);
            a.add(ScythesItems.IRON_SCYTHE);
            a.add(ScythesItems.CLOUD_SCYTHE);
            a.add(ScythesItems.GOLDEN_SCYTHE);
            a.add(ScythesItems.DIAMOND_SCYTHE);
            a.add(ScythesItems.NETHERITE_SCYTHE);
        });
        ServerTickEvents.START_SERVER_TICK.register((s)->{
            s.getPlayerManager().getPlayerList().forEach((p)->{

                PlayerAbilities abilities = p.getAbilities();
                if (!p.getAbilities().allowFlying && !p.interactionManager.getGameMode().isSurvivalLike()) {
                    boolean skip = false;
                    if (p.getInventory().getMainHandStack().getItem() instanceof Scythe ss) {
                        if (ss.toolMaterial.equals(CloudMaterial.INSTANCE)) {
                            skip = true;
                        }
                    }
                    if (!skip) {
                        p.interactionManager.getGameMode().setAbilities(abilities);
                        p.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(abilities));
                    }
                }
                if (p.getAbilities().allowFlying && p.interactionManager.getGameMode().isSurvivalLike()) {
                    boolean skip = false;
                    if (p.getInventory().getMainHandStack().getItem() instanceof Scythe ss) {
                        if (ss.toolMaterial.equals(CloudMaterial.INSTANCE)) {
                            skip = true;
                        }
                    }
                    if (!skip) {
                        p.interactionManager.getGameMode().setAbilities(abilities);
                        p.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(abilities));
                    }
                }
            });
        });
        PolymerResourcePackUtils.addModAssets("scythes");
    }
}
