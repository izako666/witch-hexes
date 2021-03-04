package com.izako666.hexmod.init;

import com.izako666.hexmod.Helper;
import com.izako666.hexmod.Main;
import com.izako666.hexmod.hexapi.HexEnchantRecipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModRecipeSerializers {

	public static final SpecialRecipeSerializer<HexEnchantRecipe> HEX_ENCHANT_SERIALIZER = new SpecialRecipeSerializer<>(HexEnchantRecipe::new);
	@SubscribeEvent
	public static void registerSerializers(RegistryEvent.Register<IRecipeSerializer<?>> e) {
		
		e.getRegistry().register(Helper.setup(HEX_ENCHANT_SERIALIZER, "hex_enchant"));
	}
}
