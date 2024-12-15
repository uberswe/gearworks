package com.gearworkssmp.gearworks.items;

import com.gearworkssmp.gearworks.Gearworks;

import com.gearworkssmp.gearworks.events.ScheduledSpawn;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;
import java.util.random.RandomGenerator;

public class TrickOrTreatCandy extends Item {

	private static final int MAX_LEVEL = 5;
	private static final StatusEffect[] POSITIVE_EFFECTS = {
			StatusEffects.SPEED,
			StatusEffects.STRENGTH,
			StatusEffects.JUMP_BOOST,
			StatusEffects.LUCK,
			StatusEffects.ABSORPTION,
			StatusEffects.CONDUIT_POWER,
			StatusEffects.DOLPHINS_GRACE,
			StatusEffects.FIRE_RESISTANCE,
			StatusEffects.GLOWING,
			StatusEffects.HASTE,
			StatusEffects.HEALTH_BOOST,
			StatusEffects.INVISIBILITY,
			StatusEffects.JUMP_BOOST,
			StatusEffects.LEVITATION,
			StatusEffects.NIGHT_VISION,
			StatusEffects.REGENERATION,
			StatusEffects.RESISTANCE,
			StatusEffects.SATURATION,
			StatusEffects.SLOW_FALLING,
			StatusEffects.SPEED,
			StatusEffects.STRENGTH,
			StatusEffects.WATER_BREATHING,
	};
	private static final StatusEffect[] NEGATIVE_EFFECTS = {
			StatusEffects.BLINDNESS,
			StatusEffects.POISON,
			StatusEffects.BAD_OMEN,
			StatusEffects.DARKNESS,
			StatusEffects.HUNGER,
			StatusEffects.MINING_FATIGUE,
			StatusEffects.NAUSEA,
			StatusEffects.SLOWNESS,
			StatusEffects.UNLUCK,
			StatusEffects.WITHER,
	};

	public TrickOrTreatCandy(Settings settings) {
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("item.gearworks.trick_or_treat_candy.tooltip"));
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		ItemStack itemStack = super.finishUsing(stack, world, user);

