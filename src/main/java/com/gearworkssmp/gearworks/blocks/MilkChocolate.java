package com.gearworkssmp.gearworks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class MilkChocolate extends Block {
	public MilkChocolate() {
		super(Settings.copy(Blocks.STONE)
				.requiresTool()
				.strength(1.0F, 3.0F));
	}
}
