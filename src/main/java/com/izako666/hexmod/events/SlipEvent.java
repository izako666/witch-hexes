package com.izako666.hexmod.events;

import com.izako666.hexmod.Main;
import com.izako666.hexmod.init.ModHexes;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class SlipEvent {

	@SubscribeEvent
	public static void slip(LivingUpdateEvent e) {
		if(e.getEntityLiving() instanceof PlayerEntity && e.getEntityLiving().ticksExisted % 500 == 0 && e.getEntityLiving().isPotionActive(ModHexes.SLIPPERY_HANDS_HEX)) {
			boolean dropItem = e.getEntityLiving().world.getRandom().nextInt(10) > 5;
			if(dropItem) {
				PlayerEntity p = (PlayerEntity) e.getEntityLiving();
				Block.spawnAsEntity(p.world, new BlockPos(p.getPositionVec().add(1, 0, 1)), p.inventory.getCurrentItem());
				p.inventory.removeStackFromSlot(p.inventory.currentItem);
			}
		}

}
}
