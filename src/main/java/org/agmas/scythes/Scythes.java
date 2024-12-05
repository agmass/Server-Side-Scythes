package org.agmas.scythes;

import com.mojang.serialization.Codec;
import eu.pb4.polymer.networking.api.PolymerServerNetworking;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.DedicatedServerModInitializer;
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
import org.agmas.scythes.util.BorderRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Scythes implements ModInitializer {

    public static HashMap<UUID, Boolean> canDoubleJump = new HashMap<>();
    public static ArrayList<BorderRoom> borderRooms = new ArrayList<>();
    public static Identifier REGISTER_PACKET = Identifier.of("scythes", "register_packet");

    @Override
    public void onInitialize() {
        ScythesItems.initalize();
        PolymerServerNetworking.setServerMetadata(REGISTER_PACKET, NbtInt.of(1));
        PolymerResourcePackUtils.addModAssets("scythes");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((a)->{
            a.add(ScythesItems.WOODEN_SCYTHE);
            a.add(ScythesItems.STONE_SCYTHE);
            a.add(ScythesItems.IRON_SCYTHE);
            a.add(ScythesItems.CLOUD_SCYTHE);
            a.add(ScythesItems.GOLDEN_SCYTHE);
            a.add(ScythesItems.DIAMOND_SCYTHE);
            a.add(ScythesItems.NETHERITE_SCYTHE);

            a.add(ScythesItems.TURTLE_SCYTHE);
            a.add(ScythesItems.ENDER_SCYTHE);
            a.add(ScythesItems.GOLEM_SCYTHE);
            a.add(ScythesItems.GUNPOWDER_SCYTHE);
            a.add(ScythesItems.SUSPICIOUS_SCYTHE);
            a.add(ScythesItems.ICE_SCYTHE);
            a.add(ScythesItems.BORDER_SCYTHE);
            a.add(ScythesItems.GROWTH_SCYTHE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register((a)->{
            a.add(ScythesItems.BAN_SCYTHE);
        });
        ScythesEffects.init();
        ServerTickEvents.START_SERVER_TICK.register((s)->{
            borderRooms.forEach(BorderRoom::tick);
            borderRooms.removeIf(b -> b.remove);
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
    }

}
