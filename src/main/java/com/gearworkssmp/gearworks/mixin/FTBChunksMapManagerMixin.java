package com.gearworkssmp.gearworks.mixin;

import com.gearworkssmp.gearworks.Gearworks;

import dev.ftb.mods.ftbchunks.client.map.MapManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;


@Pseudo
@Mixin(MapManager.class)
public class FTBChunksMapManagerMixin {

	@Inject(method = "startUp", at = @At("HEAD"), remap = false)
	private static void onStartUp(UUID serverId, CallbackInfo ci) {
		Gearworks.LOGGER.info("onStartupCalled: {}", serverId);
		if (MapManager.getInstance().isPresent()) {
			Gearworks.LOGGER.info("Shutting down before starting: {}", MapManager.getInstance().get().getServerId());
		}
		additionalShutDown(serverId);
	}

	@Unique
	private static void additionalShutDown(UUID serverId) {
		Gearworks.LOGGER.info("server id, shutdown called: {}", serverId);
		MapManager.shutdown();
	}
}
