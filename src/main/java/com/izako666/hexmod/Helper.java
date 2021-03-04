package com.izako666.hexmod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Helper {
	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
		entry.setRegistryName(new ResourceLocation(Main.MODID, name));
		return entry;
	}
	

}
