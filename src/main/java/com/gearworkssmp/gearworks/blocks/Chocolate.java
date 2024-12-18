package com.gearworkssmp.gearworks.blocks;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;

public class Chocolate extends Block {
	public Chocolate() {
		super(Settings.copy(Blocks.STONE));
	}
}
