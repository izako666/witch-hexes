package com.izako666.hexmod.hexapi;

import java.util.function.Predicate;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class CustomEnchantmentType  {

	public static Predicate<Item> predicate = new Predicate<Item>() {
		@Override
		public boolean test(Item t) {
			return true;
		}
	  };
	 public static EnchantmentType ANY;
	  static {

	  try {
	 ANY = ObfuscationReflectionHelper.findConstructor(EnchantmentType.class, Predicate.class).newInstance(predicate);
	  } catch(Exception e) {
		  
	  };

	  }
}
