package org.agmas.scythes;

import net.fabricmc.api.ModInitializer;

import java.util.HashMap;
import java.util.UUID;

public class Scythes implements ModInitializer {

    public static HashMap<UUID, Boolean> canDoubleJump = new HashMap<>();

    @Override
    public void onInitialize() {
        ScythesItems.initalize();
    }
}
