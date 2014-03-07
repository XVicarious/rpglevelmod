/*  1:   */ package us.xvicario.rpglevel.handler;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.common.network.IGuiHandler;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.tileentity.TileEntity;
/*  6:   */ import net.minecraft.world.World;
import us.xvicario.rpglevel.client.gui.LevelUpStationContainer;
import us.xvicario.rpglevel.client.gui.LevelUpStationGUI;
import us.xvicario.rpglevel.tileentity.TileEntityLevelUpStation;
/*  9:   */ 
/* 10:   */ public class LevelUpGUIHandler
/* 11:   */   implements IGuiHandler
/* 12:   */ {
/* 13:   */   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
/* 14:   */   {
/* 15:15 */     TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
/* 16:16 */     if ((tileEntity instanceof TileEntityLevelUpStation)) {
/* 17:17 */       return new LevelUpStationGUI(player, player.inventory, (TileEntityLevelUpStation)tileEntity);
/* 18:   */     }
/* 19:19 */     return null;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
/* 23:   */   {
/* 24:25 */     TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
/* 25:26 */     if ((tileEntity instanceof TileEntityLevelUpStation)) {
/* 26:27 */       return new LevelUpStationContainer(player.inventory, (TileEntityLevelUpStation)tileEntity);
/* 27:   */     }
/* 28:29 */     return null;
/* 29:   */   }
/* 30:   */ }


/* Location:           E:\Users\XVicarious\bin\
 * Qualified Name:     us.xvicario.soultether.LevelUpGUIHandler
 * JD-Core Version:    0.7.0.1
 */