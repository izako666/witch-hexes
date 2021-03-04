package com.izako666.hexmod.events;

import java.awt.Color;
import java.util.List;
import java.util.UUID;

import com.izako666.hexmod.Main;
import com.izako666.hexmod.data.ModWorldData;
import com.izako666.hexmod.hexapi.HexEffect;
import com.izako666.hexmod.init.ModRecipes;
import com.izako666.hexmod.particles.GenericParticleData;
import com.izako666.hexmod.particles.SimpleParticle;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class RecipeEvents {

	@SubscribeEvent
	public static void itemToss(ItemTossEvent e) {
		e.getEntityItem().getItem().getOrCreateTag().putString("cauldronowner", e.getPlayer().getUniqueID().toString());
	}
	
	@SubscribeEvent 
	public static void update(LivingUpdateEvent e) {
		if(e.getEntityLiving() instanceof PlayerEntity) {
			if(e.getEntityLiving().ticksExisted % 20 == 0 && !e.getEntityLiving().world.isRemote()) {
				PlayerEntity p = (PlayerEntity) e.getEntityLiving();
				BlockPos pos1 = new BlockPos(p.getPositionVec()).add(-8, -8, -8);
				BlockPos pos2 = new BlockPos(p.getPositionVec()).add(8,8,8);
				List<ItemEntity> entities = p.world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos1,pos2));
			
				ModWorldData data = ModWorldData.get((ServerWorld) p.world);
				entities.forEach(itemEntity -> {
					if(!itemEntity.getItem().getOrCreateTag().getString("cauldronowner").contentEquals("")) {
						BlockPos entityPos = new BlockPos(itemEntity.getPositionVec());
						if(p.world.getBlockState(entityPos).getBlock().getBlock() instanceof CauldronBlock) {
							BlockState state = p.world.getBlockState(entityPos);
							CauldronBlock block = (CauldronBlock) p.world.getBlockState(entityPos).getBlock().getBlock();
							if(state.get(block.LEVEL) > 0) {
							if(data.putItem(entityPos, itemEntity.getItem().getItem())) {
								itemEntity.remove();
								Item[] items = data.getOrEmpty(entityPos);
								if(items[0] != null && items[1] != null && items[2] != null) {
									HexEffect hex = ModRecipes.getHexFromRecipe(items);
									if(hex != null) {
										for(int i = 0; i < 5; i++) {
											GenericParticleData gData = new GenericParticleData();
											gData.setTexture(new ResourceLocation(Main.MODID, "textures/particles/effect_2.png"));
											Color color = new Color(hex.getLiquidColor());
											gData.setColor(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
											gData.setMotion(0, 0.1, 0);
											double posX = entityPos.getX() + p.world.getRandom().nextDouble() - 0.5;
											double posY = entityPos.getY() + 1;
											double posZ = entityPos.getZ() + p.world.getRandom().nextDouble() - -0.5;
											SimpleParticle.spawnParticles(gData, (ServerWorld) p.world, posX, posY, posZ);
											
										}
									}
								}

							} else {
								PlayerEntity owner = p.world.getPlayerByUuid(UUID.fromString(itemEntity.getItem().getOrCreateTag().getString("cauldronowner")));
								
								ItemStack result = data.putCatalyst(entityPos,itemEntity.getItem(), owner);
							
								itemEntity.setItem(result);
								p.world.setBlockState(entityPos, state.with(CauldronBlock.LEVEL, 0));
								data.clear(entityPos);
							}
							}
						}
					}
				});
			
			}
		}
	}
	
	
	@SubscribeEvent
	public static void rightClick(PlayerInteractEvent.RightClickBlock e) {
		BlockState state = e.getWorld().getBlockState(e.getPos());
		if(state.getBlock().getBlock() instanceof CauldronBlock) {
			if(!e.getWorld().isRemote()) {
				ModWorldData data = ModWorldData.get((ServerWorld) e.getWorld());
			
				Item[] items = data.getOrEmpty(e.getPos());
				
				for(int i = 0; i < items.length; i++) {
					if(items[i] != null) {
						Block.spawnAsEntity(e.getWorld(), new BlockPos(e.getPlayer().getPositionVec()), new ItemStack(items[i]));
					}
				}
				
				data.clear(e.getPos());
				
			
			}
		}
		
	}
	@SubscribeEvent
	public static void breakBlock(BlockEvent.BreakEvent e) {
		if(!e.getWorld().isRemote()) {
			ModWorldData data = ModWorldData.get((ServerWorld) e.getWorld());
		
			data.clear(e.getPos());
		
		}
	}

}
