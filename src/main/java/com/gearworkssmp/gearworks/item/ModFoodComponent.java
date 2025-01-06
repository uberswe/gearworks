package com.gearworkssmp.gearworks.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponent {
	public static final FoodComponent COG_CUPCAKE = new FoodComponent.Builder().hunger(5).saturationModifier(0.25f)
			.statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 2400, 3), 1.0f).build();
	public static final FoodComponent INCOMPLETE_COG_CUPCAKE = new FoodComponent.Builder()
			.statusEffect(new StatusEffectInstance(StatusEffects.POISON, 600), 0.8f).build();
	public static final FoodComponent TRICK_OR_TREAT_CANDY = new FoodComponent.Builder()
			.alwaysEdible()
			.build();
	public static final FoodComponent CANDY_CANE = new FoodComponent.Builder().hunger(3).saturationModifier(0.10f)
			.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 2400, 2), 1.0f).build();
	public static final FoodComponent EGGNOG = new FoodComponent.Builder().hunger(4).saturationModifier(0.20f)
			.statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 2400), 1.0f).build();
	public static final FoodComponent FROZEN_FRUIT_POPSICLE = new FoodComponent.Builder().hunger(2).saturationModifier(0.10f)
			.statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 2400, 2), 1.0f).build();
	public static final FoodComponent FRUITCAKE = new FoodComponent.Builder().hunger(10).saturationModifier(0.90f)
			.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 2400, 1), 1.0f).build();
	public static final FoodComponent GINGERBREAD_COG = new FoodComponent.Builder().hunger(2).saturationModifier(0.10f).build();
	public static final FoodComponent HOLIDAY_HAM = new FoodComponent.Builder().hunger(10).saturationModifier(0.95f).build();
	public static final FoodComponent MULLED_WINE = new FoodComponent.Builder().hunger(1).saturationModifier(0.25f).build();
	public static final FoodComponent ROASTED_CHESTNUTS = new FoodComponent.Builder().hunger(3).saturationModifier(0.35f).build();
	public static final FoodComponent SNOWBERRY_PIE = new FoodComponent.Builder().hunger(7).saturationModifier(0.95f)
			.statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 2400, 3), 1.0f).build();
	public static final FoodComponent WINTER_STEW = new FoodComponent.Builder().hunger(12).saturationModifier(0.75f).build();
}
