package de.mcrising.lightningpowers.mixin;

import de.mcrising.lightningpowers.LightningPowers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityLightningMixin {
    @Inject(method = "thunderHit", at = @At("HEAD"), cancellable = true)
    private void lightningpowers$thunderHit(ServerLevel level, LightningBolt lightning, CallbackInfo ci) {
        if ((Object)this instanceof ServerPlayer player && LightningPowers.onLightning(player)) ci.cancel();
    }
}
