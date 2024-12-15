package com.gearworkssmp.gearworks.mixin;

import com.gearworkssmp.gearworks.events.PlayerDeathCallback;

import net.minecraft.entity.damage.DamageSource;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin {
	@Inject(method = "onDeath", at = @At(value = "TAIL"))
	private void onPlayerDeath(DamageSource damageSource, CallbackInfo ci) {
		ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
		PlayerDeathCallback.EVENT.invoker().onPlayerDeath(player, damageSource);
	}
}
