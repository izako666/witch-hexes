package com.izako666.hexmod.hexapi;

import java.util.List;

import com.izako666.hexmod.init.ModHexes;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AnimalRunAwayGoal extends Goal{

	AnimalEntity animal;
	PlayerEntity nightmare;
	public AnimalRunAwayGoal(AnimalEntity animal) {
		super();
		this.animal = animal;
	}
	@Override
	public boolean shouldExecute() {
		World world = animal.world;
		BlockPos pos1 = new BlockPos(animal.getPositionVec()).add(-8, -8, -8);
		BlockPos pos2 = new BlockPos(animal.getPositionVec()).add(8, 8, 8);

		List<PlayerEntity> list = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(pos1,pos2));
		
		for(int i = 0; i < list.size(); i++) {
			PlayerEntity p = list.get(i);
			if(p.isPotionActive(ModHexes.ANIMAL_FEAR_HEX)) {
				this.nightmare = p;
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean shouldContinueExecuting() {
		return !this.animal.getNavigator().noPath();
	}

	@Override
	public void startExecuting() {
		if(this.nightmare != null) {
			double differenceX = this.animal.getPosX() - this.nightmare.getPosX();
			double differenceZ = this.animal.getPosZ() - this.nightmare.getPosZ();
			
			double posX = this.animal.getPosX() + (differenceX * 2);
			double posZ = this.animal.getPosZ() + (differenceZ * 2);
			this.animal.getNavigator().tryMoveToXYZ(posX, this.animal.getPosY(), posZ, 1.8f);
		}
	}


}
