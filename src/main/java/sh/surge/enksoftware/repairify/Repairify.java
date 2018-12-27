package sh.surge.enksoftware.repairify;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sh.surge.enksoftware.repairify.network.CHandlerRepairify;
import sh.surge.enksoftware.repairify.network.CPacketRepairify;
import sh.surge.enksoftware.repairify.proxy.IProxy;

@Mod(modid = Repairify.MODID, name = "Repairify", version = "1.12.2-1.0.0", updateJSON = "http://enksoftware.surge.sh/backend/updatedata/repairify/updateData.json")
public class Repairify
{
    @Mod.Instance
    public static Repairify instance;

    public static final Logger LOGGER = LogManager.getLogger("repairify");

    public static final String MODID = "repairify";

    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    @SidedProxy(clientSide = "sh.surge.enksoftware.repairify.proxy.ClientProxy", serverSide = "sh.surge.enksoftware.repairify.proxy.ServerProxy")
    public static IProxy proxy = null;

    static
    {
        NETWORK.registerMessage(CHandlerRepairify.class, CPacketRepairify.class, 0, Side.SERVER);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
