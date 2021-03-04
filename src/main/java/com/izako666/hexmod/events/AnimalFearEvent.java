package com.izako666.hexmod.events;

import com.izako666.hexmod.Main;
import com.izako666.hexmod.hexapi.AnimalRunAwayGoal;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class AnimalFearEvent {

	@SubscribeEvent
	public static void construct(EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof AnimalEntity) {
			AnimalEntity animal = (AnimalEntity) event.getEntity();
			animal.goalSelector.addGoal(0, new AnimalRunAwayGoal(animal));
		}
	}
}
