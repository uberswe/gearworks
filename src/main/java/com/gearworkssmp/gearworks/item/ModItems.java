package com.gearworkssmp.gearworks.item;

import com.gearworkssmp.gearworks.Gearworks;
import com.gearworkssmp.gearworks.items.BetaHat;
import com.gearworkssmp.gearworks.items.CogCupcake;
import com.gearworkssmp.gearworks.items.DonutHat;
import com.gearworkssmp.gearworks.items.EchoingCore;
import com.gearworkssmp.gearworks.items.Ectoplasm;
import com.gearworkssmp.gearworks.items.EctoplasmTransitional;
import com.gearworkssmp.gearworks.items.EnderFragment;
import com.gearworkssmp.gearworks.items.IncompleteCogCupcake;
import com.gearworkssmp.gearworks.items.InfusedStew;
import com.gearworkssmp.gearworks.items.JackOLantern;
import com.gearworkssmp.gearworks.items.MegaMask;
import com.gearworkssmp.gearworks.items.NoBetaHat;
import com.gearworkssmp.gearworks.items.ShadowEssence;
import com.gearworkssmp.gearworks.items.TrickOrTreatCandy;

import com.gearworkssmp.gearworks.items.WitchHat;

import com.gearworkssmp.gearworks.items.WitchRobes;

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
	public static final Item NO_BETA_HAT = registerItem("no_beta_hat", new NoBetaHat(new FabricItemSettings()));
	public static final Item COG_CUPCAKE = registerItem("cog_cupcake", new CogCupcake(new FabricItemSettings().food(ModFoodComponent.COG_CUPCAKE)));
	public static final Item COG_CUPCAKE_STEP1 = registerItem("cog_cupcake_step1", new IncompleteCogCupcake(new FabricItemSettings().food(ModFoodComponent.INCOMPLETE_COG_CUPCAKE)));
	public static final Item COG_CUPCAKE_STEP2 = registerItem("cog_cupcake_step2", new IncompleteCogCupcake(new FabricItemSettings().food(ModFoodComponent.INCOMPLETE_COG_CUPCAKE)));
	public static final Item COG_CUPCAKE_STEP3 = registerItem("cog_cupcake_step3", new IncompleteCogCupcake(new FabricItemSettings().food(ModFoodComponent.INCOMPLETE_COG_CUPCAKE)));
	public static final Item ECHOING_CORE = registerItem("echoing_core", new EchoingCore(new FabricItemSettings()));
	public static final Item ECTOPLASM = registerItem("ectoplasm", new Ectoplasm(new FabricItemSettings()));
	public static final Item ECTOPLASM_TRANSITIONAL = registerItem("ectoplasm_transitional", new EctoplasmTransitional(new FabricItemSettings()));
	public static final Item ENDER_FRAGMENT = registerItem("ender_fragment", new EnderFragment(new FabricItemSettings()));
	public static final Item INFUSED_STEW = registerItem("infused_stew", new InfusedStew(new FabricItemSettings()));
	public static final Item JACK_O_LANTERN = registerItem("jack_o_lantern", new JackOLantern(new FabricItemSettings()));
	public static final Item SHADOW_ESSENCE = registerItem("shadow_essence", new ShadowEssence(new FabricItemSettings()));
	public static final Item TRICK_OR_TREAT_CANDY = registerItem("trick_or_treat_candy", new TrickOrTreatCandy(new FabricItemSettings().food(ModFoodComponent.TRICK_OR_TREAT_CANDY)));
	public static final Item MEGA_MASK = registerItem("mega_mask", new MegaMask(new FabricItemSettings()));
	public static final Item DONUT_HAT = registerItem("donut_hat", new DonutHat(new FabricItemSettings()));
	public static final Item WITCH_HAT = registerItem("witch_hat", new WitchHat(new FabricItemSettings()));
	public static final Item WITCH_ROBES = registerItem("witch_robes", new WitchRobes(new FabricItemSettings()));

	private static void addItemsToCombatItemGroup(FabricItemGroupEntries group) {
		group.add(BETA_HAT);
		group.add(NO_BETA_HAT);
		group.add(JACK_O_LANTERN);
		group.add(MEGA_MASK);
		group.add(DONUT_HAT);
		group.add(WITCH_HAT);
		group.add(WITCH_ROBES);
	}

	private static void addItemsToFoodItemGroup(FabricItemGroupEntries group) {
		group.add(COG_CUPCAKE);
		group.add(TRICK_OR_TREAT_CANDY);
	}

	private static void addItemsToIngredientsItemGroup(FabricItemGroupEntries group) {
		group.add(ECHOING_CORE);
		group.add(ECTOPLASM);
		group.add(ENDER_FRAGMENT);
		group.add(SHADOW_ESSENCE);
	}

	private static Item registerItem(String name, Item item) {
		if (item instanceof TrinketRenderer) {
			Gearworks.LOGGER.info("Registering renderer " + Gearworks.ID+":"+name);
			TrinketRendererRegistry.registerRenderer(item, (TrinketRenderer) item);
		}
		return Registry.register(Registries.ITEM, new Identifier(Gearworks.ID, name), item);
	}

	public static void registerModItems() {
		Gearworks.LOGGER.info("Registering Mod Items for " + Gearworks.ID);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToCombatItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::addItemsToFoodItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientsItemGroup);
	};
}
