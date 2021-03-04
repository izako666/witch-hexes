package com.izako666.hexmod.events;

import java.util.UUID;

import com.izako666.hexmod.Main;
import com.izako666.hexmod.hexapi.BoneNeedleItem;
import com.izako666.hexmod.hexapi.HexEnchantment;
import com.izako666.hexmod.init.ModEnchantments;
import com.izako666.hexmod.init.ModItems;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class CatalystEvent {

	@SubscribeEvent
	public static void update(LivingUpdateEvent e) {
		if (e.getEntityLiving() instanceof PlayerEntity && e.getEntityLiving().ticksExisted % 20 == 0) {
			PlayerEntity p = (PlayerEntity) e.getEntityLiving();

			p.inventory.mainInventory.forEach(stack -> {
				if (!stack.getOrCreateTag().getString("hexowner").contentEquals("")) {
					UUID id = UUID.fromString(stack.getTag().getString("hexowner"));
					Effect effx = null;

					int index = -1;

					for (int i = 0; i < stack.getEnchantmentTagList().size(); i++) {
						CompoundNBT nbt = stack.getEnchantmentTagList().getCompound(i);
						ResourceLocation loc = ResourceLocation.tryCreate(nbt.getString("id"));
						Enchantment hex = ForgeRegistries.ENCHANTMENTS.getValue(loc);
						if (hex instanceof HexEnchantment) {
							effx = ((HexEnchantment) hex).getHex();
							index = i;
						}
					}

					if (stack.getItem() == ModItems.BONE_NEEDLE && BoneNeedleItem.getBloodOwner(p.world, stack) != null
							&& effx != null) {

						PlayerEntity bloodOwner = BoneNeedleItem.getBloodOwner(p.world, stack);
						bloodOwner.addPotionEffect(new EffectInstance(effx, Integer.MAX_VALUE, 1,false,false));

						stack.getTag().remove("hexowner");
						stack.getEnchantmentTagList().remove(index);
					} else if (stack.getItem() != Items.ENCHANTED_BOOK && id != p.getUniqueID() && effx != null) {
						p.addPotionEffect(new EffectInstance(effx, Integer.MAX_VALUE, 1,false,false));
						stack.getTag().remove("hexowner");
						stack.getEnchantmentTagList().remove(index);
					}
				}
			});

			p.inventory.offHandInventory.forEach(stack -> {
				if (!stack.getOrCreateTag().getString("hexowner").contentEquals("")) {
					UUID id = UUID.fromString(stack.getTag().getString("hexowner"));
					Effect effx = null;

					int index = -1;
					for (int i = 0; i < stack.getEnchantmentTagList().size(); i++) {
						CompoundNBT nbt = stack.getEnchantmentTagList().getCompound(i);
						ResourceLocation loc = ResourceLocation.tryCreate(nbt.getString("id"));
						Enchantment hex = ForgeRegistries.ENCHANTMENTS.getValue(loc);
						if (hex instanceof HexEnchantment) {
							effx = ((HexEnchantment) hex).getHex();
							index = i;
						}

					}
					if (stack.getItem() == ModItems.BONE_NEEDLE && BoneNeedleItem.getBloodOwner(p.world, stack) != null
							&& effx != null) {

						PlayerEntity bloodOwner = BoneNeedleItem.getBloodOwner(p.world, stack);
						bloodOwner.addPotionEffect(new EffectInstance(effx, Integer.MAX_VALUE, 1,false,false));

						stack.getTag().remove("hexowner");
						stack.getEnchantmentTagList().remove(index);
					} else if (stack.getItem() != Items.ENCHANTED_BOOK && id != p.getUniqueID() && effx != null) {
						p.addPotionEffect(new EffectInstance(effx, Integer.MAX_VALUE, 1,false,false));
						stack.getTag().remove("hexowner");

						stack.getEnchantmentTagList().remove(index);
					}
				}
			});
		}
	}

	@SubscribeEvent
	public static void interact(EntityInteractSpecific e) {
		ItemStack stack = e.getItemStack();
		if (!stack.isEmpty() && stack.getItem() == Items.ENCHANTED_BOOK && ModEnchantments.hasHexEnchantment(stack)
				&& e.getEntityLiving() instanceof PlayerEntity) {
			e.getEntityLiving().addPotionEffect(new EffectInstance(
					((HexEnchantment) ModEnchantments.getEnchFromBook(stack)).getHex(), Integer.MAX_VALUE, 1,false,false));
		}
	}

}
