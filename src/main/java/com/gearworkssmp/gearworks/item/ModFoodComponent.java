package com.gearworkssmp.gearworks.item;

import java.util.random.RandomGenerator;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponent {
	private static final int MAX_LEVEL = 6;
	public static final FoodComponent COG_CUPCAKE = new FoodComponent.Builder().hunger(5).saturationModifier(0.25f)
			.statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 2400), 1.0f).build();
	public static final FoodComponent INCOMPLETE_COG_CUPCAKE = new FoodComponent.Builder()
			.statusEffect(new StatusEffectInstance(StatusEffects.POISON, 600), 0.8f).build();
	public static final FoodComponent TRICK_OR_TREAT_CANDY = new FoodComponent.Builder()
			.statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.BAD_OMEN, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.UNLUCK, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.statusEffect(new StatusEffectInstance(StatusEffects.WITHER, 600, RandomGenerator.getDefault().nextInt(MAX_LEVEL)), 0.1f)
			.build();
}
