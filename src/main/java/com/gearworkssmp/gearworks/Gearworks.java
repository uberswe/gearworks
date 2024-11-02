package com.gearworkssmp.gearworks;

import java.time.LocalDate;
import java.util.UUID;

import com.gearworkssmp.gearworks.mixin.FTBChunksMapManagerMixin;

import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import dev.ftb.mods.ftbchunks.net.LoginDataPacket;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;

import net.minecraft.network.PacketByteBuf;

import net.minecraft.util.Identifier;

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
	public static final String CLIENT_CHANNEL = "client_channel";
	public static final String SWITCH_SERVER_MESSAGE = "SWITCHSERVER";
	public static final Identifier SWITCH_SERVER_EVENT_ID = new Identifier(ID, CLIENT_CHANNEL);
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
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity player = handler.getPlayer();
			sendServerJoinEventToClient(player);
		});
	}

	private static void sendServerJoinEventToClient(ServerPlayerEntity player) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeString(SWITCH_SERVER_MESSAGE);

		// Send the packet to the specific player
		ServerPlayNetworking.send(player, SWITCH_SERVER_EVENT_ID, buf);


		// This should already be done by FTB

//		SNBTCompoundTag config = new SNBTCompoundTag();
//		FTBChunksWorldConfig.CONFIG.write(config);
//		UUID managerId = FTBTeamsAPI.api().getManager().getId();
//		new LoginDataPacket(managerId, config).sendTo(player);
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
