package com.gearworkssmp.gearworks.item;

import com.gearworkssmp.gearworks.Gearworks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
	public static final Block GREEN_PRESENT_BLOCK = registerBlock("green_present", new Block(FabricBlockSettings.create().nonOpaque().strength(0.5F, 0.5F).sounds(BlockSoundGroup.WOOD)));
	public static final Block RED_PRESENT_BLOCK = registerBlock("red_present", new Block(FabricBlockSettings.create().nonOpaque().strength(0.5F, 0.5F).sounds(BlockSoundGroup.WOOD)));

	private static Block registerBlock(String name, Block block) {
		return Registry.register(Registries.BLOCK, new Identifier(Gearworks.ID, name), block);
	}
}
