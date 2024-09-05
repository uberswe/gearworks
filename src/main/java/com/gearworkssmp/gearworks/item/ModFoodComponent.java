package com.gearworkssmp.gearworks.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponent {
	public static final FoodComponent COG_CUPCAKE = new FoodComponent.Builder().hunger(3).saturationModifier(0.25f)
			.statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 2400), 1.0f).build();
	public static final FoodComponent INCOMPLETE_COG_CUPCAKE = new FoodComponent.Builder()
			.statusEffect(new StatusEffectInstance(StatusEffects.POISON, 600), 0.8f).build();
}
