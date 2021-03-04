package com.izako666.hexmod.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.izako666.hexmod.Main;
import com.izako666.hexmod.init.ModHexes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal.Flag;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SSpawnMobPacket;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class FakeFearEvent {

	public static List<EntityType<?>> MONSTERS = new ArrayList<>(Arrays.asList(EntityType.ZOMBIE,EntityType.BLAZE,EntityType.SKELETON,EntityType.CAVE_SPIDER,EntityType.SPIDER,EntityType.WITHER_SKELETON,EntityType.ENDERMAN,EntityType.CREEPER));
	
	@SubscribeEvent
	public static void update(LivingUpdateEvent e) {
		if(e.getEntityLiving() instanceof PlayerEntity && e.getEntityLiving().ticksExisted % 500 == 0 && e.getEntityLiving().isPotionActive(ModHexes.FAKE_FEAR_HEX)) {
			PlayerEntity p = (PlayerEntity) e.getEntityLiving();
			
			boolean showMob = p.world.getRandom().nextInt(10) > 6;
			if(showMob && !p.world.isRemote()) {
				MonsterEntity mon = (MonsterEntity) MONSTERS.get(p.world.getRandom().nextInt(MONSTERS.size())).create(p.world);
				mon.setPosition(p.getPosX() + (p.getLookVec().getX() * 10), p.getPosY(), p.getPosZ() + (p.getLookVec().getZ() * 10));
			
				mon.setRotationYawHead(p.rotationYawHead + 180);
				mon.goalSelector.disableFlag(Flag.JUMP);
				mon.goalSelector.disableFlag(Flag.LOOK);
				mon.goalSelector.disableFlag(Flag.MOVE);
				mon.goalSelector.disableFlag(Flag.TARGET);
				((ServerPlayerEntity)p).connection.getNetworkManager().sendPacket(new SSpawnMobPacket(mon));
			
			}
		}
	}
}
