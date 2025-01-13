package com.gearworkssmp.gearworks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DarkChocolate extends Block {
	public DarkChocolate() {
		super(Settings.copy(Blocks.STONE)
				.requiresTool()
				.strength(1.0F, 3.0F));
	}
}
