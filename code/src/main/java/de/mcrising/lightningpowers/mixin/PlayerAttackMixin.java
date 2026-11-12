package de.mcrising.lightningpowers.mixin;

import de.mcrising.lightningpowers.LightningPowers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerAttackMixin {
    @Inject(method = "attack", at = @At("TAIL"))
    private void lightningpowers$attack(Entity target, CallbackInfo ci) {
        LightningPowers.electricAttack((Player)(Object)this, target);
    }
}
