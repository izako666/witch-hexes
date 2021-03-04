package com.izako666.hexmod.hexapi;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class BoneNeedleItem extends Item {

	public BoneNeedleItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target,
			Hand hand) {
		
		if(target instanceof PlayerEntity && !stack.getOrCreateTag().hasUniqueId("blood")) {
			PlayerEntity p = (PlayerEntity) target;
			p.attackEntityFrom(DamageSource.GENERIC, 2.0f);
			stack.getOrCreateTag().putUniqueId("blood", p.getUniqueID());
		}
		return super.itemInteractionForEntity(stack, playerIn, target, hand);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if(stack.getOrCreateTag().hasUniqueId("blood")) {
		String name = worldIn.getPlayerByUuid(stack.getTag().getUniqueId("blood")).getName().getString();
		if(name != null) {
		tooltip.add(new StringTextComponent("blood owner: " + name));
		}
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	public static PlayerEntity getBloodOwner(World world,ItemStack stack) {
		if(stack.getOrCreateTag().hasUniqueId("blood")) {
		UUID uuid = stack.getOrCreateTag().getUniqueId("blood");
		return world.getPlayerByUuid(uuid);
		}
		return null;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

		if(playerIn.isSneaking() && !playerIn.getHeldItem(handIn).getOrCreateTag().hasUniqueId("blood")) {
			playerIn.attackEntityFrom(DamageSource.GENERIC, 2.0f);
			playerIn.getHeldItem(handIn).getOrCreateTag().putUniqueId("blood", playerIn.getUniqueID());

		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	
	
}
