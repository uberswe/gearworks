package com.gearworkssmp.gearworks;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gearworkssmp.gearworks.events.PlayerDeathCallback;
import com.gearworkssmp.gearworks.item.ModItems;
import com.gearworkssmp.gearworks.item.ModLootTableModifiers;
import com.gearworkssmp.gearworks.item.ModMobSpawnModifier;
import com.simibubi.create.Create;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class Gearworks implements ModInitializer {
	public static final String ID = "gearworks";
	public static final String NAME = "Gearworks";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static final String[] BETA_PARTICIPANTS = {
			"uberswe",
			"Goofyahhhcat",
			"SilentDemon678",
			"ItzAmbie",
			"baconmemes",
			"GamerLeonLP",
			"Ishnula",
			"TrentTinkers",
			"kivatornado",
			"BluetoothBread",
			"Guybrrush",
			"Phoenix10_9",
			"Arcadiuz",
			"BaniaKac",
			"kieranstinkyfeet",
			"Prancing_Blackie",
			"Dante_Dravon",
			"jcwnnr",
			"RobloticGuy",
			"Racakata",
			"Caztheaxolotl",
			"SeanieMac99",
			"ZALKIE",
			"_Kuha"
	};

	@Override
	public void onInitialize() {
		LOGGER.info("Create addon mod [{}] is loading alongside Create [{}]!", NAME, Create.VERSION);
		LOGGER.info(EnvExecutor.unsafeRunForDist(
				() -> () -> "{} is accessing Porting Lib from the client!",
				() -> () -> "{} is accessing Porting Lib from the server!"
		), NAME);
		ModItems.registerModItems();
		ModLootTableModifiers.modifyLootTables();
		ModMobSpawnModifier.modifyMobSpawns();
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

	public static boolean isBetaParticipant(PlayerEntity player) {
		for (String name : BETA_PARTICIPANTS) {
			if (name.equalsIgnoreCase(player.getGameProfile().getName())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isCloseToHalloween() {
		LocalDate today = LocalDate.now();
		// Define the start and end dates for the range
		LocalDate startDate = LocalDate.of(today.getYear(), 10, 1);  // October 1st
		LocalDate endDate = LocalDate.of(today.getYear(), 11, 5);    // November 5th

		// Check if today is within the range (inclusive)
		return (today.isEqual(startDate) || today.isAfter(startDate)) &&
				(today.isEqual(endDate) || today.isBefore(endDate));
	}
}