		if (!world.isClient && user instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) user;
			float randomFloat = player.getRandom().nextFloat();
			if (randomFloat < 0.25f) {
				applyRandomScale(player, world);
			} else if (randomFloat > 0.75f) {
				applyPositiveEffects(player, world);
			} else if (randomFloat >= 0.25f && randomFloat <= 0.5f) {
				applyNegativeEffects(player, world);
			} else {
				spawnRandomMobs(player, world);
			}
		}

		return itemStack;
	}

	private void spawnRandomMobs(PlayerEntity player, World world) {
		if (world instanceof ServerWorld) {
			float randomFloat = player.getRandom().nextFloat();
			if (randomFloat >= 0.5f) {
				spawnSlimeTower(player, (ServerWorld) world);
			} else if (randomFloat <= 0.5f) {
				spawnCreeperRain(player, (ServerWorld) world);
			}
		}
	}

	private void applyPositiveEffects(PlayerEntity player, World world) {
		for (StatusEffect effect : POSITIVE_EFFECTS) {
			if (player.getRandom().nextFloat() > 0.2f) {
				player.addStatusEffect(new StatusEffectInstance(effect, 300 * RandomGenerator.getDefault().nextInt(MAX_LEVEL), RandomGenerator.getDefault().nextInt(MAX_LEVEL)));
			}
		}
	}

	private void applyNegativeEffects(PlayerEntity player, World world) {
		for (StatusEffect effect : NEGATIVE_EFFECTS) {
			if (player.getRandom().nextFloat() > 0.2f) {
				player.addStatusEffect(new StatusEffectInstance(effect, 300 * RandomGenerator.getDefault().nextInt(MAX_LEVEL), RandomGenerator.getDefault().nextInt(MAX_LEVEL)));
			}
		}
	}

	private void applyRandomScale(PlayerEntity player, World world) {
		float randomScale;
		if (player.getRandom().nextFloat() > 0.5f) {
			randomScale = 1f + player.getRandom().nextFloat() * 10.0f;
		} else {
			randomScale = 1f - player.getRandom().nextFloat() * 0.99f;
		}

		ScaleData scaleData = ScaleTypes.BASE.getScaleData(player);
		scaleData.setTargetScale(randomScale);
		scaleData.setScaleTickDelay(60);

		if (randomScale < 1.0f) {
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 3));
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 600, 3));
		}

		// Schedule a task to revert the scale after 30 seconds (600 ticks)
		if (!world.isClient()) {
			long currentTime = world.getServer().getOverworld().getTime();
			long revertTime = currentTime + 600;
			Gearworks.SCHEDULED_REVERTS.put(player.getUuid(), revertTime);
		}
	}

	private void spawnSlimeTower(PlayerEntity player, ServerWorld world) {
		// Number of slimes in the tower
		int slimeCount = 8;
		// Smallest slime size at the bottom
		int smallestSlimeSize = 1;
		// Largest slime size at the top
		int largestSlimeSize = 8;

		boolean smallestFirst = true;

		// The increment in size from bottom to top
		int sizeIncrement = (largestSlimeSize - smallestSlimeSize) / (slimeCount - 1);

		// Bottom slime’s position (just above the ground level, at player’s feet)
		double x = player.getX();
		double y = player.getY();
		double z = player.getZ();
		if (player.getRandom().nextFloat() > 0.5f) {
			smallestFirst = false;
		}
		SlimeEntity currentBaseSlime;
		// Spawn the bottom slime (smallest)
		if (smallestFirst) {
			currentBaseSlime = spawnSlime(player, world, x, y + 10, z + 10, smallestSlimeSize);
		} else {
			currentBaseSlime = spawnSlime(player, world, x, y + 10, z + 10, largestSlimeSize);
		}

		// For each next slime, increase size and place it on top of the previous one
		for (int i = 2; i <= slimeCount; i++) {
			int slimeSize = smallestSlimeSize + sizeIncrement * (i - 1);
			if (!smallestFirst) {
				slimeSize = largestSlimeSize - sizeIncrement * (i - 1);
			}
			// Spawn the next slime
			SlimeEntity nextSlime = spawnSlime(player, world, x, y, z, slimeSize);

			// Make the next slime ride the current one to stack them
			nextSlime.startRiding(currentBaseSlime, true);

			// Update the base for the next iteration
			currentBaseSlime = nextSlime;
		}
	}

	private SlimeEntity spawnSlime(PlayerEntity player, ServerWorld world, double x, double y, double z, int size) {
		SlimeEntity slime = EntityType.SLIME.create(world);
		if (slime != null) {
			slime.refreshPositionAndAngles(x, y, z, world.random.nextFloat() * 360F, 0.0F);
			slime.setSize(size, true);
			world.spawnEntity(slime);
			slime.setAiDisabled(false);
			slime.setTarget(player);
		}
		return slime;
	}

	public static void spawnCreeperRain(PlayerEntity player, ServerWorld world) {
		Random random = player.getRandom();

		// Number of creepers to spawn
		int creeperCount = 30;
		// Delay between each creeper in ticks (20 ticks = 1 second)
		int delayPerCreeper = 20; // 1 second delay

		double playerX = player.getX();
		double playerY = player.getY();
		double playerZ = player.getZ();

		long currentTime = world.getTime();

		for (int i = 0; i < creeperCount; i++) {
			double offsetX = (random.nextDouble() - 0.5) * 10;
			double offsetZ = (random.nextDouble() - 0.5) * 10;

			double spawnX = playerX + offsetX;
			double spawnY = playerY + 5 + random.nextInt(10); // 20-30 blocks above
			double spawnZ = playerZ + offsetZ;

			long spawnTime = currentTime + (i * delayPerCreeper);

			// Schedule the creeper spawn
			Gearworks.SCHEDULED_CREEPER_SPAWNS.add(
					new ScheduledSpawn(spawnTime, spawnX, spawnY, spawnZ, player.getUuid())
			);
		}
	}

}
