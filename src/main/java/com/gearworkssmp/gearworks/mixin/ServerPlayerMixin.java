package com.gearworkssmp.gearworks.mixin;

import com.gearworkssmp.gearworks.events.PlayerDeathCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
	@Inject(method = "die", at = @At(value = "TAIL"))
	private void onPlayerDeath(DamageSource damageSource, CallbackInfo ci) {
		ServerPlayer player = (ServerPlayer) (Object) this;
		PlayerDeathCallback.EVENT.invoker().onPlayerDeath(player, damageSource);
	}
}
