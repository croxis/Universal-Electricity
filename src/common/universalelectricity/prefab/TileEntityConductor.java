package universalelectricity.prefab;

import net.minecraft.src.Block;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.electricity.ElectricityManager;
import universalelectricity.implement.IConductor;
import universalelectricity.implement.IConnector;
import universalelectricity.implement.IElectricityReceiver;

/**
 * REQUIRED
 * This tile entity is for all conductors.
 * @author Calclavia
 *
 */
public abstract class TileEntityConductor extends TileEntity implements IConductor
{
    public int connectionID = 0;

    /**
     * Stores information on the blocks that this conductor is connected to
     */
    public TileEntity[] connectedBlocks = {null, null, null, null, null, null};

    public TileEntityConductor()
    {
        this.reset();
    }

    /**
     * Adds a connection between this conductor and a UE unit
     * @param tileEntity - Must be either a producer, consumer or a conductor
     * @param side - side in which the connection is coming from
     */
    public void updateConnection(TileEntity tileEntity, ForgeDirection side)
    {
        if (tileEntity instanceof TileEntityConductor || tileEntity instanceof IConnector)
        {
            this.connectedBlocks[side.ordinal()] = tileEntity;

            if (tileEntity instanceof TileEntityConductor)
            {
                ElectricityManager.instance.mergeConnection(this.connectionID, ((TileEntityConductor)tileEntity).connectionID);
            }
        }
        else
        {
            if (this.connectedBlocks[side.ordinal()] != null)
            {
                if (this.connectedBlocks[side.ordinal()] instanceof TileEntityConductor)
                {
                    ElectricityManager.instance.splitConnection(this, (TileEntityConductor)this.connectedBlocks[side.ordinal()]);
                }
            }

            this.connectedBlocks[side.ordinal()] = null;
        }
        
        if(!this.worldObj.isRemote)
        {
        	this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 0, 0);
        }
    }

    public void updateConnectionWithoutSplit(TileEntity tileEntity, ForgeDirection side)
    {
        if(tileEntity instanceof TileEntityConductor || tileEntity instanceof IConnector)
        {
            this.connectedBlocks[side.ordinal()] = tileEntity;

            if (tileEntity instanceof TileEntityConductor)
            {
                ElectricityManager.instance.mergeConnection(this.connectionID, ((TileEntityConductor)tileEntity).connectionID);
            }
        }
        else
        {
            this.connectedBlocks[side.ordinal()] = null;
        }
        
        if(!this.worldObj.isRemote)
        {
        	this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 0, 0);
        }
    }
    
    @Override
    public void receiveClientEvent(int key, int value)
    {
    	if(this.worldObj.isRemote)
    	{
    		this.refreshConnectedBlocks();
    	}
    }

    /**
     * Determines if this TileEntity requires update calls.
     * @return True if you want updateEntity() to be called, false if not
     */
    @Override
    public boolean canUpdate()
    {
        return false;
    }

    public void reset()
    {
        this.connectionID = 0;
        ElectricityManager.instance.registerConductor(this);
    }

    public void refreshConnectedBlocks()
    {
        if (this.worldObj != null)
        {
            BlockConductor.updateConductorTileEntity(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        }
    }

    public World getWorld()
    {
    	return this.worldObj;
    }
    
    public Block getBlockType()
    {
        if (this.blockType == null)
        {
            this.blockType = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord)];
        }

        return this.blockType;
    }
}