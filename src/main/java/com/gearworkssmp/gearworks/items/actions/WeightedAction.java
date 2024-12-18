package com.gearworkssmp.gearworks.items.actions;

import java.util.function.BiConsumer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class WeightedAction {
	public final BiConsumer<PlayerEntity, ServerWorld> action;
	public final float weight;

	public WeightedAction(BiConsumer<PlayerEntity, ServerWorld> action, float weight) {
		this.action = action;
		this.weight = weight;
	}
}
