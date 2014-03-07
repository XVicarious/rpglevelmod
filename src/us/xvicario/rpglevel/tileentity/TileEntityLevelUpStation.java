package us.xvicario.rpglevel.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLevelUpStation extends TileEntity implements IInventory {
	private ItemStack[] inv;

	public TileEntityLevelUpStation() {
		this.inv = new ItemStack[9];
	}

	@Override
	public void closeChest() {
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack stack = getStackInSlot(i);
		if (stack != null) {
			if (stack.stackSize <= j) {
				setInventorySlotContents(i, null);
			} else {
				stack = stack.splitStack(j);
				if (stack.stackSize == 0) {
					setInventorySlotContents(i, null);
				}
			}
		}
		return stack;
	}
	
	@Override
	public String getInvName() {
		return "rpglevel.levelUpStation";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public int getSizeInventory() {
		return this.inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.inv[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack stack = getStackInSlot(i);
		if (stack != null) {
			setInventorySlotContents(i, null);
		}
		return stack;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return !(itemstack.getItem() instanceof ItemBook);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this) && (entityplayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) < 64.0D);
	}

	@Override
	public void openChest() {
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.inv[i] = itemstack;
		if ((itemstack != null)
				&& (itemstack.stackSize > getInventoryStackLimit())) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Inventory");
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("Slot");
			if ((slot >= 0) && (slot < this.inv.length)) {
				this.inv[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < this.inv.length; i++) {
			ItemStack stack = this.inv[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
	}
}