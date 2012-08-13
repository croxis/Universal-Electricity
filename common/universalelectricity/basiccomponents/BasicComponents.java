package universalelectricity.basiccomponents;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraftforge.oredict.OreDictionary;
import universalelectricity.UniversalElectricity;
import universalelectricity.electricity.ElectricityManager;
import universalelectricity.network.PacketManager;
import universalelectricity.recipe.UERecipeManager;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.BaseMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;

/**
 * A static class where you can reference variables from here
 * @author Calclavia
 *
 */

@Mod(modid = "UniversalElectricity", name = "Universal Electricity", version = UniversalElectricity.version)
@BaseMod(channels = { "BasicComponents" }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketManager.class)

public class BasicComponents implements IWorldGenerator
{
    public static final String FILE_PATH = "/basiccomponents/textures/";
    public static final String blockTextureFile = FILE_PATH + "blocks.png";
    
    @Instance
    public static BasicComponents instance;
    
    @SidedProxy(clientSide = "universalelectricity.basiccomponents.BCClientProxy", serverSide = "universalelectricity.basiccomponents.BCCommonProxy")
	public static BCCommonProxy proxy;
    
    /**
     * Here is where all the Universal Components are defined. You may reference to these variables.
     */
    public static final int BLOCK_ID_PREFIX = 3970;
    public static final Block blockOre = new BlockBCOre(UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Copper and Tin Ores", BLOCK_ID_PREFIX-1, true));
    public static final Block blockCopperWire = new BlockCopperWire(UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Copper_Wire", BLOCK_ID_PREFIX, true));
    public static final Block blockBatteryBox = new BlockBatteryBox(UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Battery_Box", BLOCK_ID_PREFIX + 1, true), 0);
    public static final Block blockCoalGenerator = new BlockCoalGenerator(UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Coal_Generator", BLOCK_ID_PREFIX + 2, true), 0);
    public static final Block blockElectricFurnace = new BlockElectricFurnace(UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Electric_Furnace", BLOCK_ID_PREFIX + 3, true), 0);

    public static final Item itemBattery = new ItemBattery(UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Battery", 1586, false), 0);
    public static final Item itemWrench = new ItemWrench(UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Wrench", 1587, false), 20);
    public static final Item itemCopperIngot = new BCItem("Copper Ingot", UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Copper_Ingot", 1588, false), 1);
    public static final Item itemTinIngot = new BCItem("Tin Ingot", UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Tin_Ingot", 1589, false), 2);
    public static final Item itemSteelIngot = new BCItem("Steel Ingot", UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Steel_Ingot", 1590, false), 3);
    public static final Item itemSteelClump = new BCItem("Steel Alloy", UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Steel_Clump", 1591, false), 5);
    public static final Item itemCircuit = new ItemCircuit(UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Circuit", 1592, false), 16);
    public static final Item itemCopperWire = new ItemCopperWire(UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Item_Copper_Wire", 1593, false), 21);
    public static final Item itemBronzeIngot = new BCItem("Bronze Ingot", UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Bronze_Ingot", 1594, false), 7);
    public static final Item itemBronzeClump = new BCItem("Bronze Alloy", UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Bronze_Clump", 1595, false), 6);
    public static final Item itemSteelPlate = new BCItem("Steel Plate", UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Steel_Plate", 1597, false), 8);
    public static final Item itemMotor = new BCItem("Motor", UniversalElectricity.getConfigID(UniversalElectricity.CONFIGURATION, "Motor", 1598, false), 9);

    @PreInit
	public void preInit(FMLPreInitializationEvent event)
    {    	
		instance = this;
		
		UniversalElectricity.registerMod(this, "Basic Componenets", UniversalElectricity.getVersion());

    	//MinecraftForge.versionDetect("Universal Electricity", 3, 3, 8);
		NetworkRegistry.instance().registerGuiHandler(this, this.proxy);
		GameRegistry.registerWorldGenerator(this);
    	TickRegistry.registerTickHandler(new ElectricityManager(), Side.SERVER);
		
		proxy.preInit();
    }
    
    @Init
	public void load(FMLInitializationEvent evt)
    {
    	proxy.init();
    	
		//Register Blocks
    	GameRegistry.registerBlock(blockOre, ItemBCOre.class);
		GameRegistry.registerBlock(BasicComponents.blockCopperWire);
		GameRegistry.registerBlock(BasicComponents.blockBatteryBox);
		GameRegistry.registerBlock(BasicComponents.blockCoalGenerator);
		GameRegistry.registerBlock(BasicComponents.blockElectricFurnace);
		
		//Add Names for Special Items
		ModLoader.addName(BasicComponents.itemBattery, "Basic Battery");
		ModLoader.addName(BasicComponents.blockCopperWire, "Copper Wire");
		ModLoader.addName(new ItemStack(BasicComponents.itemCircuit, 1, 0), "Basic Circuit");
        ModLoader.addName(new ItemStack(BasicComponents.itemCircuit, 1, 1), "Advanced Circuit");
        ModLoader.addName(new ItemStack(BasicComponents.itemCircuit, 1, 2), "Elite Circuit");
        ModLoader.addName(BasicComponents.blockBatteryBox, "Battery Box");
        ModLoader.addName(BasicComponents.blockCoalGenerator, "Coal Generator");
        ModLoader.addName(BasicComponents.blockElectricFurnace, "Electric Furnace");
		
		//Register Tile Entities
		ModLoader.registerTileEntity(TileEntityBatteryBox.class, "TileEntityBatteryBox");
		ModLoader.registerTileEntity(TileEntityCoalGenerator.class, "TileEntityCoalGenerator");
		ModLoader.registerTileEntity(TileEntityElectricFurnace.class, "TileEntityElectricFurnace");
		
		OreDictionary.registerOre("oreCopper", new ItemStack(blockOre, 1, 0));
		OreDictionary.registerOre("oreTin", new ItemStack(blockOre, 1, 1));
		OreDictionary.registerOre("ingotCopper", BasicComponents.itemCopperIngot);
		OreDictionary.registerOre("ingotTin", BasicComponents.itemTinIngot);
		OreDictionary.registerOre("ingotBronze", BasicComponents.itemBronzeIngot);
		OreDictionary.registerOre("ingotSteel", BasicComponents.itemSteelIngot);
		
		//Recipes
		//Motor
		UERecipeManager.addRecipe(new ItemStack(BasicComponents.itemMotor), new Object [] {"@!@", "!#!", "@!@", '!', BasicComponents.itemSteelPlate, '#', BasicComponents.itemCircuit, '@', BasicComponents.itemCopperWire});
		//Wrench
		UERecipeManager.addRecipe(new ItemStack(BasicComponents.itemWrench), new Object [] {"! !", " ! ", " ! ", '!', "ingotSteel"});
		//Battery Box
		UERecipeManager.addRecipe(BasicComponents.blockBatteryBox, new Object [] {"!?!", "???", "!?!", '!', BasicComponents.itemSteelPlate, '?', (((ItemBattery)BasicComponents.itemBattery).getChargedItemStack()) });
		//Coal Generator
		UERecipeManager.addRecipe(BasicComponents.blockCoalGenerator, new Object [] {"!@!", "$#$", "!?!", '!', BasicComponents.itemSteelPlate, '@', BasicComponents.itemCopperWire, '?', BasicComponents.itemCircuit, '#', BasicComponents.itemMotor, '$', Block.stoneOvenIdle});
		//Electric Furnace
		UERecipeManager.addRecipe(BasicComponents.blockElectricFurnace, new Object [] {"!!!", "!?!", "!#!", '!', "ingotSteel", '#', BasicComponents.itemCircuit, '?', BasicComponents.itemSteelPlate});
		//Copper
		UERecipeManager.addSmelting(new ItemStack(blockOre, 1, 0), new ItemStack(BasicComponents.itemCopperIngot));
		UERecipeManager.addRecipe(new ItemStack(BasicComponents.itemCopperWire, 6), new Object [] {"!!!", "@@@", "!!!", '!', Block.cloth, '@', "ingotCopper"});
		//Tin
		UERecipeManager.addSmelting(new ItemStack(blockOre, 1, 1), new ItemStack(BasicComponents.itemTinIngot));
		UERecipeManager.addRecipe(new ItemStack(BasicComponents.itemBattery), new Object [] {" ! ", "!#!", "!?!", '!', "ingotTin", '?', Item.redstone, '#', Item.coal});
		
		//Steel
		UERecipeManager.addRecipe(new ItemStack(BasicComponents.itemSteelClump), new Object [] {"!#!", '!', new ItemStack(Item.coal, 1, 1), '#', Item.ingotIron});
		UERecipeManager.addSmelting(BasicComponents.itemSteelClump, new ItemStack(BasicComponents.itemSteelIngot));
		UERecipeManager.addRecipe(new ItemStack(BasicComponents.itemSteelPlate), new Object [] {" ! ", "!!!", " ! ", '!', "ingotSteel"});
		//Bronze
		UERecipeManager.addRecipe(BasicComponents.itemBronzeClump, new Object [] {"!#!", '!', "ingotCopper",  '#', "ingotTin"});
		UERecipeManager.addSmelting(BasicComponents.itemBronzeClump, new ItemStack(BasicComponents.itemBronzeIngot));
		//Circuit
		UERecipeManager.addRecipe(new ItemStack(BasicComponents.itemCircuit, 1, 0), new Object [] {"!#!", "?@?", "!#!", '@', BasicComponents.itemSteelPlate, '?', Item.ingotGold, '#', Item.redstone, '!', BasicComponents.itemCopperWire});
		UERecipeManager.addRecipe(new ItemStack(BasicComponents.itemCircuit, 1, 1), new Object [] {"@@@", "#?#", "@@@", '@', Item.redstone, '?', Item.diamond, '#', BasicComponents.itemCircuit});
		UERecipeManager.addRecipe(new ItemStack(BasicComponents.itemCircuit, 1, 2), new Object [] {"@@@", "?#?", "@@@", '@', Item.ingotGold, '?', new ItemStack(BasicComponents.itemCircuit, 1, 1), '#', Block.blockLapis});
    }
    
    @PostInit
   	public void modsLoaded(FMLPostInitializationEvent evt) 
	{
    	UERecipeManager.initialize();
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		
	}
}