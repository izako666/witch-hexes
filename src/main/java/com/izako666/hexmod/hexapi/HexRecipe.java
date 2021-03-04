package com.izako666.hexmod.hexapi;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

public class HexRecipe {

	Item[] components = new Item[3];
	private HexEffect hex;
	public HexRecipe(Item item1, Item item2, Item item3, HexEffect hex) {
		components[0] = item1;
		components[1] = item2;
		components[2] = item3;
		this.hex = hex;
	}
	
	public HexRecipe(ItemStack stack1, ItemStack stack2, ItemStack stack3, HexEffect hex) {
		this(stack1.getItem(), stack2.getItem(), stack3.getItem(), hex);
	}
	public boolean test(Item[] items) {
		boolean returnVal = true;
		for(int i = 0; i < 3; i++) {
			Item item = components[i];
			if(items[0] == item || items[1] == item || items[2] == item) {
				continue;
			} else {
				returnVal = false;
				break;
			}
		}
		return returnVal;
	}
	
	
	public HexEffect getEffect() {
		return this.hex;
	}
}
