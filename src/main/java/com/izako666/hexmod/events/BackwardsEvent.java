package com.izako666.hexmod.events;

import com.izako666.hexmod.Main;
import com.izako666.hexmod.init.ModHexes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class BackwardsEvent {

	@SubscribeEvent
	public static void keyInput(InputUpdateEvent e) {
		
        ClientPlayerEntity p = Minecraft.getInstance().player;
		if(!p.isPotionActive(ModHexes.BACKWARDS_HEX))
		     return;
		

		e.getMovementInput().moveForward = -e.getMovementInput().moveForward;
		e.getMovementInput().moveStrafe = -e.getMovementInput().moveStrafe;
		if(e.getMovementInput().jump && e.getMovementInput().sneaking == false) {
			e.getMovementInput().sneaking = true;
			e.getMovementInput().jump = false;
		} else if(e.getMovementInput().sneaking) {
			e.getMovementInput().sneaking = false;
			e.getMovementInput().jump = true;
		}


	}
}
