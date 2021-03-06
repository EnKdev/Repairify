package xyz.enkdev.repairify;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xyz.enkdev.repairify.config.RepairifyConfig;
import xyz.enkdev.repairify.event.KeybindEvents;
import xyz.enkdev.repairify.network.RepairifyPacketHandler;
import xyz.enkdev.repairify.registry.KeyBindingRegistry;

@Mod(Repairify.MOD_ID)
public class Repairify
{
    public static final String MOD_ID = "repairify";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public Repairify()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, RepairifyConfig.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RepairifyConfig.COMMON_CONFIG);

        // Register the setup method for mod loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(Repairify::setup);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Repairify::setupClient);
            MinecraftForge.EVENT_BUS.addListener(KeybindEvents::handleRepairOnKeybindStroke);
        });

        // The config itself won't work in-game but it is sort of setup.
        // Needs more investigation and research on how Forge does Config matters with 1.14 and above.
        RepairifyConfig.loadConfig(RepairifyConfig.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("repairify-client.toml"));
        RepairifyConfig.loadConfig(RepairifyConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("repairify-common.toml"));
    }

    public static void setup(FMLCommonSetupEvent event)
    {
        RepairifyPacketHandler.init();
    }

    public static void setupClient(FMLClientSetupEvent event)
    {
        ClientRegistry.registerKeyBinding(KeyBindingRegistry.REPAIR_KEY);
    }
}
