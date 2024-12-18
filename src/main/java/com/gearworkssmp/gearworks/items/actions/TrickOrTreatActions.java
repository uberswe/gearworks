package com.gearworkssmp.gearworks.items.actions;

import com.gearworkssmp.gearworks.Gearworks;
import com.gearworkssmp.gearworks.events.ScheduledSpawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.MuleEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public class TrickOrTreatActions {
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

	public static void applyPositiveEffects(PlayerEntity player, World world) {
		player.sendMessage(Text.literal("Applied positive buffs!"), false);
		for (StatusEffect effect : POSITIVE_EFFECTS) {
			if (world.getRandom().nextFloat() > 0.2f) {
				player.addStatusEffect(new StatusEffectInstance(effect, 300 * world.getRandom().nextInt(MAX_LEVEL), world.getRandom().nextInt(MAX_LEVEL)));
			}
		}
	}

	public static void applyNegativeEffects(PlayerEntity player, World world) {
		player.sendMessage(Text.literal("Applied negative buffs!"), false);
		for (StatusEffect effect : NEGATIVE_EFFECTS) {
			if (world.getRandom().nextFloat() > 0.2f) {
				player.addStatusEffect(new StatusEffectInstance(effect, 300 * world.getRandom().nextInt(MAX_LEVEL), world.getRandom().nextInt(MAX_LEVEL)));
			}
		}
	}

	public static void applyRandomScale(PlayerEntity player, World world) {
		float randomScale;
		if (player.getRandom().nextFloat() > 0.5f) {
			player.sendMessage(Text.literal("You feel yourself getting a bit taller"), false);
			randomScale = 1f + player.getRandom().nextFloat() * 20.0f;
		} else {
			player.sendMessage(Text.literal("You shrink in size, but feel faster?!"), false);
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

	public static void spawnSlimeTower(PlayerEntity player, ServerWorld world) {
		player.sendMessage(Text.literal("A tower of... slimes!"), false);

		int slimeCount = 5;
		int smallestSlimeSize = 2;
		int largestSlimeSize = 6;
		boolean smallestFirst = player.getRandom().nextFloat() > 0.5f;

		// Increment in size from bottom to top
		int sizeIncrement = (largestSlimeSize - smallestSlimeSize) / (slimeCount - 1);

		// Choose a random location near the player on the surface
		Random random = world.getRandom();
		double offsetX = (random.nextDouble() - 0.5) * 20; // within 10 blocks horizontally
		double offsetZ = (random.nextDouble() - 0.5) * 20;
		double spawnX = player.getX() + offsetX;
		double spawnZ = player.getZ() + offsetZ;

		BlockPos groundPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos((int) spawnX, (int) player.getY(), (int) spawnZ));
		double spawnY = groundPos.getY();

		// Determine the bottom slime size
		int bottomSlimeSize = smallestFirst ? smallestSlimeSize : largestSlimeSize;
		SlimeEntity bottomSlime = spawnSlime(world, spawnX, spawnY, spawnZ, bottomSlimeSize);

		// If the bottom slime failed to spawn, just return
		if (bottomSlime == null) return;

		// We'll stack the remaining slimes on top of the bottom slime
		SlimeEntity currentBaseSlime = bottomSlime;

		for (int i = 2; i <= slimeCount; i++) {
			int slimeSize;
			if (smallestFirst) {
				slimeSize = smallestSlimeSize + sizeIncrement * (i - 1);
			} else {
				slimeSize = largestSlimeSize - sizeIncrement * (i - 1);
			}

			SlimeEntity nextSlime = spawnSlime(world, spawnX, spawnY, spawnZ, slimeSize);
			if (nextSlime != null) {
				nextSlime.startRiding(currentBaseSlime, true);
				currentBaseSlime = nextSlime;
				currentBaseSlime.getNavigation().startMovingTo(player.getX(), player.getY(), player.getZ(), 1.0D);
			}
		}

		bottomSlime.setAiDisabled(false);
		bottomSlime.speed = 10;
		bottomSlime.setPersistent();
		bottomSlime.getNavigation().startMovingTo(player.getX(), player.getY(), player.getZ(), 1.0D);
		bottomSlime.setAttacking(true);
		bottomSlime.setVelocity(0, 0, 0.5);
		bottomSlime.setAttacker(player);
		bottomSlime.velocityModified = true;
	}

	private static SlimeEntity spawnSlime(ServerWorld world, double x, double y, double z, int size) {
		SlimeEntity slime = EntityType.SLIME.create(world);
		if (slime != null) {
			slime.refreshPositionAndAngles(x, y, z, world.random.nextFloat() * 360F, 0.0F);
			slime.setPersistent();
			slime.setAiDisabled(false);
			slime.setSize(size, true);
			world.spawnEntity(slime);
		}
		return slime;
	}

	public static void spawnCreeperRain(PlayerEntity player, ServerWorld world) {
		player.sendMessage(Text.literal("Creeper rain!"), false);
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

	public static void spawnRainbowSheep(PlayerEntity player, ServerWorld world) {
		player.sendMessage(Text.literal("You see a rainbow!"), false);
		SheepEntity sheep = EntityType.SHEEP.create(world);
		if (sheep != null) {
			sheep.refreshPositionAndAngles(player.getX(), player.getY() + 1, player.getZ() + 1, world.random.nextFloat() * 360F, 0.0F);
			// Naming the sheep "jeb_" triggers the vanilla color-cycling Easter egg
			sheep.setCustomName(Text.literal("jeb_"));
			sheep.setPersistent();
			world.spawnEntity(sheep);
		}
	}

	public static void spawnAnimalsInCircle(PlayerEntity player, ServerWorld world) {
		player.sendMessage(Text.literal("You are surrounded!"), false);
		int animalCount = 8;       // Number of animals to spawn
		double radius = 5.0;       // Radius of the circle in blocks

		// Array of different farm animal entity types
		EntityType<?>[] farmAnimals = {
				EntityType.COW,
				EntityType.SHEEP,
				EntityType.PIG,
				EntityType.CHICKEN
		};

		// Random instance for choosing animal types
		Random random = world.getRandom();

		double playerX = player.getX();
		double playerY = player.getY();
		double playerZ = player.getZ();

		for (int i = 0; i < animalCount; i++) {
			double angle = 2 * Math.PI * i / animalCount;
			double offsetX = radius * Math.cos(angle);
			double offsetZ = radius * Math.sin(angle);

			double spawnX = playerX + offsetX;
			double spawnZ = playerZ + offsetZ;

			// Find the top position so animals spawn on the ground
			BlockPos groundPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos((int) spawnX, (int) playerY, (int) spawnZ));
			double spawnY = groundPos.getY();

			// Pick a random animal from the array
			EntityType<?> chosenAnimal = farmAnimals[random.nextInt(farmAnimals.length)];

			// Create the animal and spawn it
			LivingEntity animal = (LivingEntity) chosenAnimal.create(world);
			if (animal != null) {
				animal.refreshPositionAndAngles(spawnX, spawnY, spawnZ, (float)(angle * 180 / Math.PI), 0.0F);
				world.spawnEntity(animal);
			}
		}
	}

	public static void spawnFireworkFrenzy(PlayerEntity player, ServerWorld world) {
		player.sendMessage(Text.literal("Firework Frenzy!"), false);

		// Number of fireworks to spawn
		int fireworkCount = 10;
		// The radius within which they spawn around the player
		double radius = 5.0;
		Random random = world.getRandom();

		double playerX = player.getX();
		double playerY = player.getY() + 1.0; // slightly above ground
		double playerZ = player.getZ();

		for (int i = 0; i < fireworkCount; i++) {
			// Random position around the player
			double offsetX = (random.nextDouble() - 0.5) * 2 * radius;
			double offsetZ = (random.nextDouble() - 0.5) * 2 * radius;
			double spawnX = playerX + offsetX;
			double spawnY = playerY;
			double spawnZ = playerZ + offsetZ;

			ItemStack fireworkStack = createRandomFirework(random);
			FireworkRocketEntity firework = new FireworkRocketEntity(world, spawnX, spawnY, spawnZ, fireworkStack);
			world.spawnEntity(firework);
		}
	}

	private static ItemStack createRandomFirework(Random random) {
		ItemStack fireworkStack = new ItemStack(Items.FIREWORK_ROCKET, 1);

		// Set flight duration (1 to 3)
		byte flight = (byte)(1 + random.nextInt(3));

		// Create the Fireworks NBT
		NbtCompound fireworksNbt = new NbtCompound();
		fireworksNbt.putByte("Flight", flight);

		// Create explosions list
		NbtList explosions = new NbtList();
		explosions.add(createRandomExplosion(random));

		fireworksNbt.put("Explosions", explosions);
		fireworkStack.getOrCreateSubNbt("Fireworks").copyFrom(fireworksNbt);

		return fireworkStack;
	}

	private static NbtCompound createRandomExplosion(Random random) {
		NbtCompound explosion = new NbtCompound();

		// Choose a random type: 0 = small ball, 1 = large ball, 2 = star, 3 = creeper face, 4 = burst
		byte type = (byte)random.nextInt(5);
		explosion.putByte("Type", type);

		// Add random colors
		int colorCount = 1 + random.nextInt(3); // 1 to 3 colors
		int[] colors = new int[colorCount];
		for (int i = 0; i < colorCount; i++) {
			DyeColor dye = DyeColor.values()[random.nextInt(DyeColor.values().length)];
			colors[i] = dye.getFireworkColor();
		}
		explosion.putIntArray("Colors", colors);

		// Optional: Add fade colors, flicker, trail
		// Random flicker and trail
		if (random.nextBoolean()) explosion.putBoolean("Flicker", true);
		if (random.nextBoolean()) explosion.putBoolean("Trail", true);

		return explosion;
	}

	public static void spawnLivingLootPinata(PlayerEntity player, ServerWorld world) {
		player.sendMessage(Text.literal("A mysterious sheep appears..."), false);

		double playerX = player.getX();
		double playerZ = player.getZ();
		double playerY = player.getY();

		Random random = world.getRandom();

		// Random offset around player
		double offsetX = (random.nextDouble() - 0.5) * 6; // within 3 blocks horizontally
		double offsetZ = (random.nextDouble() - 0.5) * 6;
		double spawnX = playerX + offsetX;
		double spawnZ = playerZ + offsetZ;

		// Get top block at that position to spawn on surface
		BlockPos groundPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos((int) spawnX, (int) playerY, (int) spawnZ));
		double spawnY = groundPos.getY();

		SheepEntity sheep = EntityType.SHEEP.create(world);
		if (sheep != null) {
			sheep.refreshPositionAndAngles(spawnX, spawnY, spawnZ, world.random.nextFloat() * 360F, 0.0F);
			sheep.setPersistent();
			sheep.setCustomName(Text.literal("Loot Pinata"));
			sheep.setCustomNameVisible(true);

			// Give the sheep a special color and some effects
			sheep.setColor(DyeColor.PINK); // A fun color for the pinata
			sheep.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600, 0));
			sheep.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 1));

			world.spawnEntity(sheep);

			// Schedule the burst in 10 seconds (200 ticks)
			long currentTime = world.getTime();
			Gearworks.PINATA_BURSTS.put(sheep.getUuid(), currentTime + 200);
		}
	}

	public static void reverseGravityField(PlayerEntity player, ServerWorld world) {
		player.sendMessage(Text.literal("Gravity is reversed!"), false);

		// Radius in which entities are affected
		double radius = 10.0;

		// Duration of levitation in ticks (20 ticks = 1 second)
		int duration = 100; // 5 seconds of levitation
		// Amplifier 0 = Levitation I
		int amplifier = 0;

		// Define the bounding box around the player
		double x = player.getX();
		double y = player.getY();
		double z = player.getZ();
		Box box = new Box(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);

		// Find all living entities in the radius
		// This will include the player and other mobs
		world.getEntitiesByClass(LivingEntity.class, box, (entity) -> true)
				.forEach(entity -> {
					// Apply Levitation effect
					entity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, duration, amplifier));
				});
	}

	public static void spawnGhostParade(PlayerEntity player, ServerWorld world) {
		player.sendMessage(Text.literal("A ghostly presence fills the air..."), false);

		// Number of vex to spawn
		int vexCount = 5;
		// Radius around the player
		double radius = 5.0;
		// Duration before vex vanish (in ticks, 20 ticks = 1 second)
		int duration = 200; // 10 seconds

		double playerX = player.getX();
		double playerY = player.getY();
		double playerZ = player.getZ();

		Random random = world.getRandom();
		long currentTime = world.getTime();

		for (int i = 0; i < vexCount; i++) {
			double angle = 2 * Math.PI * i / vexCount;
			double offsetX = radius * Math.cos(angle);
			double offsetZ = radius * Math.sin(angle);

			double spawnX = playerX + offsetX;
			double spawnZ = playerZ + offsetZ;

			BlockPos groundPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos((int) spawnX, (int) playerY, (int) spawnZ));
			double spawnY = groundPos.getY() + 1; // slightly above ground

			VexEntity vex = EntityType.VEX.create(world);
			if (vex != null) {
				vex.refreshPositionAndAngles(spawnX, spawnY, spawnZ, world.random.nextFloat() * 360F, 0.0F);
				vex.setPersistent();

				// Make them ghostly: Invisibility + Glowing
				vex.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, duration, 0, false, false));
				vex.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, duration, 0, false, false));

				// Optional: Make them silent and give a spooky name
				vex.setSilent(true);
				vex.setCustomName(Text.literal("Ghostly Presence"));
				vex.setCustomNameVisible(false);

				world.spawnEntity(vex);

				// Schedule removal after 'duration' ticks
				Gearworks.SCHEDULED_VEX_DESPAWN.put(vex.getUuid(), currentTime + duration);
			}
		}
	}

	public static void spawnRainbowMule(PlayerEntity player, ServerWorld world) {
		player.sendMessage(Text.literal("You hear a distant neigh... 'Rocket' has arrived!"), false);

		// Random position within 10 blocks
		Random random = world.getRandom();
		double offsetX = (random.nextDouble() - 0.5) * 20; // -10 to +10
		double offsetZ = (random.nextDouble() - 0.5) * 20;
		double spawnX = player.getX() + offsetX;
		double spawnZ = player.getZ() + offsetZ;

		BlockPos groundPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos((int) spawnX, (int) player.getY(), (int) spawnZ));
		double spawnY = groundPos.getY();

		MuleEntity mule = EntityType.MULE.create(world);
		if (mule != null) {
			mule.refreshPositionAndAngles(spawnX, spawnY, spawnZ, world.random.nextFloat() * 360F, 0.0F);
			mule.setCustomName(Text.literal("Rocket"));
			mule.setCustomNameVisible(true);
			mule.setPersistent();

			mule.setTame(true);

			mule.equipStack(net.minecraft.entity.EquipmentSlot.CHEST, new ItemStack(Items.SADDLE));

			mule.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 1));
			mule.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 1));
			mule.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 600, 1));
			mule.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 1));
			mule.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 600, 0));

			world.spawnEntity(mule);
		}
	}

	public static void spawnBoatRain(PlayerEntity player, ServerWorld world) {
		player.sendMessage(Text.literal("Boats are falling from the sky!"), false);

		Random random = world.getRandom();

		int boatCount = 10; // number of boats to spawn
		double radius = 10.0; // horizontal spread
		int minHeight = 20; // minimum height above player
		int maxHeight = 30; // maximum height above player

		double playerX = player.getX();
		double playerY = player.getY();
		double playerZ = player.getZ();

		String playerName = player.getName().getString();

		for (int i = 0; i < boatCount; i++) {
			double offsetX = (random.nextDouble() - 0.5) * 2 * radius;
			double offsetZ = (random.nextDouble() - 0.5) * 2 * radius;
			double spawnX = playerX + offsetX;
			double spawnZ = playerZ + offsetZ;

			double spawnY = playerY + minHeight + random.nextInt(maxHeight - minHeight + 1);

			BoatEntity boat = EntityType.BOAT.create(world);
			if (boat != null) {
				boat.refreshPositionAndAngles(spawnX, spawnY, spawnZ, random.nextFloat() * 360F, 0.0F);
				boat.setCustomName(Text.literal(playerName));
				boat.setCustomNameVisible(true);

				world.spawnEntity(boat);
			}
		}
	}
}
