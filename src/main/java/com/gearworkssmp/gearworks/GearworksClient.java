package com.gearworkssmp.gearworks;

import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.client.map.MapManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

public class GearworksClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(Gearworks.SWITCH_SERVER_EVENT_ID, (client, handler, buf, responseSender) -> {
			String message = buf.readString(); // Read the message from the server

			// Handle the packet on the client thread
			client.execute(() -> {
				// Execute any client-side logic with the received data
				if (message.equals(Gearworks.SWITCH_SERVER_MESSAGE)) {
					MinecraftServer server = client.getServer();
					if (server != null) {
						onJoinServer(server);
					} else if (client.player != null){
						client.player.sendMessage(Text.of("Server is null!"), false);
					}
				}
			});
		});
	}

	private void onJoinServer(MinecraftServer server) {
		// Do nothing for now
	}

}
