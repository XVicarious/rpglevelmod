package us.xvicario.rpglevel.client.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBook;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import us.xvicario.rpglevel.tileentity.TileEntityLevelUpStation;
import cpw.mods.fml.common.network.PacketDispatcher;

public class LevelUpStationGUI extends GuiContainer {
	public static final ResourceLocation textureGUI = new ResourceLocation("rpglevel", "textures/gui/levelUpGui.png");
	public TileEntityLevelUpStation levelUpStation;
	public static EntityPlayer player;
	private boolean flag = false;

	public LevelUpStationGUI(EntityPlayer player,
			InventoryPlayer inventoryPlayer, TileEntityLevelUpStation entity) {
		super(new LevelUpStationContainer(inventoryPlayer, entity));
		this.levelUpStation = entity;
		LevelUpStationGUI.player = player;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		String name = this.levelUpStation.isInvNameLocalized() ? this.levelUpStation.getInvName() : I18n.getString(this.levelUpStation.getInvName());
		this.fontRenderer.drawString(name, (this.xSize / 2) - (this.fontRenderer.getStringWidth(name) / 2), 6, 4210752);
		this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, (this.ySize - 96) + 2, 4210752);
		this.mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
		Icon itemIcon;
		itemIcon = Item.pickaxeDiamond.getIconFromDamage(0);
		drawTexturedModelRectFromIcon(10,18, itemIcon, 16, 16);
		itemIcon = Item.plateDiamond.getIconFromDamage(0);
		this.drawTexturedModelRectFromIcon(32, 18, itemIcon, 16, 16);
		itemIcon = Item.swordDiamond.getIconFromDamage(0);
		this.drawTexturedModelRectFromIcon(10, 41, itemIcon, 16, 16);
		itemIcon = Item.axeDiamond.getIconFromDamage(0);
		this.drawTexturedModelRectFromIcon(54, 18, itemIcon, 16, 16);
		itemIcon = Item.bow.getIconFromDamage(0);
		this.drawTexturedModelRectFromIcon(32, 41, itemIcon, 16, 16);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.renderEngine.bindTexture(textureGUI);
		this.buttonList.add(new GuiButton(0, this.guiLeft + 8, this.guiTop + 17, 20, 20, "")); //Mining
		this.buttonList.add(new GuiButton(1, this.guiLeft + 8, this.guiTop + 39, 20, 20, "")); //Attack
		this.buttonList.add(new GuiButton(2, this.guiLeft + 30, this.guiTop + 17, 20, 20, "")); //Defense
		this.buttonList.add(new GuiButton(3, this.guiLeft + 30, this.guiTop + 39, 20, 20, "")); //Archery
		this.buttonList.add(new GuiButton(4, this.guiLeft + 52, this.guiTop + 17, 20, 20, "")); //WoodCutting

		drawTexturedModalRect((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);

	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (flag == false) {
			if (this.levelUpStation != null) {
				if ((LevelUpStationGUI.player.experienceLevel >= 20) && (this.levelUpStation.getStackInSlot(0) != null)) {
					if (this.levelUpStation.getStackInSlot(0).getItem() instanceof ItemBook) {
						ByteArrayOutputStream bos1 = new ByteArrayOutputStream(5);
						DataOutputStream outputStream = new DataOutputStream(bos1);
						try {
							outputStream.writeInt(button.id);
							outputStream.writeInt(player.dimension);
							outputStream.writeInt(this.levelUpStation.xCoord);
							outputStream.writeInt(this.levelUpStation.yCoord);
							outputStream.writeInt(this.levelUpStation.zCoord);
						} catch (Exception e) {
							e.printStackTrace();
						}
						Packet250CustomPayload packet = new Packet250CustomPayload();
						packet.channel = "RPGLEVEL";
						packet.data = bos1.toByteArray();
						packet.length = bos1.size();
						PacketDispatcher.sendPacketToServer(packet);
					}
				}
			}
			flag = true;
		}
	}
}