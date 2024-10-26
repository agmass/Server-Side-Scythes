package org.agmas.scythes;

import eu.pb4.polymer.networking.api.client.PolymerClientNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.nbt.NbtInt;

public class ScythesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        PolymerClientNetworking.setClientMetadata(Scythes.REGISTER_PACKET, NbtInt.of(1));
    }
}
