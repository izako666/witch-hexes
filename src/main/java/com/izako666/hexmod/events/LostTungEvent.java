package com.izako666.hexmod.events;

import com.izako666.hexmod.Main;
import com.izako666.hexmod.init.ModHexes;

import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class LostTungEvent {

	public static char[] chars = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	@SubscribeEvent
	public static void sendMessage(ServerChatEvent e) {
		if (e.getPlayer().isPotionActive(ModHexes.LOST_TUNG_HEX) && !(e.getMessage().charAt(0) == '/')) {
			String oldText = e.getMessage();

			String newText = "";
			for(int i = 0; i < oldText.length(); i++) {
				String str = String.valueOf(chars[e.getPlayer().world.getRandom().nextInt(chars.length)]);
				newText = newText.concat(str);
			}
			e.setComponent(new StringTextComponent(newText));
		}
	}
}
