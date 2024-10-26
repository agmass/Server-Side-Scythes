package org.agmas.scythes.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.agmas.scythes.items.Scythe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @ModifyVariable(method = "attack", at =@At("STORE"), ordinal = 3)
    public boolean a(boolean value) {
        if (me().getStackInHand(Hand.MAIN_HAND).getItem() instanceof Scythe) {
            return true;
        }
        return value;
    }

    public PlayerEntity me() {
        return (PlayerEntity) (Object) this;
    }
}
