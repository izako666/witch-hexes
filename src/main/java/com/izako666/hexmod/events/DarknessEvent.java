package com.izako666.hexmod.events;

import com.izako666.hexmod.Main;
import com.izako666.hexmod.init.ModHexes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class DarknessEvent {

	@SubscribeEvent
	public static void update(LivingUpdateEvent e) {
		if(e.getEntityLiving() instanceof PlayerEntity && e.getEntityLiving().isPotionActive(ModHexes.DARKNESS_HEX) && e.getEntityLiving().ticksExisted % 1000 == 0) {
			boolean giveBlindness = e.getEntityLiving().world.getRandom().nextInt(10) > 6;
			int extraTicks = e.getEntityLiving().world.getRandom().nextInt(200);
				if(giveBlindness) {
					e.getEntityLiving().addPotionEffect(new EffectInstance(Effects.BLINDNESS, 40 + extraTicks, 1));
				}
		}
	}
}
