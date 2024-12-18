package com.gearworkssmp.gearworks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CandyCane extends Block {
	public CandyCane() {
		super(AbstractBlock.Settings.copy(Blocks.STONE));
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
		tooltip.add(Text.of("Mostly sugar!"));
	}
}
