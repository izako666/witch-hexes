package com.izako666.hexmod.init;

import java.util.ArrayList;
import java.util.List;

import com.izako666.hexmod.Helper;
import com.izako666.hexmod.Main;
import com.izako666.hexmod.hexapi.HexEffect;
import com.izako666.hexmod.hexapi.HexEnchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEnchantments {

	public static final List<HexEnchantment> HEX_ENCHANTMENTS = new ArrayList<>();
	public static final Enchantment SLEEP_ENCH = new HexEnchantment(ModHexes.SLEEP_HEX);
	public static final Enchantment STARVATION_ENCH = new HexEnchantment(ModHexes.STARVATION_HEX);
	public static final Enchantment SICKNESS_ENCH = new HexEnchantment(ModHexes.SICKNESS_HEX);
    public static final Enchantment DARKNESS_ENCH = new HexEnchantment(ModHexes.DARKNESS_HEX);
	public static final Enchantment ANIMAL_FEAR_ENCH = new HexEnchantment(ModHexes.ANIMAL_FEAR_HEX);
    public static final Enchantment FAKE_FEAR_ENCH = new HexEnchantment(ModHexes.FAKE_FEAR_HEX);
    public static final Enchantment LOST_TUNG_ENCH = new HexEnchantment(ModHexes.LOST_TUNG_HEX);
	public static final Enchantment BACKWARDS_ENCH = new HexEnchantment(ModHexes.BACKWARDS_HEX);
	public static final Enchantment SLIPPERY_HANDS_ENCH = new HexEnchantment(ModHexes.SLIPPERY_HANDS_HEX);

	@SubscribeEvent
	public static void onRegisterEnchantments(RegistryEvent.Register<Enchantment> e) {
		e.getRegistry().registerAll(setup(SLEEP_ENCH, "sleep_ench"),
				setup(STARVATION_ENCH,"starvation_ench"),
				setup(SICKNESS_ENCH,"sickness_ench"),
				setup(DARKNESS_ENCH, "darkness_ench"),
				setup(ANIMAL_FEAR_ENCH, "animal_fear_ench"),
				setup(FAKE_FEAR_ENCH, "fake_fear_ench"),
				setup(LOST_TUNG_ENCH, "lost_tung_ench"),
				setup(BACKWARDS_ENCH, "backwards_ench"),
				setup(SLIPPERY_HANDS_ENCH, "slippery_hands_ench"));
	}

	public static Enchantment getEnchFromHex(HexEffect hex) {
		for (int i = 0; i < HEX_ENCHANTMENTS.size(); i++) {
			HexEnchantment ench = HEX_ENCHANTMENTS.get(i);
			if (ench.getHex() == hex) {
				return ench;
			}
		}
		return null;
	}

	public static Enchantment setup(Enchantment ench, String name) {
		HEX_ENCHANTMENTS.add((HexEnchantment) ench);
		return Helper.setup(ench, name);
	}

	public static boolean hasHexEnchantment(ItemStack stack) {
		for (int i = 0; i < HEX_ENCHANTMENTS.size(); i++) {
			if (EnchantmentHelper.getEnchantmentLevel(HEX_ENCHANTMENTS.get(i), stack) > 0) {
				return true;
			}
		}
		return false;
	}

	public static Enchantment getEnchFromBook(ItemStack stack) {
		for (int i = 0; i < HEX_ENCHANTMENTS.size(); i++) {
			if (EnchantmentHelper.getEnchantmentLevel(HEX_ENCHANTMENTS.get(i), stack) > 0) {
				return HEX_ENCHANTMENTS.get(i);
			}
		}

		return null;
	}
}
