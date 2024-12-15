package com.gearworkssmp.gearworks.mixin;

import com.tom.storagemod.gui.CraftingTerminalMenu;
import com.tom.storagemod.tile.CraftingTerminalBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(value = CraftingTerminalMenu.class, remap = false)
public abstract class CraftingTerminalMenuMixin {
	@Shadow private CraftingTerminalBlockEntity te;
	@Shadow private ResultContainer craftResult;
	@Shadow protected abstract boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection);
	@Shadow public abstract void onCraftMatrixChanged();
	@Shadow protected abstract void broadcastChanges();
	@Shadow protected List<Slot> slots;

	@Overwrite
	public ItemStack shiftClickItems(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);

		if (slot != null && slot.hasItem()) {
			ItemStack originalStack = slot.getItem();
			itemstack = originalStack.copy();

			// If we're clicking the crafting result slot
			if (index == 0) {
				if (te == null) return ItemStack.EMPTY;

				// Loop to craft as many items as possible
				while (te.canCraft()) {
					// Get the current crafting result
					ItemStack craftedItem = craftResult.getItem(0).copy();
					// Attempt to move the crafted item into player's inventory
					if (!this.moveItemStackTo(craftedItem, 10, 46, true)) {
						// Can't move the item, stop crafting
						break;
					}

					// Consume ingredients and update the crafting grid
					te.craft(playerIn);
					// Trigger slot onTake to handle any vanilla logic (exp gain, etc.)
					slot.onTake(playerIn, craftedItem);
					// Update the crafting result for the next iteration
					this.onCraftMatrixChanged();
				}

				// Return the original itemstack (the item before crafting)
				return itemstack;
			}
			// If we're clicking inside the crafting grid (indexes 1-9)
			else if (index > 0 && index < 10) {
				if (te == null) return ItemStack.EMPTY;

				// Move the item from the crafting grid into the storage network
				ItemStack stack = te.pushStack(itemstack);
				slot.set(stack);

				if (!playerIn.level().isClientSide) {
					broadcastChanges();
				}
			}

			// Ensure the slot finalizes the take
			slot.onTake(playerIn, originalStack);
		}

		return ItemStack.EMPTY;
	}
}
