package com.gearworkssmp.gearworks.events;

import java.util.UUID;

public class ScheduledSpawn {
	public final long spawnTime;      // The tick time when the creeper should spawn
	public final double x, y, z;      // Coordinates
	public final UUID playerUuid;     // Player reference if needed

	public ScheduledSpawn(long spawnTime, double x, double y, double z, UUID playerUuid) {
		this.spawnTime = spawnTime;
		this.x = x;
		this.y = y;
		this.z = z;
		this.playerUuid = playerUuid;
	}
}
