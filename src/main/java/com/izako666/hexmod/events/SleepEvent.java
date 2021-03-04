package com.izako666.hexmod.events;

import com.izako666.hexmod.Main;
import com.izako666.hexmod.init.ModHexes;

import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class SleepEvent {

	@SubscribeEvent
	public static void sleep(SleepingLocationCheckEvent e) {
		if(e.getEntityLiving().getActivePotionEffect(ModHexes.SLEEP_HEX) != null) {
			e.setResult(Result.DENY);
		} else {
			e.setResult(Result.DEFAULT);
		}
	}
}
