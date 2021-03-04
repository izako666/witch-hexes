package com.izako666.hexmod.init;

import com.izako666.hexmod.Main;
import com.izako666.hexmod.particles.GenericParticleData;
import com.izako666.hexmod.particles.SimpleParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticleTypes
{
	public static final ParticleType<GenericParticleData> GENERIC_PARTICLE = new GenericParticleData();

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particles.registerFactory(ModParticleTypes.GENERIC_PARTICLE,
				new SimpleParticle.Factory());
	}
	
	@SubscribeEvent
	public static void onRegisterParticleTypes(RegistryEvent.Register<ParticleType<?>> event) {
		GENERIC_PARTICLE.setRegistryName(Main.MODID, "generic_particle");
		event.getRegistry().register(GENERIC_PARTICLE);
	}
	}