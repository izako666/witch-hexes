package com.izako666.hexmod.events;

import com.izako666.hexmod.Main;
import com.izako666.hexmod.init.ModHexes;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class StarvationEvent {

	@SubscribeEvent
	public static void onRender(RenderGameOverlayEvent e) {
	
		PlayerEntity p = Minecraft.getInstance().player;
		if(p.isPotionActive(ModHexes.STARVATION_HEX) && e.getType() == ElementType.FOOD) {
			e.setCanceled(true);
		} else if(p.isPotionActive(ModHexes.SICKNESS_HEX) && e.getType() == ElementType.HEALTH) {
			e.setCanceled(true);
		}
	}
}
