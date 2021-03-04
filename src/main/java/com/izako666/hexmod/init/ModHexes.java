package com.izako666.hexmod.init;

import com.izako666.hexmod.Helper;
import com.izako666.hexmod.Main;
import com.izako666.hexmod.hexapi.HexEffect;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModHexes {

	public static final HexEffect SLEEP_HEX = new HexEffect(EffectType.HARMFUL, 637);
	public static final HexEffect STARVATION_HEX = new HexEffect(EffectType.HARMFUL,10569229);
	public static final HexEffect SICKNESS_HEX = new HexEffect(EffectType.HARMFUL,13697024);
	public static final HexEffect DARKNESS_HEX = new HexEffect(EffectType.HARMFUL,1576993);
	public static final HexEffect ANIMAL_FEAR_HEX = new HexEffect(EffectType.HARMFUL,6094955);
	public static final HexEffect FAKE_FEAR_HEX = new HexEffect(EffectType.HARMFUL, 684195);
	public static final HexEffect LOST_TUNG_HEX = new HexEffect(EffectType.HARMFUL, 3099140);
	public static final HexEffect BACKWARDS_HEX = new HexEffect(EffectType.HARMFUL,14739001);
	public static final HexEffect SLIPPERY_HANDS_HEX = new HexEffect(EffectType.HARMFUL, 11342719);
	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Effect> event) {
		event.getRegistry().registerAll(
				Helper.setup(SLEEP_HEX, "sleep_hex"),
				Helper.setup(STARVATION_HEX, "starvation_hex"),
				Helper.setup(SICKNESS_HEX, "sickness_hex"),
				Helper.setup(DARKNESS_HEX, "darkness_hex"),
				Helper.setup(ANIMAL_FEAR_HEX, "animal_fear_hex"),
				Helper.setup(FAKE_FEAR_HEX, "fake_fear_hex"),
				Helper.setup(LOST_TUNG_HEX, "lost_tung_hex"),
				Helper.setup(BACKWARDS_HEX, "backwards_hex"),
				Helper.setup(SLIPPERY_HANDS_HEX, "slippery_hands_hex")
				);
		
	}
	
	
}
