package com.izako666.hexmod.hexapi;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class HexEnchantment extends Enchantment{

	private HexEffect hex;
	public HexEnchantment(HexEffect hex) {
		super(Rarity.VERY_RARE, EnchantmentType.BREAKABLE, new EquipmentSlotType[] {});
		this.hex = hex;
	}
	
	public HexEffect getHex() {
		return this.hex;
	}

}
