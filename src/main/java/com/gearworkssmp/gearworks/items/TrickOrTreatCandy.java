package com.gearworkssmp.gearworks.items;

import com.gearworkssmp.gearworks.items.actions.TrickOrTreatActions;
import com.gearworkssmp.gearworks.items.actions.WeightedAction;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TrickOrTreatCandy extends Item {

	private static final WeightedAction[] ACTIONS = {
			new WeightedAction(TrickOrTreatActions::spawnAnimalsInCircle, 1.0f),
			new WeightedAction(TrickOrTreatActions::spawnSlimeTower, 0.5f),
			new WeightedAction(TrickOrTreatActions::spawnCreeperRain, 0.1f),
			new WeightedAction(TrickOrTreatActions::spawnRainbowSheep, 1.0f),
			new WeightedAction(TrickOrTreatActions::applyPositiveEffects, 1.0f),
			new WeightedAction(TrickOrTreatActions::applyNegativeEffects, 0.5f),
			new WeightedAction(TrickOrTreatActions::applyRandomScale, 3.0f),
			new WeightedAction(TrickOrTreatActions::spawnFireworkFrenzy, 1.0f),
			new WeightedAction(TrickOrTreatActions::spawnLivingLootPinata, 1.0f),
			new WeightedAction(TrickOrTreatActions::reverseGravityField, 1.0f),
			new WeightedAction(TrickOrTreatActions::spawnGhostParade, 1.0f),
			new WeightedAction(TrickOrTreatActions::spawnRainbowMule, 0.3f),
			new WeightedAction(TrickOrTreatActions::spawnBoatRain, 0.5f),
	};
	private WeightedAction pickRandomAction(PlayerEntity player) {
		// Calculate total weight
		float totalWeight = 0.0f;
		for (WeightedAction wa : ACTIONS) {
			totalWeight += wa.weight;
		}

		// Pick a random number up to totalWeight
		float choice = player.getRandom().nextFloat() * totalWeight;

		// Find which action corresponds to this choice
		float cumulative = 0.0f;
		for (WeightedAction wa : ACTIONS) {
			cumulative += wa.weight;
			if (choice <= cumulative) {
				return wa;
			}
		}

		// Fallback (should never happen if weights > 0)
		return ACTIONS[ACTIONS.length - 1];
	}

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

		if (!world.isClient && user instanceof PlayerEntity player) {
			WeightedAction chosen = pickRandomAction(player);
			chosen.action.accept(player, (ServerWorld) world);
		}

		return itemStack;
	}

}
