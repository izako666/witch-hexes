package com.izako666.hexmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.izako666.hexmod.init.ModRecipes;
import com.izako666.hexmod.networking.PacketHandler;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("hexmod")
public class Main
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "hexmod";
    public Main() {
    	PacketHandler.registerPackets();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    	ModRecipes.register();
    	
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

}
