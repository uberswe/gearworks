package com.gearworkssmp.gearworks.item;

import com.gearworkssmp.gearworks.Gearworks;

import com.gearworkssmp.gearworks.items.BetaHat;

import com.gearworkssmp.gearworks.items.CogCupcake;

import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

	public static final Item BETA_HAT = registerItem("beta_hat", new BetaHat(new FabricItemSettings()));
	public static final Item COG_CUPCAKE = registerItem("cog_cupcake", new CogCupcake(new FabricItemSettings().food(ModFoodComponent.COG_CUPCAKE)));

	private static void addItemsToItemGroup(FabricItemGroupEntries group) {
		group.add(BETA_HAT);
		group.add(COG_CUPCAKE);
	}

	private static Item registerItem(String name, Item item) {
		if (item instanceof TrinketRenderer) {
			TrinketRendererRegistry.registerRenderer(item, (TrinketRenderer) item);
		}
		return Registry.register(Registries.ITEM, new Identifier(Gearworks.ID, name), item);
	}

	public static void registerModItems() {
		Gearworks.LOGGER.info("Registering Mod Items for" + Gearworks.ID);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToItemGroup);
	};
}
