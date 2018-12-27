package sh.surge.enksoftware.repairify;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(modid = Repairify.MODID)
@Config(modid = Repairify.MODID)
public class RepairifyConfig
{
    @Config.Comment("List of items that shouldn't be repairable with this mod. Useful for modpack makers.")
    public static String[] repairBlackList = {};

    @Config.Comment("Soundcue upon successful repair check")
    public static String repairSoundCue = "minecraft:entity.experience_orb.pickup";

    @Config.Comment("What pitch should the soundcue be played?")
    public static float soundCuePitch = 0.0F;

    @Config.Comment("What volume should the soundcue be played?")
    public static float soundCueVolume = 1.0F;

    @Mod.EventBusSubscriber(modid = Repairify.MODID)
    private static class Handler
    {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(Repairify.MODID))
            {
                ConfigManager.sync(Repairify.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
