package com.gearworkssmp.gearworks;

import com.gearworkssmp.gearworks.events.PlayerDeathCallback;
import com.gearworkssmp.gearworks.item.ModItems;
import com.simibubi.create.Create;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;


import net.minecraft.entity.damage.DamageSource;

import net.minecraft.server.network.ServerPlayerEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gearworks implements ModInitializer {
	public static final String ID = "gearworks";
	public static final String NAME = "Gearworks";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	@Override
	public void onInitialize() {
		LOGGER.info("Create addon mod [{}] is loading alongside Create [{}]!", NAME, Create.VERSION);
		LOGGER.info(EnvExecutor.unsafeRunForDist(
				() -> () -> "{} is accessing Porting Lib from the client!",
				() -> () -> "{} is accessing Porting Lib from the server!"
		), NAME);
		ModItems.registerModItems();
		registerEvents();
	}

	public static void registerEvents() {
		PlayerDeathCallback.EVENT.register(Gearworks::onPlayerDeathEvent);
	}

	public static void onPlayerDeathEvent(ServerPlayerEntity player, DamageSource source) {
		LOGGER.info("Player died: {}", player.getName().getString());
		player.totalExperience = 0;
		player.experienceLevel = 0;
		player.experienceProgress = 0.0f;
	}
}
