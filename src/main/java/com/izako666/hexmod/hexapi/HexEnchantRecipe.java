package com.izako666.hexmod.hexapi;

import com.izako666.hexmod.init.ModEnchantments;
import com.izako666.hexmod.init.ModRecipeSerializers;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class HexEnchantRecipe extends SpecialRecipe{

	public HexEnchantRecipe(ResourceLocation idIn) {
		super(idIn);
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn) {
		int nonEmptyCount = 0;
		ItemStack enchantedBook = ItemStack.EMPTY;
		ItemStack toEnchant = ItemStack.EMPTY;
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if(stack.isEmpty())
				continue;
			
			if(!stack.isEmpty() && stack.getItem() == Items.ENCHANTED_BOOK && ModEnchantments.hasHexEnchantment(stack)) {
				enchantedBook = stack;
				nonEmptyCount++;
			} else if(!stack.isEmpty()) {
				toEnchant = stack;
				nonEmptyCount++;
			}
		}
		
		if(nonEmptyCount == 2 && !enchantedBook.isEmpty() && !toEnchant.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv) {
		ItemStack enchantedBook = ItemStack.EMPTY;
		ItemStack toEnchant = ItemStack.EMPTY;
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if(stack.isEmpty())
				continue;
			
			if(!stack.isEmpty() && stack.getItem() == Items.ENCHANTED_BOOK && ModEnchantments.hasHexEnchantment(stack)) {
				enchantedBook = stack;
			} else if(!stack.isEmpty()) {
				toEnchant = stack;
			}
		}
		
		if(!enchantedBook.isEmpty() && !toEnchant.isEmpty()) {
			ItemStack stack = toEnchant.copy();
			stack.getOrCreateTag().putString("hexowner", enchantedBook.getTag().getString("hexowner"));
			stack.addEnchantment(ModEnchantments.getEnchFromBook(enchantedBook), 1);
		return stack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.HEX_ENCHANT_SERIALIZER;
	}

}
