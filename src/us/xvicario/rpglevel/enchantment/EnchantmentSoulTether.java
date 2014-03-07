package us.xvicario.rpglevel.enchantment;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import us.xvicario.rpglevel.RPGLevel;
import cpw.mods.fml.common.IPlayerTracker;

public class EnchantmentSoulTether extends Enchantment implements IPlayerTracker {
	
	public EnchantmentSoulTether(int par1, int par2) {
		super(par1, par2, EnumEnchantmentType.all);
		addToBookList(this);
		this.setName("soulTether");
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return super.canApplyAtEnchantingTable(stack);
	}

	public int getMinEnchantability() {
		return 20;
	}

	public int getMaxEnchantability() {
		return 50;
	}

	@Override
	public boolean canApply(ItemStack par1ItemStack) {
		return par1ItemStack.isItemStackDamageable() ? true : super.canApply(par1ItemStack);
	}

	@ForgeSubscribe
	public void onPlayerDeathDrops(LivingDropsEvent lde) {
		if ((lde.entityLiving instanceof EntityPlayer)) {
			ArrayList<EntityItem> tethered = new ArrayList<EntityItem>();
			Iterator<EntityItem> i = lde.drops.iterator();
			while (i.hasNext()) {
				EntityItem ei = i.next();
				if (EnchantmentHelper.getEnchantmentLevel(RPGLevel.enchantmentSoulTether.effectId, ei.getEntityItem()) > 0) {
					i.remove();
					tethered.add(ei);
				}
			}
			if (tethered.size() > 0) {
				EntityPlayer ep = (EntityPlayer) lde.entityLiving;
				NBTTagCompound persist = ep.getEntityData().getCompoundTag("PlayerPersisted");
				NBTTagList items = new NBTTagList("soulboundItems");
				for (EntityItem ei : tethered) {
					items.appendTag(ei.getEntityItem().writeToNBT(new NBTTagCompound()));
				}
				persist.setTag("soulboundItems", items);
				ep.getEntityData().setCompoundTag("PlayerPersisted", persist);
			}
		}
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
	}

	@Override
	public void onPlayerLogin(EntityPlayer player) {
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		//NBTTagCompound persist = player.getEntityData().getCompoundTag("PlayerPersisted");
		if ((!player.worldObj.isRemote) && (player.getEntityData().hasKey("PlayerPersisted")) && (player.getEntityData().getCompoundTag("PlayerPersisted").hasKey("soulboundItems"))) {
			NBTTagList list = (NBTTagList) player.getEntityData().getCompoundTag("PlayerPersisted").getTag("soulboundItems");
			for (int i = 0; i < list.tagCount(); i++) {
				player.inventory.addItemStackToInventory(ItemStack.loadItemStackFromNBT((NBTTagCompound) list.tagAt(i)));
			}
			player.getEntityData().getCompoundTag("PlayerPersisted").removeTag("soulboundItems");
		}
	}

}
