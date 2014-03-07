package us.xvicario.rpglevel.handler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.DimensionManager;
import us.xvicario.rpglevel.RPGLevel;
import us.xvicario.rpglevel.tileentity.TileEntityLevelUpStation;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
	private EntityPlayer player;

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		this.player = ((EntityPlayer) player);
		if (packet.channel.equals("RPGLEVEL")) {
			handlePacket(packet, player);
		}
	}

	private void handlePacket(Packet250CustomPayload packet, Player player) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int bookId = dis.readInt();
			int dimension = dis.readInt();
			int x = dis.readInt();
			int y = dis.readInt();
			int z = dis.readInt();

			TileEntityLevelUpStation te = (TileEntityLevelUpStation) DimensionManager.getWorld(dimension).getBlockTileEntity(x, y, z);
			ItemStack levelUpBook = new ItemStack(RPGLevel.itemLevelUpBook, 1, bookId);
			te.setInventorySlotContents(0, levelUpBook);

			this.player.addExperienceLevel(-RPGLevel.requiredLevels);
		} catch (IOException e) {
			RPGLevel.log.info("Failed to send last packet! This isn't good!");
		}
	}
}
