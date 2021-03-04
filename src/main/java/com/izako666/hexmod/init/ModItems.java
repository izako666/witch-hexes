package com.izako666.hexmod.init;

import com.izako666.hexmod.Helper;
import com.izako666.hexmod.Main;
import com.izako666.hexmod.hexapi.BoneNeedleItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModItems {

	
	public static final Item BONE_NEEDLE = new BoneNeedleItem(new Item.Properties().group(ItemGroup.TOOLS));
	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> e) {
		e.getRegistry().registerAll(Helper.setup(BONE_NEEDLE, "bone_needle"));
	}
}
