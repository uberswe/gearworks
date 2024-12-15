package com.gearworkssmp.gearworks.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfusedStew extends Item {

	public InfusedStew(Settings settings) {
		super(settings
				// Make the item edible by giving it a FoodComponent:
				.food(new FoodComponent.Builder().hunger(6).saturationModifier(0.6F).alwaysEdible().build())
		);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("item.gearworks.infused_stew.tooltip"));
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		// Call super to handle the standard food-consumption logic (item decrement, etc.)
		ItemStack result = super.finishUsing(stack, world, user);

		// Only create an explosion on the server side
		if (!world.isClient()) {
			// Create an explosion at the player's position
			// Parameters: (exploder, x, y, z, power, destructionType)
			// power ~4.0F mimics TNT
			// DestructionType.NONE means no block damage
			world.createExplosion(null, user.getX(), user.getY(), user.getZ(), 4.0F, World.ExplosionSourceType.NONE);
		}

		return result;
	}
}
