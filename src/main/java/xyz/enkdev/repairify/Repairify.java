package xyz.enkdev.repairify;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xyz.enkdev.repairify.api.ClientProxy;
import xyz.enkdev.repairify.api.IProxy;
import xyz.enkdev.repairify.api.ServerProxy;
import xyz.enkdev.repairify.config.RepairifyConfig;
import xyz.enkdev.repairify.network.RepairifyPacketHandler;
import xyz.enkdev.repairify.registry.KeyBindingRegistry;

@Mod(Repairify.MOD_ID)
public class Repairify
{
    public static final String MOD_ID = "repairify";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Repairify()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, RepairifyConfig.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RepairifyConfig.COMMON_CONFIG);

        // Register the setup method for mod loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(Repairify::setup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new KeyBindingRegistry());

        // The config itself won't work in-game but it is sort of setup.
        // Needs more investigation and research on how Forge does Config matters with 1.14 and above.
        RepairifyConfig.loadConfig(RepairifyConfig.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("repairify-client.toml"));
        RepairifyConfig.loadConfig(RepairifyConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("repairify-common.toml"));
    }

    public static void setup(FMLCommonSetupEvent event)
    {
        // Do networking stuff here later after we got Networking down.
        proxy.init();
        RepairifyPacketHandler.init();
    }
}
