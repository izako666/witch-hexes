package com.izako666.hexmod.init;

import java.util.ArrayList;
import java.util.List;

import com.izako666.hexmod.hexapi.HexEffect;
import com.izako666.hexmod.hexapi.HexRecipe;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ModRecipes {

	public static List<HexRecipe> RECIPES = new ArrayList<>();
	
	public static final HexRecipe SLEEP = new HexRecipe(Items.SPIDER_EYE, Items.HONEYCOMB, Items.REDSTONE, ModHexes.SLEEP_HEX);
    public static final HexRecipe STARVATION = new HexRecipe(Items.HONEYCOMB,Items.REDSTONE,Items.GUNPOWDER, ModHexes.STARVATION_HEX);
    public static final HexRecipe SICKNESS = new HexRecipe(Items.GUNPOWDER,Items.BLAZE_POWDER,Items.HONEYCOMB,ModHexes.SICKNESS_HEX);
    public static final HexRecipe DARKNESS = new HexRecipe(Items.REDSTONE,Items.GOLD_NUGGET,Items.ENDER_PEARL,ModHexes.DARKNESS_HEX);
	public static final HexRecipe ANIMAL_FEAR = new HexRecipe(Items.ENDER_PEARL,Items.REDSTONE,Items.BONE,ModHexes.ANIMAL_FEAR_HEX);
    public static final HexRecipe FAKE_FEAR = new HexRecipe(Items.BONE,Items.BLAZE_POWDER,Items.ENDER_PEARL,ModHexes.FAKE_FEAR_HEX);
    public static final HexRecipe LOST_TUNG = new HexRecipe(Items.HONEYCOMB,Items.GOLD_NUGGET,Items.MAGMA_CREAM,ModHexes.LOST_TUNG_HEX);
    public static final HexRecipe BACKWARDS = new HexRecipe(Items.SPIDER_EYE,Items.GLOWSTONE_DUST,Items.GUNPOWDER,ModHexes.BACKWARDS_HEX);
    public static final HexRecipe SLIPPERY_HANDS = new HexRecipe(Items.MAGMA_CREAM,Items.GLOWSTONE_DUST,Items.HONEYCOMB,ModHexes.SLIPPERY_HANDS_HEX);
    
    public static void register() {
		RECIPES.add(SLEEP);
		RECIPES.add(STARVATION);
		RECIPES.add(SICKNESS);
		RECIPES.add(DARKNESS);
		RECIPES.add(ANIMAL_FEAR);
		RECIPES.add(FAKE_FEAR);
		RECIPES.add(LOST_TUNG);
		RECIPES.add(BACKWARDS);
		RECIPES.add(SLIPPERY_HANDS);
	}

	public static HexEffect getHexFromRecipe(Item[] items) {
		for(int i = 0; i < RECIPES.size(); i++) {
			HexRecipe recipe = RECIPES.get(i);
			if(recipe.test(items)) {
				return recipe.getEffect();
			}
		}
		return null;

	}
}
