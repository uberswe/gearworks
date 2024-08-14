package com.gearworkssmp.gearworks;

import com.simibubi.create.Create;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

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

		ServerPlayConnectionEvents.JOIN.register(this::onjoin);
		ServerPlayConnectionEvents.DISCONNECT.register(this::onDisconnect);
	}

	private void onDisconnect(ServerGamePacketListenerImpl serverGamePacketListener, MinecraftServer minecraftServer) {
	}

	private void onjoin(ServerGamePacketListenerImpl serverGamePacketListener, PacketSender packetSender, MinecraftServer minecraftServer) {
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}

// TODO Add custom hat item

// TODO sync inventories
// MPDS uses two events
// ServerPlayConnectionEvents.JOIN
// ServerPlayConnectionEvents.DISCONNECT
// Note that disconnect is not always called

// MCInvSync works in the same way but a bit more abstracted and also tries to sync achievements

// How to sync Create contraptions?
// look at CartAssemblerBlockEntity assemble function

// TODO Should sync be used with MySQL or GRPC? GRPC allows 2 way communication but requires a backend
