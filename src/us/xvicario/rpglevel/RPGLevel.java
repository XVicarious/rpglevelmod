package us.xvicario.rpglevel;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import us.xvicario.rpglevel.block.BlockEmeraldEncrustedObsidian;
import us.xvicario.rpglevel.block.BlockLevelUpStation;
import us.xvicario.rpglevel.enchantment.EnchantmentSoulTether;
import us.xvicario.rpglevel.handler.LevelUpGUIHandler;
import us.xvicario.rpglevel.handler.PacketHandler;
import us.xvicario.rpglevel.handler.RPGLevelEventHandler;
import us.xvicario.rpglevel.item.ItemLevelUpBook;
import us.xvicario.rpglevel.tileentity.TileEntityLevelUpStation;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "rpglevel", name = "RPGLevel", version = "0.5.2")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { "RPGLEVEL" }, packetHandler = PacketHandler.class)
public class RPGLevel {
	@Mod.Instance("rpglevel")
	public static RPGLevel instance;
	public static RPGLevelEventHandler itemToolBoost;
	public static Item itemLevelUpBook;
	public static Block blockEmeraldEncrustedObsidian;
	public static Block levelUpStation;
	public static int enchantmentSoulTetherID;
	public static int blockEEOID;
	public static int blockLUSID;
	public static int itemLUPID;
	public static double levelMultiplier;
	public static int requiredLevels;
	public static EnchantmentSoulTether enchantmentSoulTether;
	public static final int guiID = 0;
	public static Logger log;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		log = event.getModLog();
		log.setParent(FMLLog.getLogger());
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		enchantmentSoulTetherID = config.get("enchantment", "EnchantmentSoulTether", 52).getInt();
		blockEEOID = config.getBlock("emeraldEncrustedObsidian", 500).getInt();
		blockLUSID = config.getBlock("levelUpStation", 501).getInt();
		itemLUPID = config.getItem("levelUpBook", 5000).getInt();
		// Stuff
		levelMultiplier = config.get("RPGLevel", "levelMultiplier", 0.1, "Multiply this with your level to get a boost.").getDouble(0.1);
		requiredLevels = config.get("RPGLevel", "requiredLevels", 20, "How many levels it costs to make a book").getInt();
		// End Stuff
		config.save();
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		registerEnchantment();
		itemToolBoost = new RPGLevelEventHandler();
		itemLevelUpBook = new ItemLevelUpBook(itemLUPID).setTextureName("rpglevel:levelBook");
		NetworkRegistry.instance().registerGuiHandler(instance, new LevelUpGUIHandler());
		MinecraftForge.EVENT_BUS.register(itemToolBoost);
		registerBlocks();
		//registerTileEntity();
		registerLanguage();
		registerRecipes();
	}
	
	@Mod.EventHandler public void serverStart(FMLServerStartingEvent event) {
		event.registerServerCommand(new CheckLevelsCommand());
	}

	public void registerLanguage() {
		log.info("Registering to the LanguageRegistry...");
		LanguageRegistry.instance().addStringLocalization(enchantmentSoulTether.getName(), "en_US", "Soul Tether");
		LanguageRegistry.addName(blockEmeraldEncrustedObsidian, "Emerald Encrusted Obsidian");
		LanguageRegistry.addName(new ItemStack(itemLevelUpBook, 1, 0), "Mining Skill Book");
		LanguageRegistry.addName(new ItemStack(itemLevelUpBook, 1, 1), "Attack Skill Book");
		LanguageRegistry.addName(new ItemStack(itemLevelUpBook, 1, 2), "Defence Skill Book");
		LanguageRegistry.addName(new ItemStack(itemLevelUpBook, 1, 3), "Archery Skill Book");
		LanguageRegistry.addName(new ItemStack(itemLevelUpBook, 1, 4), "Woodcutting Skill Book");
		LanguageRegistry.addName(levelUpStation, "Level Up Station");
		log.info("Registered!");

	}

	public void registerRecipes() {
		ItemStack bookStack = new ItemStack(Item.book);
		ItemStack encrustedObsidianStack = new ItemStack(blockEmeraldEncrustedObsidian);
		ItemStack emeraldStack = new ItemStack(Item.emerald);
		ItemStack obsidianStack = new ItemStack(Block.obsidian);
		log.info("Creating Recipies...");
		GameRegistry.addRecipe(new ItemStack(blockEmeraldEncrustedObsidian, 4), new Object[] { " e ", "eoe", " e ", Character.valueOf('e'), emeraldStack, Character.valueOf('o'), obsidianStack });
		GameRegistry.addRecipe(new ItemStack(levelUpStation), new Object[] {" b ", "eoe", "ooo", Character.valueOf('b'), bookStack, Character.valueOf('e'), emeraldStack, Character.valueOf('o'), encrustedObsidianStack});
		log.info("Recipies created!");
	}
	
	public void registerBlocks() {
		log.info("Time to register the blocks...");
		blockEmeraldEncrustedObsidian = new BlockEmeraldEncrustedObsidian(blockEEOID, Material.rock).setTextureName("rpglevel:emeraldEncrustedObsidian");
		MinecraftForge.setBlockHarvestLevel(blockEmeraldEncrustedObsidian, "pickaxe", 3);
		GameRegistry.registerBlock(blockEmeraldEncrustedObsidian, "emeraldEncrustedObsidian");
		levelUpStation = new BlockLevelUpStation(blockLUSID, Material.rock, false);
		GameRegistry.registerBlock(levelUpStation, "levelUpStation");
		log.info("Blocks registered!");
	}
	
	public void registerTileEntity() {
		GameRegistry.registerTileEntity(TileEntityLevelUpStation.class, "rpglevel.levelUpStation");
	}
	
	public void registerEnchantment() {
		enchantmentSoulTether = new EnchantmentSoulTether(enchantmentSoulTetherID, 1);
		MinecraftForge.EVENT_BUS.register(enchantmentSoulTether);
		GameRegistry.registerPlayerTracker(enchantmentSoulTether);
	}
	
}
