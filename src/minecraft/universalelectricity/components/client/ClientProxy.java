package universalelectricity.components.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import universalelectricity.components.client.gui.GuiBatteryBox;
import universalelectricity.components.client.gui.GuiCoalGenerator;
import universalelectricity.components.client.gui.GuiElectricFurnace;
import universalelectricity.components.common.BasicComponents;
import universalelectricity.components.common.CommonProxy;
import universalelectricity.components.common.tileentity.TileEntityBatteryBox;
import universalelectricity.components.common.tileentity.TileEntityCoalGenerator;
import universalelectricity.components.common.tileentity.TileEntityCopperWire;
import universalelectricity.components.common.tileentity.TileEntityElectricFurnace;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit()
	{

	}

	@Override
	public void init()
	{
		super.init();
		BasicComponents.registerTileEntityRenderers();
	}
}
