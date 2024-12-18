package com.gearworkssmp.gearworks.item;

import com.gearworkssmp.gearworks.Gearworks;

import com.gearworkssmp.gearworks.blocks.CandyCane;

import com.gearworkssmp.gearworks.blocks.Chocolate;

import com.gearworkssmp.gearworks.blocks.DarkChocolate;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
	public static final Block GREEN_PRESENT_BLOCK = registerBlock("green_present", new Block(FabricBlockSettings.create().nonOpaque().strength(0.5F, 0.5F).sounds(BlockSoundGroup.WOOD)));
	public static final Block RED_PRESENT_BLOCK = registerBlock("red_present", new Block(FabricBlockSettings.create().nonOpaque().strength(0.5F, 0.5F).sounds(BlockSoundGroup.WOOD)));
	public static final Block CANDY_CANE_BLOCK = registerBlock("candy_cane_block", new CandyCane());
	public static final Block CHOCOLATE_BLOCK = registerBlock("chocolate_block", new Chocolate());
	public static final Block DARK_CHOCOLATE_BLOCK = registerBlock("dark_chocolate_block", new DarkChocolate());
	public static final Block MILK_CHOCOLATE_BLOCK = registerBlock("milk_chocolate_block", new DarkChocolate());

	private static Block registerBlock(String name, Block block) {
		return Registry.register(Registries.BLOCK, new Identifier(Gearworks.ID, name), block);
	}
}
