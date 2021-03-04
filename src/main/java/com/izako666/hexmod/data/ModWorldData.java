package com.izako666.hexmod.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.izako666.hexmod.Main;
import com.izako666.hexmod.hexapi.BoneNeedleItem;
import com.izako666.hexmod.hexapi.HexEffect;
import com.izako666.hexmod.init.ModEnchantments;
import com.izako666.hexmod.init.ModItems;
import com.izako666.hexmod.init.ModRecipes;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

public class ModWorldData extends WorldSavedData{
	/*
	  * DISCLAIMER
	  * this is a retarded way of doing what I'm doing
	  * I can't be bothered tho
	  */
	private static final String DATA_NAME = Main.MODID + "hexdata";
	public Map<BlockPos, Item[]> RECIPES_MANAGER = new HashMap<>();
	public ModWorldData() {
		super(DATA_NAME);
	}
 

	@Override
	public void read(CompoundNBT nbt) {
		Set<String> keySet = nbt.keySet();
		for(String key : keySet) {
			if(key.contains("blockpos")) {
				int[] posArray = nbt.getIntArray(key);
				String itemKey = "items" + key.substring("blockpos".length());
				int[] itemArray = nbt.getIntArray(itemKey);
				Item[] items = new Item[] {Item.getItemById(itemArray[0]), Item.getItemById(itemArray[1]), Item.getItemById(itemArray[2])};
				BlockPos pos = new BlockPos(posArray[0],posArray[1],posArray[2]);
				RECIPES_MANAGER.put(pos, items);
			}
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		BlockPos[] positions = this.RECIPES_MANAGER.keySet().toArray(new BlockPos[0]);
		
		for(int i = 0; i < positions.length; i++) {
			BlockPos pos = positions[i];
			Item[] items = this.RECIPES_MANAGER.get(pos);
			compound.putIntArray("blockpos" + String.valueOf(i), new int[] {pos.getX(),pos.getY(),pos.getZ()});
		    compound.putIntArray("items" + String.valueOf(i), new int[] {Item.getIdFromItem(items[0]), Item.getIdFromItem(items[1]), Item.getIdFromItem(items[2])});
		}
		return compound;
	}
	
	
	public static ModWorldData get(ServerWorld world) {
		return world.getSavedData().getOrCreate(ModWorldData::new, DATA_NAME);

	}
	
	public boolean putItem(BlockPos pos, Item item) {
		Item[] items = this.getOrEmpty(pos);
		if(this.addToEmpty(item, items)) {
		this.RECIPES_MANAGER.put(pos, items);
		this.markDirty();
		
		return true;
		} else {
			return false;
		}
	}
	



	public Item[] getOrEmpty(BlockPos pos) {
		Item[] test  = this.RECIPES_MANAGER.get(pos);
		if(test == null) {
			return new Item[3];
		}
		return test;
	}
	
	public boolean addToEmpty(Item item,Item[] items) {
		if(items[0] == null) {
			items[0] = item;
			return true;
		} else if(items[1] == null) {
			items[1] = item;
			return true;
		} else if(items[2] == null) {
			items[2] = item;
			return true;
		} else {
			return false;
		}
	}
	
	public ItemStack putCatalyst(BlockPos pos,ItemStack catalyst, PlayerEntity p) {
		Item[]  items = this.RECIPES_MANAGER.get(pos);
		HexEffect hex = ModRecipes.getHexFromRecipe(items);
		if(hex == null) {
			for(int i = 0; i<  items.length; i++) {
				if(items[i] != null) {
					Block.spawnAsEntity(p.world, pos, new ItemStack(items[i]));
				}
			}
			return catalyst;
		}
		if(catalyst.getItem() == Items.BOOK) {
			catalyst.getOrCreateTag().putString("hexowner", p.getUniqueID().toString());

			catalyst = new ItemStack(Items.ENCHANTED_BOOK);
			catalyst.addEnchantment(ModEnchantments.getEnchFromHex(hex), 1);
		} else if(catalyst.getItem() == ModItems.BONE_NEEDLE && BoneNeedleItem.getBloodOwner(p.world, catalyst) != null) {
			PlayerEntity toBeCursed = BoneNeedleItem.getBloodOwner(p.world, catalyst);
		
			p.sendMessage(new StringTextComponent(toBeCursed.getName().getString() + " has been cursed").func_240701_a_(TextFormatting.BLUE), p.getUniqueID());
	       toBeCursed.addPotionEffect(new EffectInstance(hex,Integer.MAX_VALUE,1,false,false));
		
	       catalyst.getOrCreateTag().remove("blood");
		}else {
			catalyst.getOrCreateTag().putString("hexowner",p.getUniqueID().toString());
		
			catalyst.addEnchantment(ModEnchantments.getEnchFromHex(hex), 1);
		}
		return catalyst;
	}
	
	
	public void clear(BlockPos pos) {
		this.RECIPES_MANAGER.remove(pos);
	}
}
